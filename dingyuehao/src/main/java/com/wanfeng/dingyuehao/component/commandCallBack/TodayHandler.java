package com.wanfeng.dingyuehao.component.commandCallBack;

import com.wanfeng.dingyuehao.domain.VO.MsgReq;
import com.wanfeng.dingyuehao.util.MsgUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

@Component("今日")
public class TodayHandler implements CommandHandler{

    @Override
    public String handler(Matcher matcher, MsgReq msgReq) {
        return MsgUtils.buildReply(msgReq,"今日消息");
    }

    @Override
    public String desc() {
        return "获取实时消息\n仅支持 今日";
    }
}
