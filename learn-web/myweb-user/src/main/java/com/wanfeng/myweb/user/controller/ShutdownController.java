package com.wanfeng.myweb.user.controller;

import com.wanfeng.myweb.common.vo.Result;
import com.wanfeng.myweb.user.entity.SystemConfigEntity;
import com.wanfeng.myweb.user.service.SystemConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
@Tag(name = "关机信号接口")
public class ShutdownController {
    @Autowired
    SystemConfigService systemConfigService;

    @Operation(summary = "获取关机信号")
    @GetMapping("/shutdownSign")
    public Result<?> getShutdownSign() {
        LocalTime now = LocalTime.now(); // 获取当前时间
//        SystemConfigEntity byId = systemConfigService.getById(1);
//        if (byId.getShutdown() == 1) {
//            if (now.isAfter(LocalTime.of(19, 0))) { // 判断是否在下午7点之后
//                return Result.ok(0);
//            } else {
//                return Result.ok(1);
//            }
//        } else {
//            return Result.ok(0);
//        }


        if (now.isAfter(LocalTime.of(20, 0)) || now.isBefore(LocalTime.of(1,0))) { // 判断是否在下午8点之后
            return Result.ok(0);
        } else {
            return Result.ok(1);
        }
    }

}
