package com.s1mple.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.s1mple.constant.MessageConstant;
import com.s1mple.constant.RedisMessageConstant;
import com.s1mple.entity.Result;
import com.s1mple.pojo.Member;
import com.s1mple.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author wanfeng
 * @create 2022/2/24 23:32
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    /**
     * 登录
     */
    @RequestMapping("/login")
    public Result login(HttpServletResponse response, @RequestBody Map map){
        String redisString = jedisPool.getResource().get(map.get("telephone") + RedisMessageConstant.SENDTYPE_LOGIN);
        String getString = (String)map.get("validateCode");
        if(redisString!=null && redisString.equals(getString)){
            // 验证码正确
            Member byTelephone = memberService.findByTelephone(redisString);
            if(byTelephone == null ){
                // 不是会员自动保存
                byTelephone = new Member();
                byTelephone.setRegTime(new Date());
                byTelephone.setPhoneNumber(redisString);
                memberService.add(byTelephone);
            }
            // 向客户端写入cookie，内容为手机号
            Cookie cookie = new Cookie("member_telephone",redisString);
            cookie.setPath("/");
            cookie.setMaxAge(60*60*24*30);
            response.addCookie(cookie);
            // 将会员信息保存到redis
            String s = JSON.toJSON(byTelephone).toString();
            jedisPool.getResource().setex(redisString,60*30,s);
            return new Result(true,MessageConstant.LOGIN_SUCCESS);

        }else{
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }
}
