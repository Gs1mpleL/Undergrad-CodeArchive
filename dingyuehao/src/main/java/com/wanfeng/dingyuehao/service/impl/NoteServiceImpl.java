package com.wanfeng.dingyuehao.service.impl;

import cn.hutool.core.date.DateUtil;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfeng.dingyuehao.domain.VO.MsgReq;
import com.wanfeng.dingyuehao.domain.template.StringTemplate;
import com.wanfeng.dingyuehao.entity.NoteEntity;
import com.wanfeng.dingyuehao.mapper.NoteMapper;
import com.wanfeng.dingyuehao.service.NoteService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, NoteEntity> implements NoteService {
    @Override
    public boolean add(String text, MsgReq msgReq) {
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setText(text);
        noteEntity.setUserid(msgReq.getFromUserName());
        noteEntity.setTime(new Date());
        return baseMapper.insert(noteEntity) == 1;
    }

    @Override
    public String search(MsgReq msgReq, Date date) {
        List<NoteEntity> list = new LambdaQueryChainWrapper<>(baseMapper)
                .eq(NoteEntity::getUserid, msgReq.getFromUserName())
                .ge(NoteEntity::getTime, DateUtil.beginOfDay(date))
                .le(NoteEntity::getTime, DateUtil.endOfDay(date))
                .list();
        String format = DateUtil.format(date, "yyyy-MM-dd");
        if (CollectionUtils.isEmpty(list)){
            return format+"->无记录";
        }else {
            String collect = list.stream().map(i -> {
                Date time = i.getTime();
                String text = i.getText();
                String hour = DateUtil.format(time, "HH:mm:ss");
                return "["+hour+"] " + text;
            }).collect(Collectors.joining("\n"));
            return String.format(StringTemplate.NoteRecord, format, collect);
        }
    }
}
