package com.wanfeng.myweb.crawler.service.Impl.biliTask;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wanfeng.myweb.api.client.UserClient;
import com.wanfeng.myweb.api.dto.CoinLogDto;
import com.wanfeng.myweb.common.Utils.ThreadLocalUtils;
import com.wanfeng.myweb.crawler.config.BiliUserData;
import com.wanfeng.myweb.crawler.service.BiliHttpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class CoinLogTask implements Task {
    @Autowired
    private BiliHttpService biliHttpService;
    @Autowired
    private UserClient userClient;

    public void run() {
        getLogList();
    }

    public List<CoinLogDto> getLogList() {
        ThreadLocalUtils.put(BiliUserData.BILI_USER_DATA, new BiliUserData(userClient.getSystemConfig()));
        JSONArray logList = biliHttpService.getWithTotalCookie("https://api.bilibili.com/x/member/web/coin/log?jsonp=jsonp&web_location=333.33").getJSONObject("data").getJSONArray("list");
        float curCoinNum = biliHttpService.getWithTotalCookie("https://account.bilibili.com/site/getCoin?web_location=333.33").getJSONObject("data").getFloat("money");
        List<CoinLogDto> logDtos = new ArrayList<>();
        CoinLogDto preAddToList = null;
        for (int i = 0; i < logList.size(); i++) {
            JSONObject oneRecord = logList.getJSONObject(i);
            CoinLogDto coinLogDto = new CoinLogDto();
            coinLogDto.setDelta(oneRecord.getFloat("delta"));
            coinLogDto.setReason(oneRecord.getString("reason").contains("打赏") ? "视频打赏" : oneRecord.getString("reason"));
            String time = oneRecord.getString("time");
            // 与上一条时间相同  就合并
            if (preAddToList != null && (Objects.equals(time, preAddToList.getTime()) || (Objects.equals(coinLogDto.getReason(), "视频打赏") && Objects.equals(preAddToList.getReason(), "视频打赏")))) {
                preAddToList.setDelta(preAddToList.getDelta() + coinLogDto.getDelta());
                continue;
            }
            coinLogDto.setTime(time);
            logDtos.add(coinLogDto);
            preAddToList = coinLogDto;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        for (CoinLogDto logDto : logDtos) {

            logDto.setCurTotal(df.format(curCoinNum));
            curCoinNum = curCoinNum - logDto.getDelta();
        }
        log.info("上传日志[{}]", logDtos);
        Collections.reverse(logDtos);
        return logDtos;
    }
}
