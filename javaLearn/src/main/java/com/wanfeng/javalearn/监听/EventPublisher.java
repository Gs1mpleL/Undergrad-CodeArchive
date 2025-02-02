package com.wanfeng.javalearn.监听;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EventPublisher {
    @Resource
    private ApplicationContext context;

    public void publishEvent(String str){
        context.publishEvent(new Event(str));
    }
}
