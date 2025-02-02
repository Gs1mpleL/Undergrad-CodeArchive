package com.wanfeng.javalearn.设计模式.proxy;

import com.wanfeng.javalearn.service.HelloService;
import net.sf.cglib.proxy.Enhancer;

public class CGLIBProxy {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloService.class);
        System.out.println(Integer.valueOf("001"));
    }
}
