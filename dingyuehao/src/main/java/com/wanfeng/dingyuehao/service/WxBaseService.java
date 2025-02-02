package com.wanfeng.dingyuehao.service;

import com.wanfeng.dingyuehao.domain.VO.MsgReq;

import com.wanfeng.dingyuehao.domain.VO.WxBaseVo;

public interface WxBaseService {
    boolean check(WxBaseVo wxBaseVo);

    Object handleText(MsgReq msgReq);

    Object handleImage(MsgReq msgFromUser);
}
