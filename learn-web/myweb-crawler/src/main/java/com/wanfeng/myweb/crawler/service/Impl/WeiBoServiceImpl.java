package com.wanfeng.myweb.crawler.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wanfeng.myweb.api.client.UserClient;
import com.wanfeng.myweb.api.dto.PushVO;
import com.wanfeng.myweb.common.Utils.HttpUtils.Requests;
import com.wanfeng.myweb.crawler.dto.WeiBoHotNew;
import com.wanfeng.myweb.crawler.service.WeiBoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class WeiBoServiceImpl implements WeiBoService {
    @Resource
    private Requests requests;
    @Resource
    private UserClient userClient;

    @Override
    public void pushNews() {
        ArrayList<WeiBoHotNew> hotList = getHotList();
        StringBuilder pushMsgBuilder = new StringBuilder();
        for (int i = 0; i < hotList.size(); i++) {
            pushMsgBuilder.append(hotList.get(i).getRealpos()).append(".").append(hotList.get(i).getWord()).append("\n");
        }
        String pushMsg = pushMsgBuilder.substring(0, pushMsgBuilder.length() - 1);
        userClient.pushIphone(new PushVO("微博热搜", pushMsg, "微博热搜", "https://img1.baidu.com/it/u=4085563512,341444261&fm=253&fmt=auto&app=138&f=JPEG?w=531&h=332"));
    }

    /**
     * 获取十条微博热搜
     */
    @Override
    public ArrayList<WeiBoHotNew> getHotList() {
        String hotUrl = "https://weibo.com/ajax/side/hotSearch";
        JSONObject jsonObject = requests.get(hotUrl, null, null);
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("realtime");
        ArrayList<WeiBoHotNew> weiBoHotNews = new ArrayList<>();
        // 只取9条
        for (int i = 0; i < 11; i++) {
            String word = jsonArray.getJSONObject(i).getString("word");
            weiBoHotNews.add(new WeiBoHotNew(word, String.valueOf(i + 1), "https://s.weibo.com/weibo?q=" + word));
        }
        return weiBoHotNews;
    }
}
