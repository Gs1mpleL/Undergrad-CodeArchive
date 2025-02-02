package com.wanfeng.javalearn.设计模式.observer;

public class Main {
    public static void main(String[] args) {
        Observer a = new ObserverA();
        Observer b = new ObserverB();
        Subject subject = new Subject();
        subject.addObserver(a);
        subject.addObserver(b);
        subject.change();
        System.out.println("观察者模式，在被观察者变动时，主动去通知所有观察者");
    }
}
