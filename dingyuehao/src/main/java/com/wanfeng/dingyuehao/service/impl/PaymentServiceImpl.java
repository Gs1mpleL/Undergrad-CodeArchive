package com.wanfeng.dingyuehao.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanfeng.dingyuehao.domain.Enum.PaymentEnum;
import com.wanfeng.dingyuehao.domain.VO.PaymentReq;
import com.wanfeng.dingyuehao.domain.VO.PaymentResp;
import com.wanfeng.dingyuehao.domain.rediskey.PaymentKey;
import com.wanfeng.dingyuehao.entity.PaymentEntity;
import com.wanfeng.dingyuehao.mapper.PaymentMapper;
import com.wanfeng.dingyuehao.service.PaymentService;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, PaymentEntity> implements PaymentService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Override
    public boolean addPayment(PaymentReq paymentReq) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setAmount(paymentReq.getAmount());
        paymentEntity.setTime(new Date());
        paymentEntity.setType(paymentReq.getType());
        paymentEntity.setUserid(paymentReq.getUserid());
        int insert = baseMapper.insert(paymentEntity);
        if (insert == 1) {
            String format = String.format(PaymentKey.payKey,paymentReq.getType(), paymentReq.getUserid());
            redisTemplate.opsForValue().set(format,String.valueOf(paymentEntity.getId()),120, TimeUnit.SECONDS);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public PaymentResp getCurDay(String userId) {
        List<PaymentEntity> list = new LambdaQueryChainWrapper<>(baseMapper)
                .eq(PaymentEntity::getUserid, userId)
                .ge(PaymentEntity::getTime, LocalDateTimeUtil.beginOfDay(LocalDateTime.now()))
                .le(PaymentEntity::getTime, LocalDateTimeUtil.endOfDay(LocalDateTime.now()))
                .list();

        return mapResp(list);
    }

    @Override
    public PaymentResp getCurMonth(String userId) {
        List<PaymentEntity> list = new LambdaQueryChainWrapper<>(baseMapper)
                .eq(PaymentEntity::getUserid, userId)
                .ge(PaymentEntity::getTime, LocalDateTimeUtil.beginOfDay(LocalDateTime.now().withDayOfMonth(1)))
                .le(PaymentEntity::getTime, LocalDateTimeUtil.endOfDay(LocalDateTime.now()))
                .list();

        return mapResp(list);
    }

    @Override
    public PaymentResp getAll(String userId) {
        List<PaymentEntity> list = new LambdaQueryChainWrapper<>(baseMapper)
                .eq(PaymentEntity::getUserid, userId)
                .list();
        return mapResp(list);
    }

    @Nullable
    private static PaymentResp mapResp(List<PaymentEntity> list) {
        PaymentResp paymentResp = new PaymentResp();
        paymentResp.setPay(new BigDecimal(0));
        paymentResp.setSalary(new BigDecimal(0));
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            list.forEach(i -> {
                // 消费
                if (i.getType() == PaymentEnum.Pay.getType()) {
                    paymentResp.setPay(paymentResp.getPay().add(i.getAmount()));
                } else if (i.getType() == PaymentEnum.Salary.getType()) {
                    paymentResp.setSalary(paymentResp.getSalary().add(i.getAmount()));
                }
            });
            paymentResp.setRemain(paymentResp.getSalary().subtract(paymentResp.getPay()));
            return paymentResp;
        }
    }

    @Override
    public boolean delOne(PaymentReq paymentReq) {
        String format = String.format(PaymentKey.payKey,paymentReq.getType(), paymentReq.getUserid());
        String s = redisTemplate.opsForValue().get(format);
        if (s == null){
            return false;
        }else{
            removeById(Long.parseLong(s));
            return true;
        }
    }

}
