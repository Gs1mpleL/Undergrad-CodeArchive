package com.wanfeng.dingyuehao.service;

import com.wanfeng.dingyuehao.domain.VO.MsgToAllReq;
import com.wanfeng.dingyuehao.domain.VO.WxClientResq;

public interface MsgSendService {
    WxClientResq sendMsgToAll(MsgToAllReq msgToAllReq);

    WxClientResq sendMsgToAllJustText(String text);

    String buildText();
}
