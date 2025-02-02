package com.wanfeng.myweb.crawler.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Objects;

@Data
public class GuessResult {
    private String title;
    private Boolean res;
    private Double income;
    private Long time;

    public GuessResult(JSONObject oneRecord) {
        this.res = Objects.equals(oneRecord.getString("correct"), "1");
        this.title = oneRecord.getString("title");
        this.income = oneRecord.getDouble("income");
        this.time = oneRecord.getLong("ctime");
    }
}
