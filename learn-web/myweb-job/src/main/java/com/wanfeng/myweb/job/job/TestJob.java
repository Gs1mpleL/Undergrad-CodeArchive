package com.wanfeng.myweb.job.job;

import com.wanfeng.myweb.api.client.UserClient;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TestJob {
    @Resource
    private UserClient userClient;

    @XxlJob("testHandler")
    public void testHandler() {
        System.out.println(userClient.pushIphoneEasy("xxl-job测试！"));
    }
}
