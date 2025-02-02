package com.wanfeng.myweb.job;

import com.wanfeng.myweb.job.job.NoticeJob;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MywebJobApplicationTests {
    @Autowired
    NoticeJob noticeJob;

    @Test
    public void test() throws InterruptedException {
        noticeJob.kaoYanHandler();
    }

}
