package com.wanfeng.javalearn.设计模式.proxy;

import com.wanfeng.javalearn.service.HelloService;
import com.wanfeng.javalearn.service.HelloServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKProxy implements InvocationHandler {
    private Object object;

    JDKProxy(Object o){
        object = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("Pre Process");
        Object invoke = method.invoke(object,args);
        System.out.println("after Process");
        return invoke;
    }


    public static void main(String[] args) {
        HelloService helloService1 = new HelloServiceImpl();
        HelloService o1 = (HelloService)Proxy.newProxyInstance(helloService1.getClass().getClassLoader(), new Class[]{HelloService.class}, new JDKProxy(helloService1));
        o1.hello();
    }
}
