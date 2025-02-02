package com.wanfeng.myweb.crawler.controller;

import com.wanfeng.myweb.common.vo.Result;
import com.wanfeng.myweb.crawler.service.BaiduService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/baidu")
@Tag(name = "百度开放平台接口")
public class BaiduController {
    @Autowired
    private BaiduService baiduService;

    @Operation(summary = "根据IP获取地理位置")
    @GetMapping("/getLocal/{ip}")
    public Result<?> getLocal(@PathVariable String ip) {
        return Result.ok(baiduService.getLocal(ip));
    }
}
