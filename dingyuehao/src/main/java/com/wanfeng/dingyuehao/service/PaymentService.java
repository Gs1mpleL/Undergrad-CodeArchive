package com.wanfeng.dingyuehao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wanfeng.dingyuehao.domain.VO.PaymentReq;
import com.wanfeng.dingyuehao.domain.VO.PaymentResp;
import com.wanfeng.dingyuehao.entity.PaymentEntity;


public interface PaymentService extends IService<PaymentEntity> {
    boolean addPayment(PaymentReq paymentReq);

    PaymentResp getCurDay(String userId);
    PaymentResp getCurMonth(String userId);
    PaymentResp getAll(String userId);

    boolean delOne(PaymentReq paymentReq);
}
