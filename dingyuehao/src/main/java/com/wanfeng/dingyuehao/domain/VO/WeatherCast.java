package com.wanfeng.dingyuehao.domain.VO;

import lombok.Data;

@Data
public class WeatherCast {
    private String date;
    private String week;
    private String dayweather;
    private String nightweather;
    private String daytemp;
    private String nighttemp;
    private String daywind;
    private String nightwind;
    private String daypower;
    private String nightpower;
}
