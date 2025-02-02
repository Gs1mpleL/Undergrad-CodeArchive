package com.wanfeng.myweb.crawler.controller;

import com.wanfeng.myweb.common.Utils.HttpUtils.Requests;
import com.wanfeng.myweb.common.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@Slf4j
public class KaoYanController {
    @Autowired
    Requests requests;

    @GetMapping("/kaoYan")
    public Result<?> kaoYan() {
        HashMap<String, String> map = new HashMap<>();
        map.put("xm", "刘卓昊");
        map.put("zjhm", "141082200102170039");
        map.put("ksbh", "107014142406069");
        map.put("bkdwdm", "10701");
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Cookie", "JSESSIONID=69A1672C2E320FD0832398A740F69D03; TS01cf0d22=01886fbf6e57c958206d753b3f77e955ef2bf347957ceac6062ba07d1f90699ecb2a45bf92bb098b92b0c9f2f1098ced319e267c94bdcefb14b6cda7b9414a9eb29cfd9fa23af7cc154a8a06ec2b58b8ffe1f4d352e751f0f736bfed8b8abd83c2ce3135f3; CHSICC_CLIENTFLAGYZ=953fa4295fb546e6438aa02a0b5a50f5; CHSICC02=!tyZQI4hQr1IHXXzzYxYLahOzddj6Y4GFtTqQ+On6EIQqSsx4A6oQEYuUl9MylYedCOfZCxPwlLfUcw==; CHSICC01=!zP7ZKs1kPEGw0wPzYxYLahOzddj6YweUucI/dvi7NsdceRP/QmXg5GDKXfsOqk6nBG4IYjd1BoTTAw==; Hm_lvt_3916ecc93c59d4c6e9d954a54f37d84c=1708567391; _gid=GA1.3.1375441761.1708567391; CHSICC_CLIENTFLAGAPPLY=ef623695d08380bc5f7c8213909daaa4; TS01d9ac57=01886fbf6e9555fbda1d4cf29c2fbfa9f55f34d96f7ceac6062ba07d1f90699ecb2a45bf92bb098b92b0c9f2f1098ced319e267c94bdcefb14b6cda7b9414a9eb29cfd9fa2dc18784e4a9ef27fe33064960274344db9c8b7b5123596db6c9cdd823a5e6deb; _ga_TT7MCH8RRF=GS1.1.1708567455.1.1.1708567466.0.0.0; Hm_lpvt_3916ecc93c59d4c6e9d954a54f37d84c=1708567652; _ga=GA1.1.71440793.1708567391; _ga_YZV5950NX3=GS1.1.1708567390.1.1.1708567651.0.0.0");
        String html = requests.getForHtml("https://yz.chsi.com.cn/apply/cjcx/cjcx.do", map, headers);

        // 检测是否存在成绩数据
        String regex = "cj: (.*?),";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            String group = matcher.group(1);
            log.info(group);
            if (!Objects.equals(group, "null")) {
                return Result.ok(group);
            }
        }
        Elements templates = Jsoup.parse(html).body().select("template");
        for (Element template : templates) {
            String str = template.html();
            log.info(str);
            if (str.contains("第一门") || html.contains("数学") || html.contains("政治")) {
                return Result.ok(Jsoup.parse(html).body().html());
            }
        }
        if (html.contains("第一门") || html.contains("数学") || html.contains("政治")) {
            return Result.ok(Jsoup.parse(html).body().html());
        }
        return Result.fail(html);
    }
}
