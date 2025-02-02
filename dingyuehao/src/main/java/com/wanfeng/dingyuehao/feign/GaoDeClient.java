package com.wanfeng.dingyuehao.feign;

import com.wanfeng.dingyuehao.domain.VO.WeatherResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "https://restapi.amap.com/v3",name = "GaoDe")
public interface GaoDeClient {
    @GetMapping("/weather/weatherInfo")
    WeatherResp getWeather(@RequestParam("key")String key,@RequestParam("city")String cityCode,@RequestParam("extensions")String extensions,@RequestParam("output")String output);

}
