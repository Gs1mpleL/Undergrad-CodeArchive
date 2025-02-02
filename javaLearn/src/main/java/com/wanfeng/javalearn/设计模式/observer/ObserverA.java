package com.wanfeng.javalearn.设计模式.observer;

public class ObserverA implements Observer{
    @Override
    public void update() {
        System.out.println("A get update");
    }
}
