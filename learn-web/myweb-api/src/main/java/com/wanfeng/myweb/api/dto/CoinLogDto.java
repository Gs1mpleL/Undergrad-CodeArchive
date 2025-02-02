package com.wanfeng.myweb.api.dto;

import lombok.Data;


@Data
public class CoinLogDto {
    private float delta;
    private String time;
    private String curTotal;
    private String reason;
}
