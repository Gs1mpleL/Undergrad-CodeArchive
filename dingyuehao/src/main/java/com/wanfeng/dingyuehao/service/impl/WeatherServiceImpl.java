package com.wanfeng.dingyuehao.service.impl;

import com.wanfeng.dingyuehao.domain.VO.WeatherResp;
import com.wanfeng.dingyuehao.feign.GaoDeClient;
import com.wanfeng.dingyuehao.domain.prop.GaoDeProp;
import com.wanfeng.dingyuehao.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {
    @Autowired
    private GaoDeProp gaoDeProp;

    @Autowired
    private GaoDeClient gaoDeClient;

    @Override
    public WeatherResp getLiveWeather(String city) {
        for (String s : gaoDeProp.getCityAndCode()) {
            if (s.contains(city)){
                String[] cityAndCode = s.split("_");
                return gaoDeClient.getWeather(gaoDeProp.getKey(), cityAndCode[1],
                        "base", "JSON");
            }
        }


        return null;
    }

    @Override
    public WeatherResp getForecastWeather(String city) {
        for (String s : gaoDeProp.getCityAndCode()) {
            if (s.contains(city)){
                String[] cityAndCode = s.split("_");
                return gaoDeClient.getWeather(gaoDeProp.getKey(), cityAndCode[1],
                        "all", "JSON");
            }
        }


        return null;
    }
}
