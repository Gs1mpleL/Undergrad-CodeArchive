package com.wanfeng.myweb.crawler.controller;


import com.wanfeng.myweb.common.vo.Result;
import com.wanfeng.myweb.crawler.service.BiliLoginService;
import com.wanfeng.myweb.crawler.service.Impl.biliTask.CoinLogTask;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Tag(name = "哔哩哔哩接口")
@RestController
@RequestMapping("/bilibili")
public class BilibiliController {
    @Resource
    private BiliLoginService loginService;
    @Autowired
    private CoinLogTask coinLogTask;

    @PostMapping("/dailyTask")
    @Operation(summary = "执行每日任务")
    @Parameters({
            @Parameter(name = "totalCookie", description = "完整的Cookie", required = true, content = @Content(mediaType = "application/json")),
    })
    public Result<?> dailyTask(@RequestBody String totalCookie) {
        String s = loginService.dailyTask(totalCookie);
        return Result.ok(s);
    }

    @Operation(summary = "更新Cookie")
    @GetMapping("/updateCookie")
    public Result<?> updateCookie() throws Exception {
        loginService.refreshCookie();
        return Result.ok();
    }

    @Operation(summary = "获取硬币变化日志")
    @GetMapping("/getCoinLogList")
    public Result<?> getCoinLogList() {
        return Result.ok(coinLogTask.getLogList());
    }
}
