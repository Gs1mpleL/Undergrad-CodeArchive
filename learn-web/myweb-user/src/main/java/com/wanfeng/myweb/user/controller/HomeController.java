package com.wanfeng.myweb.user.controller;

import cn.hutool.core.date.DateTime;
import com.wanfeng.myweb.api.client.CrawlerClient;
import com.wanfeng.myweb.api.dto.CoinLogDto;
import com.wanfeng.myweb.api.dto.SystemConfigDto;
import com.wanfeng.myweb.common.vo.Result;
import com.wanfeng.myweb.user.dto.HomeCardVo;
import com.wanfeng.myweb.user.dto.HomeDataVo;
import com.wanfeng.myweb.user.entity.HomeCardEntity;
import com.wanfeng.myweb.user.entity.SystemConfigEntity;
import com.wanfeng.myweb.user.service.HomeCardService;
import com.wanfeng.myweb.user.service.SystemConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "首页数据接口")
public class HomeController {
    @Autowired
    HomeCardService homeCardService;
    @Autowired
    SystemConfigService systemConfigService;
    @Autowired
    CrawlerClient CrawlerClient;

    @Operation(summary = "获取主页数据")
    @GetMapping("/getHomeData")
    public Result<?> getHomeData() {
        HomeDataVo homeDataVo = new HomeDataVo();
        SystemConfigEntity byId = systemConfigService.getById(1);
        SystemConfigDto systemConfigDto = new SystemConfigDto();
        BeanUtils.copyProperties(byId, systemConfigDto);
        byId.setTime(DateTime.now().toTimestamp());
        systemConfigService.updateById(byId);
        homeDataVo.setUserInfo(systemConfigDto);
        List<HomeCardEntity> list = homeCardService.list();
        List<HomeCardVo> homeCardVos = new ArrayList<>();
        for (HomeCardEntity homeCardEntity : list) {
            HomeCardVo homeCardVo = new HomeCardVo();
            BeanUtils.copyProperties(homeCardEntity, homeCardVo);
            homeCardVo.setContent("未添加！");
            homeCardVos.add(homeCardVo);
        }

        homeDataVo.setCardData(homeCardVos);
        List<CoinLogDto> data = CrawlerClient.getCoinLogList().getData();
        homeDataVo.setCoinLogs(data);

        // 第一个小卡片的展示内容
        float money = 0;
        for (CoinLogDto datum : data) {
            if (datum.getReason().contains("预测")) {
                money += datum.getDelta();
            }
        }
        int intMoney = (int) money;
        homeCardVos.get(0).setContent(Math.abs(intMoney) + "个硬币");
        if (intMoney >= 0) {
            homeCardVos.get(0).setTitle("B站赛事竞猜盈利");
        } else {
            homeCardVos.get(0).setTitle("B站赛事竞猜亏损");
        }

        return Result.ok(homeDataVo);
    }

    @Operation(summary = "更新主页卡片")
    @PostMapping("/updateCart")
    public Result<?> updateCart(@RequestBody HomeCardVo homeCardVo) {
        HomeCardEntity homeCardEntity = new HomeCardEntity();
        BeanUtils.copyProperties(homeCardVo, homeCardEntity);
        return Result.ok(homeCardService.updateById(homeCardEntity));
    }
}
