package com.wanfeng.javalearn.SPI;

public class ServiceImpl implements Service{
    @Override
    public void test() {
        System.out.println("Hello SPI");
    }
}
