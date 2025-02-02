package com.s1mple.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.s1mple.constant.MessageConstant;
import com.s1mple.constant.RedisMessageConstant;
import com.s1mple.entity.Result;
import com.s1mple.pojo.Order;
import com.s1mple.service.OrderService;
import com.s1mple.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * 预约提交Controller
 * @author wanfeng
 * @create 2022/2/24 19:32
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;
    /**
     * 在线体检预约 若返回的参数什么实体都不能接收，可以使用map来接受
     */
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
        // 判断验证码的正确
        String redisString = jedisPool.getResource().get(map.get("email") + RedisMessageConstant.SENDTYPE_ORDER);
        String getString = (String)map.get("validateCode");
        // 处理预约
        if(redisString != null && redisString.equals(getString)){
            // 设置预约类型
            map.put("orderType",Order.ORDERTYPE_WEIXIN);
            // 这种远程调用可能出问题 需要处理
            Result orderResult = null;
            try{
                orderResult = orderService.order(map);
            }catch (Exception e){
                e.printStackTrace();
                return orderResult;
            }
            // 预约成功
            if(orderResult.isFlag()){
                // 给用户发送预约成功信息
                try{
                    EmailUtils.sendEmail((String) map.get("email"),"预约成功！日期 :" + map.get("orderDate"));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            return orderResult;
        }else{
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }


    /**
     * 返回预约信息
     */
    @RequestMapping("/findbyId")
    public Result findById(Integer id){
        Map order;
        try{
            order = orderService.findById(id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);}
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS);
    }
}
