package com.s1mple.jobs;

import com.s1mple.constant.RedisConstant;
import com.s1mple.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * 清理垃圾图片的任务
 * @author lzh80
 */
public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){
        // sdiff 返回 两个集合的差别内容
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if(sdiff!=null){
            for(String imgName:sdiff){
                // 删除云服务器的图片，删除redis中垃圾缓存
                QiniuUtils.deleteFileFromQiniu(imgName);
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,imgName);
            }
        }
    }
}
