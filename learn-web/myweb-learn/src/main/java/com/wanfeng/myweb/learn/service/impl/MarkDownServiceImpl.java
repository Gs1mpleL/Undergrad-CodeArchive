package com.wanfeng.myweb.learn.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfeng.myweb.learn.entity.MarkDownEntity;
import com.wanfeng.myweb.learn.mapper.MarkDownMapper;
import com.wanfeng.myweb.learn.service.MarkDownService;
import org.springframework.stereotype.Service;

@Service
public class MarkDownServiceImpl extends ServiceImpl<MarkDownMapper, MarkDownEntity> implements MarkDownService {
}
