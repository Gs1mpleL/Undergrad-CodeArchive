package com.wanfeng.myweb.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {"com.wanfeng.myweb.user", "com.wanfeng.myweb.common"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.wanfeng.myweb.api")
public class MywebUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MywebUserApplication.class, args);
    }

}
