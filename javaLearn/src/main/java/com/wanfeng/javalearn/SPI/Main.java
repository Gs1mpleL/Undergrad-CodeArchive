package com.wanfeng.javalearn.SPI;

import java.util.ServiceLoader;

/**
 * 获取接口的实现类，需要在META-INF/services中注册
 */
public class Main {
    public static void main(String[] args) {
        ServiceLoader<Service> load = ServiceLoader.load(Service.class);
        for (Service service : load) {
            System.out.println(service);
        }
    }
}
