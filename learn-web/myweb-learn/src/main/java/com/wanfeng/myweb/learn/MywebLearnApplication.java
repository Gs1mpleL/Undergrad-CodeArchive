package com.wanfeng.myweb.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.wanfeng.myweb")
public class MywebLearnApplication {
    public static void main(String[] args) {
        SpringApplication.run(MywebLearnApplication.class, args);
    }

}
