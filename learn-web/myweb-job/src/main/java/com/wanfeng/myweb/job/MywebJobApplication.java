package com.wanfeng.myweb.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.wanfeng.myweb.api")
@SpringBootApplication(scanBasePackages = "com.wanfeng.myweb")
public class MywebJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(MywebJobApplication.class, args);
    }

}
