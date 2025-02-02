package com.wanfeng.myweb.user.controller;

import com.wanfeng.myweb.api.dto.PushVO;
import com.wanfeng.myweb.user.service.PushService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Tag(name = "推送服务接口")
public class PushController {
    @Resource
    private PushService pushService;

    @Operation(summary = "推送到Iphone")
    @PostMapping("/pushIphone")
    public boolean pushIphone(@RequestBody PushVO pushVO) {
        return pushService.pushIphone(pushVO);
    }

    @Operation(summary = "推送到Mac")
    @PostMapping("/pushMac")
    public boolean pushMac(@RequestBody PushVO pushVO) {
        return pushService.pushMac(pushVO);
    }

    @Operation(summary = "简易信息推送")
    @PostMapping("/pushIphoneEasy")
    public boolean pushIphoneEasy(@RequestBody String msg) {
        return pushService.pushIphone(msg);
    }
}
