package com.wanfeng.dingyuehao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DingyuehaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DingyuehaoApplication.class, args);
    }

}
