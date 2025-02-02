package com.wanfeng.javalearn.监听;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class EventListenerB implements ApplicationListener<Event> {
    @Override
    public void onApplicationEvent(Event event) {
        String source = (String) event.getSource();
        System.out.println("listenerB 接收到 消息");
        System.out.println(source);
    }
}
