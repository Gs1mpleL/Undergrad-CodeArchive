package com.wanfeng.myweb.job.job;

import com.wanfeng.myweb.api.client.CrawlerClient;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CrawlerJob {
    @Resource
    private CrawlerClient crawlerClient;

    @XxlJob("bibiDailyHandler")
    public void bibiDailyHandler() {
        crawlerClient.dailyTask("liuzhuohao123");
    }
}
