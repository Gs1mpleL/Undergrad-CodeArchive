package com.s1mple.controller;

import com.s1mple.constant.MessageConstant;
import com.s1mple.constant.RedisMessageConstant;
import com.s1mple.entity.Result;
import com.s1mple.utils.EmailUtils;
import com.s1mple.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * 验证码操作Controller
 * @author wanfeng
 * @create 2022/2/24 15:04
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 发送订单确定的验证码
     *  使用redis保存验证码信息（5分钟自动清理）
     */
    @RequestMapping("/sendForOrder")
    public Result sendForOrder(String email) {
        // 随机生成4位数字验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        try{
            EmailUtils.sendEmail(email, String.valueOf(validateCode));
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        // 设置某个值保存一段时间 使用email+验证码类型来保证唯一   保存验证码5分钟
        jedisPool.getResource().setex(email+ RedisMessageConstant.SENDTYPE_ORDER,300, String.valueOf(validateCode));
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    /**
     * 发送登录确定的验证码
     *  使用redis保存验证码信息（5分钟自动清理）
     */
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        // 随机生成4位数字验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        try{
            EmailUtils.sendEmail(telephone, String.valueOf(validateCode));
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        // 设置某个值保存一段时间 使用email+验证码类型来保证唯一   保存验证码5分钟
        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_LOGIN,300, String.valueOf(validateCode));
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
