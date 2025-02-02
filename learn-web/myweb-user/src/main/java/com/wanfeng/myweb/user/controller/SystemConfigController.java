package com.wanfeng.myweb.user.controller;

import com.wanfeng.myweb.api.dto.SystemConfigDto;
import com.wanfeng.myweb.user.entity.SystemConfigEntity;
import com.wanfeng.myweb.user.service.SystemConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@Tag(name = "系统配置接口")
public class SystemConfigController {

    @Resource
    private SystemConfigService systemConfigService;

    @Operation(summary = "获取系统配置信息")
    @GetMapping("/getSystemConfig")
    public SystemConfigDto getSystemConfig() {
        SystemConfigDto systemConfigDto = new SystemConfigDto();
        SystemConfigEntity byId = systemConfigService.getById(1);
        BeanUtils.copyProperties(byId, systemConfigDto);
        return systemConfigDto;
    }

    @Operation(summary = "更新系统配置")
    @PostMapping("/updateSystemConfig")
    public boolean updateSystemConfig(@RequestBody SystemConfigDto systemConfigDto) {
        SystemConfigEntity systemConfigEntity = new SystemConfigEntity();
        BeanUtils.copyProperties(systemConfigDto, systemConfigEntity);
        return systemConfigService.updateById(systemConfigEntity);
    }
}
