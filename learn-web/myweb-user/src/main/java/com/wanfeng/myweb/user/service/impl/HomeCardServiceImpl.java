package com.wanfeng.myweb.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfeng.myweb.user.entity.HomeCardEntity;
import com.wanfeng.myweb.user.mapper.HomeCardMapper;
import com.wanfeng.myweb.user.service.HomeCardService;
import org.springframework.stereotype.Service;

@Service
public class HomeCardServiceImpl extends ServiceImpl<HomeCardMapper, HomeCardEntity> implements HomeCardService {
}
