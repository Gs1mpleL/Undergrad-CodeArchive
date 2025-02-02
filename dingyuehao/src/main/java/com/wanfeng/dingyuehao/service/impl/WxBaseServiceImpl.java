package com.wanfeng.dingyuehao.service.impl;

import com.wanfeng.dingyuehao.component.eventCallback.EventHandlerSelector;
import com.wanfeng.dingyuehao.component.commandCallBack.CommandSelector;
import com.wanfeng.dingyuehao.domain.VO.MsgReq;
import com.wanfeng.dingyuehao.domain.VO.WxBaseVo;
import com.wanfeng.dingyuehao.domain.prop.CommandProp;
import com.wanfeng.dingyuehao.domain.prop.WxBaseProp;
import com.wanfeng.dingyuehao.service.WxBaseService;
import com.wanfeng.dingyuehao.util.MsgUtils;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class WxBaseServiceImpl implements WxBaseService {
    @Autowired
    private WxBaseProp wxBaseProp;

    @Autowired
    private CommandProp commandProp;
    @Autowired
    private EventHandlerSelector callBackEventHandler;
    @Autowired
    private CommandSelector commandSelector;
    private List<Pattern> patterns;


    public void init(){
        patterns = new ArrayList<>();
        for (String regex : commandProp.getRegex()) {
            Pattern compile = Pattern.compile(regex);
            patterns.add(compile);
            log.info("语法解析[{}]已生效",regex);
        }
    }
    @Override
    public boolean check(WxBaseVo wxBaseVo) {
        String[] arr = new String[]{wxBaseProp.getToken(), wxBaseVo.getTimestamp(), wxBaseVo.getNonce()};
        Arrays.sort(arr);
        String str = arr[0]+arr[1]+arr[2];
        str = DigestUtils.sha1Hex(str);
        return str.equalsIgnoreCase(wxBaseVo.getSignature());
    }

    @Override
    public Object handleText(MsgReq msgReq) {
        if (!StringUtil.isNullOrEmpty(msgReq.getEvent())){
            log.info("触发事件[{}]",msgReq.getEvent());
            // 事件处理器选择器
            callBackEventHandler.select(msgReq);
            return null;
        }
        return replyUser(msgReq);
    }
    @Value("${image.path}")
    private String path;

    @Override
    public Object handleImage(MsgReq msgFromUser) {
        String pathInOs  = path +new Date().getTime()+".jpg";
        MsgUtils.urlImage2String(msgFromUser.getPicUrl(),pathInOs);
        return MsgUtils.buildReply(msgFromUser,"保存地址：["+pathInOs+"]");
    }

    /**
     * 回复用户
     */
    private String replyUser(MsgReq msgReq) {
        if (CollectionUtils.isEmpty(patterns)){
            init();
        }
        for (Pattern pattern : patterns) {
            log.info(pattern.toString()+"->"+msgReq.getContent());
            Matcher matcher = pattern.matcher(msgReq.getContent());
            if (matcher.find()){
                String command = matcher.group(1);
                log.info("当前命令 [{}]",command);
                return commandSelector.select(matcher, msgReq);
            }
        }
        return MsgUtils.buildReply(msgReq,"语法无法解析\n输入帮助查看支持语法");
    }
}
