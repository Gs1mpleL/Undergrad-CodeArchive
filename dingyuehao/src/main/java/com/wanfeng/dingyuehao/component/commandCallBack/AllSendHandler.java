package com.wanfeng.dingyuehao.component.commandCallBack;

import com.wanfeng.dingyuehao.domain.VO.MsgReq;
import com.wanfeng.dingyuehao.service.MsgSendService;
import com.wanfeng.dingyuehao.util.MsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

@Component("群发")
public class AllSendHandler implements CommandHandler{

    @Autowired
    private MsgSendService msgSendService;

    @Override
    public String handler(Matcher matcher, MsgReq msgReq) {
        return MsgUtils.buildReply(msgReq,"个人应用无法开启群发");
//        try {
//            String text = matcher.group(2);
//            String password = matcher.group(3);
//            if (password.equals("wanfeng")){
//                msgSendService.sendMsgToAllJustText(text);
//                return MsgUtils.buildReply(msgReq,"群发任务开始执行");
//            }else {
//                return MsgUtils.buildReply(msgReq,"密码错误，你谁?");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new RuntimeException("command");
//        }
    }

    @Override
    public String desc() {
        return "发送消息给所有关注者\n语法:群发 [文本] [密码]\n个人开发者目前不支持";
    }
}
