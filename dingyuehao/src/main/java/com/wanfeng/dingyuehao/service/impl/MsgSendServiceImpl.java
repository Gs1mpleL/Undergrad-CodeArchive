package com.wanfeng.dingyuehao.service.impl;

import com.wanfeng.dingyuehao.domain.VO.*;
import com.wanfeng.dingyuehao.feign.WxClient;
import com.wanfeng.dingyuehao.service.MsgSendService;
import com.wanfeng.dingyuehao.service.TokenService;
import com.wanfeng.dingyuehao.util.MsgUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MsgSendServiceImpl implements MsgSendService {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private WxClient wxClient;


    @Override
    public WxClientResq sendMsgToAll(MsgToAllReq msgToAllReq) {
        TokenResp token = tokenService.getToken();
        WxClientResq wxClientResq = wxClient.sendToAll(token.getAccess_token(), msgToAllReq);
        log.info("消息群发开始 状态[{}]",wxClientResq.getErrmsg());
        return wxClientResq;
    }

    @Override
    public WxClientResq sendMsgToAllJustText(String text) {
        MsgToAllReq msgToAllReq = new MsgToAllReq();
        SendUserFilter sendUserFilter = new SendUserFilter();
        sendUserFilter.set_to_all(false);
        sendUserFilter.setTag_id(100L);
        msgToAllReq.setFilter(sendUserFilter);
        msgToAllReq.setText(new SendText(text));
        msgToAllReq.setMsgtype(MsgUtils.REQ_MESSAGE_TYPE_TEXT);
        return sendMsgToAll(msgToAllReq);
    }

    @Override
    public String buildText() {
        return "牛逼！！！";
    }

}
