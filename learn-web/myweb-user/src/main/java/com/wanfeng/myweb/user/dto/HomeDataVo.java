package com.wanfeng.myweb.user.dto;

import com.wanfeng.myweb.api.dto.CoinLogDto;
import com.wanfeng.myweb.api.dto.SystemConfigDto;
import lombok.Data;

import java.util.List;

@Data
public class HomeDataVo {
    private SystemConfigDto UserInfo;
    private List<HomeCardVo> cardData;
    private List<CoinLogDto> coinLogs;
}
