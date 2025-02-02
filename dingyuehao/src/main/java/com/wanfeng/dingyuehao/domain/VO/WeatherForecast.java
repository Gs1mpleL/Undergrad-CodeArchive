package com.wanfeng.dingyuehao.domain.VO;

import lombok.Data;

import java.util.List;

@Data
public class WeatherForecast {
    private String city;
    private String adcode;
    private String province;
    private String reporttime;
    private List<WeatherCast> casts;
}
