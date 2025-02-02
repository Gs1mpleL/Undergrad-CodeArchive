package com.wanfeng.dingyuehao.component.commandCallBack;

import com.wanfeng.dingyuehao.domain.VO.MsgReq;
import com.wanfeng.dingyuehao.util.MsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.regex.Matcher;

@Component
public class CommandSelector {

    @Autowired
    Map<String, CommandHandler> handlerMap;

    public String select(Matcher matcher, MsgReq msgReq){
        if (handlerMap.get(matcher.group(1)) == null) {
            return MsgUtils.buildReply(msgReq,"当前语法无处理器，请联系管理员");
        }else {
            try {
                return handlerMap.get(matcher.group(1)).handler(matcher,msgReq);
            }catch (Exception e){
                e.printStackTrace();
                String format = "语法解析错误，请输入[帮助]或[帮助 %s]查看语法规则";
                MsgUtils.buildReply(msgReq,String.format(format,handlerMap.get(matcher.group(1))));
            }
        }
        return MsgUtils.buildReply(msgReq,"未知错误，请联系管理员");
    }
}
