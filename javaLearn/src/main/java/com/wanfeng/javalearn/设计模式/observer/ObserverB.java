package com.wanfeng.javalearn.设计模式.observer;

public class ObserverB implements Observer{
    @Override
    public void update() {
        System.out.println("B get update");
    }
}
