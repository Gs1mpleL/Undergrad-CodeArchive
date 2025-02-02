package com.wanfeng.myweb.job.job;

import com.alibaba.fastjson.JSON;
import com.wanfeng.myweb.api.client.CrawlerClient;
import com.wanfeng.myweb.api.client.UserClient;
import com.wanfeng.myweb.api.dto.PgNotice;
import com.wanfeng.myweb.api.dto.PushVO;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import groovy.util.logging.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Component
@Slf4j
public class NoticeJob {
    private final Logger logger = LoggerFactory.getLogger(NoticeJob.class);


    @Resource
    UserClient userClient;
    @Resource
    CrawlerClient crawlerClient;

    @XxlJob("pushNoticeHandler")
    public void pushNoticeHandler() {

        // 获取参数
        String param = XxlJobHelper.getJobParam();
        userClient.pushIphone(JSON.parseObject(param, PushVO.class));
    }

    @XxlJob("kaoYanHandler")
    public void kaoYanHandler() {
        PgNotice firstNotice = getFirstNotice();
        if (!Objects.equals(firstNotice.getTime(), "2024-02-23")){
            userClient.pushIphone(new PushVO("公告更新",firstNotice.getMsg(),"NOTICE",firstNotice.getUrl()));
        }else {
            XxlJobHelper.handleSuccess(firstNotice.getMsg());
        }
    }

    private static PgNotice getFirstNotice() {
        String url = "https://gr.xidian.edu.cn/yjsy/yjszs.htm";
        try {
            Elements doc = Jsoup.connect(url).get().body().select(".main-right-list").select("UL").select("#lineu10_0");
            // 查找<a>标签
            Elements aTags = doc.select("a");
            if (!aTags.isEmpty()) {
                Element aTag = aTags.first();
                String msg = aTag.text();
                String url1 = aTag.attr("href").replace("..","https://gr.xidian.edu.cn/");

                String time = null;
                Element nextSibling = aTag.nextElementSibling();
                if (nextSibling != null && nextSibling.tagName().equals("span")) {
                    time = nextSibling.text().trim(); // 移除两边的空白字符
                } else {
                    String textAfterATag = aTag.parent().text().substring(aTag.text().length()).trim();
                    int spaceIndex = textAfterATag.indexOf(' ');
                    if (spaceIndex != -1) {
                        time = textAfterATag.substring(0, spaceIndex).trim();
                    }
                }

                // 创建PgNotice实例
                return new PgNotice(msg, time, url1);
            }
        } catch (Exception ignored){

        }
        return new PgNotice("error", "2024-02-23", "http://wanfeng.pro");
    }
}
