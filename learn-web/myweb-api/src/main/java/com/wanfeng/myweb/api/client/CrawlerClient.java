package com.wanfeng.myweb.api.client;

import com.wanfeng.myweb.api.dto.CoinLogDto;
import com.wanfeng.myweb.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "myweb-crawler", contextId = "myweb-crawler")
public interface CrawlerClient {
    @PostMapping("/bilibili/dailyTask")
    Result<?> dailyTask(@RequestBody String totalCookie);

    @GetMapping("/bilibili/updateCookie")
    Result<?> updateCookie();

    @GetMapping("/bilibili/getCoinLogList")
    Result<List<CoinLogDto>> getCoinLogList();

    @GetMapping("/kaoYan")
    Result<?> kaoYan();
}
