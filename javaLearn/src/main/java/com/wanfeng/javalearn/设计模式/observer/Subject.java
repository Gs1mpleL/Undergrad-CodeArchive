package com.wanfeng.javalearn.设计模式.observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void notify_All(){
        for (Observer observer : observers) {
            observer.update();
        }
    }



    public void change(){
        notify_All();
    }
}
