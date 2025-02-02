package com.wanfeng.dingyuehao.component.commandCallBack;

import com.wanfeng.dingyuehao.domain.VO.MsgReq;
import com.wanfeng.dingyuehao.service.NoteService;
import com.wanfeng.dingyuehao.util.MsgUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.regex.Matcher;

@Slf4j
@Component("记录")
public class NoteHandler implements CommandHandler{

    @Autowired
    private NoteService noteService;

    @Override
    public String handler(Matcher matcher, MsgReq msgReq) {
        String text = matcher.group(2);

        try {
            Date date = DateUtils.parseDate(text, "yyyy-MM-dd");
            return MsgUtils.buildReply(msgReq,noteService.search(msgReq,date));
        }catch (Exception e){
            log.info("记录日志，不是查询日志");
        }
        if (noteService.add(text,msgReq)){
            return MsgUtils.buildReply(msgReq,"记录成功!\n["+text+"]");
        }else {
            return MsgUtils.buildReply(msgReq,"记录失败!");
        }
    }

    @Override
    public String desc() {
        return "记录一些文本到后台\n记录 [要记录的内容/日期(2022-01-01)] 填入日期返回当日的日志";
    }
}
