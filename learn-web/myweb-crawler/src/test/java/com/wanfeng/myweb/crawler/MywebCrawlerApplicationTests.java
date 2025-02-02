package com.wanfeng.myweb.crawler;

import com.wanfeng.myweb.api.client.UserClient;
import com.wanfeng.myweb.common.Utils.ThreadLocalUtils;
import com.wanfeng.myweb.crawler.config.BiliUserData;
import com.wanfeng.myweb.crawler.service.BaiduService;
import com.wanfeng.myweb.crawler.service.Impl.biliTask.CoinLogTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
@Slf4j
class MywebCrawlerApplicationTests {
    @Resource
    UserClient userClient;
    @Resource
    private BaiduService baiduService;
    @Resource
    private CoinLogTask coinLogTask;

    void loadBiliBiliContext() {
        ThreadLocalUtils.put(BiliUserData.BILI_USER_DATA, new BiliUserData(userClient.getSystemConfig()));
    }
}
