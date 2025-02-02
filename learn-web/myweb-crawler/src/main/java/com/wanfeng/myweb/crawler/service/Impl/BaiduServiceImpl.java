package com.wanfeng.myweb.crawler.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.wanfeng.myweb.common.Utils.HttpUtils.Requests;
import com.wanfeng.myweb.crawler.dto.BaiduLocalVo;
import com.wanfeng.myweb.crawler.service.BaiduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class BaiduServiceImpl implements BaiduService {
    @Autowired
    private Requests requests;

    @Override
    public BaiduLocalVo getLocal(String ip) {
        HashMap<String, String> map = new HashMap<>();
        map.put("ip", ip);
        map.put("ak", "YPEr26k9yxSiQavkG8SL3X8nkq5yYCUb");
        JSONObject jsonObject = requests.get("http://api.map.baidu.com/location/ip", map, null);
        JSONObject jsonObject1 = jsonObject.getJSONObject("content").getJSONObject("address_detail");
        BaiduLocalVo baiduLocalVo = new BaiduLocalVo();
        baiduLocalVo.setCity(jsonObject1.getString("city"));
        baiduLocalVo.setProvince(jsonObject1.getString("province"));
        return baiduLocalVo;
    }
}
