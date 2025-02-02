package com.wanfeng.dingyuehao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanfeng.dingyuehao.domain.VO.MsgReq;
import com.wanfeng.dingyuehao.entity.NoteEntity;

import java.util.Date;

public interface NoteService extends IService<NoteEntity> {
    boolean add(String text, MsgReq msgReq);

    String search(MsgReq msgReq, Date date);
}
