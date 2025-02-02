package com.wanfeng.javalearn.监听;


import org.springframework.context.ApplicationEvent;


public class Event extends ApplicationEvent {
    public Event(String eventStr){super(eventStr);}
}
