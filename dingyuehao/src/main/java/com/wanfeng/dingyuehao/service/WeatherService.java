package com.wanfeng.dingyuehao.service;

import com.wanfeng.dingyuehao.domain.VO.WeatherResp;

public interface WeatherService {
    WeatherResp getLiveWeather(String city);

    WeatherResp getForecastWeather(String city);
}
