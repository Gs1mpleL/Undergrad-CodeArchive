package com.wanfeng.javalearn;

import com.wanfeng.javalearn.MyBatis测试.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest(classes = JavaLearnApplication.class)
@Slf4j
class JavaLearnApplicationTests {
    @Autowired
    private BizServiceImpl bizService;

    @Autowired
    private TimeMapper timeMapper;
    @Test
    void contextLoads() {
    }
    @Test
    void insertAndDeleteTest(){
        List<BizEntity> list = bizService.list();
    }

    @Test
    void insertTimeTest(){
        TimeEntity timeEntity = new TimeEntity();
        Date date = new Date();
        System.out.println(date);
        timeEntity.setDate(date);
        timeMapper.insert(timeEntity);
    }

}
