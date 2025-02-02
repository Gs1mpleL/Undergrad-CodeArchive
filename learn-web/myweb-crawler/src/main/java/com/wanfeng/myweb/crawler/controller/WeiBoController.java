package com.wanfeng.myweb.crawler.controller;


import com.wanfeng.myweb.common.vo.Result;
import com.wanfeng.myweb.crawler.service.WeiBoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/weibo")
@Tag(name = "微博接口")
public class WeiBoController {
    @Resource
    WeiBoService weiBoService;

    @Operation(summary = "获取微博热搜")
    @GetMapping("/news")
    public Result<?> getHot() {
        return Result.ok(weiBoService.getHotList());
    }
}
