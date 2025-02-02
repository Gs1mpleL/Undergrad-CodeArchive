package com.wanfeng.myweb.crawler.service;

import com.wanfeng.myweb.crawler.dto.BaiduLocalVo;

public interface BaiduService {
    BaiduLocalVo getLocal(String ip);
}
