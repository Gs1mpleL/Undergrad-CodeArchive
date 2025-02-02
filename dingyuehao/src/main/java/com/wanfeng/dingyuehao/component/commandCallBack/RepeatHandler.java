package com.wanfeng.dingyuehao.component.commandCallBack;

import com.wanfeng.dingyuehao.domain.VO.MsgReq;
import com.wanfeng.dingyuehao.util.MsgUtils;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

@Component("重复")
public class RepeatHandler implements CommandHandler{
    @Override
    public String handler(Matcher matcher, MsgReq msgReq) {
        String content = msgReq.getContent();
        content = content.replace("重复 ","");
        return MsgUtils.buildReply(msgReq,content);
    }

    @Override
    public String desc() {
        return "让服务器重复你的话 \n重复 [需要重复的话]";
    }
}
