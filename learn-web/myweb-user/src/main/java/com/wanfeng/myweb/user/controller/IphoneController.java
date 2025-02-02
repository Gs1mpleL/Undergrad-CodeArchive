package com.wanfeng.myweb.user.controller;

import com.wanfeng.myweb.common.vo.Result;
import com.wanfeng.myweb.user.entity.SystemConfigEntity;
import com.wanfeng.myweb.user.service.PushService;
import com.wanfeng.myweb.user.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iphone")
public class IphoneController {
    @Autowired
    PushService pushService;
    @Autowired
    SystemConfigService systemConfigService;

    @GetMapping("/position/{pos}")
    public Result<?> test(@PathVariable String pos) {
        SystemConfigEntity byId = systemConfigService.getById(1);
        byId.setPosition(pos);
        systemConfigService.updateById(byId);
        return Result.ok(pos);
    }
}
