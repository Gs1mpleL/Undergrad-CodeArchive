package com.wanfeng.dingyuehao.domain.VO;

import lombok.Data;

import java.util.List;

@Data
public class WeatherResp {
    private String status;
    private String count;
    private String info;
    private String infocode;
    private List<WeatherLive> lives;
    private List<WeatherForecast> forecasts;
}
