package com.wanfeng.dingyuehao.component.commandCallBack;

import com.wanfeng.dingyuehao.domain.VO.MsgReq;
import com.wanfeng.dingyuehao.domain.prop.CommandProp;
import com.wanfeng.dingyuehao.util.MsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

@Component("帮助")
public class HelpHandler implements CommandHandler{
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private CommandProp commandProp;
    @Override
    public String handler(Matcher matcher, MsgReq msgReq) {
        if (msgReq.getContent().length()==2){
            String collect = "目前支持的语法有:\n------------------\n";
            collect += String.join("\n", commandProp.getRegex());
            collect = collect.replace("(", "");
            collect = collect.replace(")","");
            collect = collect.replace("*","");
            collect = collect.replace(".","");
            collect = collect.replace(".*","[xx]");
            collect += "\n-----------------\n你也可以输入 帮助 [具体命令] 来查看命令的详细介绍";
            return MsgUtils.buildReply(msgReq,collect);
        }else {
            String commandToDesc = msgReq.getContent().substring(3);
            CommandHandler bean = (CommandHandler) applicationContext.getBean(commandToDesc);
            return MsgUtils.buildReply(msgReq,bean.desc());
        }

    }

    @Override
    public String desc() {
        return "查看所有命令或具体命令的介绍";
    }
}
