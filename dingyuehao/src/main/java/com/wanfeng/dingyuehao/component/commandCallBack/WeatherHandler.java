package com.wanfeng.dingyuehao.component.commandCallBack;

import com.wanfeng.dingyuehao.domain.VO.MsgReq;
import com.wanfeng.dingyuehao.domain.VO.WeatherForecast;
import com.wanfeng.dingyuehao.domain.VO.WeatherLive;
import com.wanfeng.dingyuehao.domain.VO.WeatherResp;
import com.wanfeng.dingyuehao.service.WeatherService;
import com.wanfeng.dingyuehao.domain.template.StringTemplate;
import com.wanfeng.dingyuehao.util.MsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Component("天气")
public class WeatherHandler implements CommandHandler{

    @Autowired
    private WeatherService weatherService;

    @Override
    public String handler(Matcher matcher, MsgReq msgReq) {
        String city = null;
        try {
            city = matcher.group(2).replace(" ","");

        }catch (Exception e){
            city = "上海市";
        }
        WeatherResp weather = weatherService.getLiveWeather(city);
        WeatherLive live = weather.getLives().get(0);
        WeatherResp forecastWeather = weatherService.getForecastWeather(city);
        WeatherForecast weatherForecast = forecastWeather.getForecasts().get(0);
        String collect = weatherForecast.getCasts().stream().map(i->{
            String week = i.getDate();
            String dayweather = i.getDayweather();
            String daytemp = i.getDaytemp();
            return week+":"+dayweather+","+daytemp+"℃";
        }).collect(Collectors.joining("\n"));
        String reply = String.format(StringTemplate.WeatherTemplate,city,live.getWeather(),live.getTemperature()+"℃",live.getWindpower(),"\n"+collect+"\n");
        return MsgUtils.buildReply(msgReq,reply);
    }

    @Override
    public String desc() {
        return "查询当前天气以及天气预报\n天气 [城市名（不填默认上海市）]";
    }
}
