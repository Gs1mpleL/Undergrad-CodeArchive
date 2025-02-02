package com.wanfeng.javalearn.kafka学习;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@org.springframework.kafka.annotation.KafkaListener(topics = {"test-topic"},groupId = "test-group")
public class KafkaListener2 {
    @KafkaHandler
    public void receive(String msg){
        log.info("测试环境接收到消息[{}]",msg);
    }
}
