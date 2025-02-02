package com.wanfeng.dingyuehao.component.commandCallBack;

import com.wanfeng.dingyuehao.domain.Enum.PaymentEnum;
import com.wanfeng.dingyuehao.domain.VO.MsgReq;

import com.wanfeng.dingyuehao.domain.VO.PaymentReq;
import com.wanfeng.dingyuehao.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.regex.Matcher;

import static com.wanfeng.dingyuehao.component.commandCallBack.SalaryHandler.insertAndReturnRecord;

@Component("消费")
public class PaymentHandler implements CommandHandler{

    @Autowired
    private PaymentService paymentService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String handler(Matcher matcher, MsgReq msgReq) {
        PaymentReq paymentReq = new PaymentReq();
        paymentReq.setUserid(msgReq.getFromUserName());
        paymentReq.setType(PaymentEnum.Pay.getType());
        return insertAndReturnRecord(matcher, msgReq, paymentService, paymentReq);
    }

    @Override
    public String desc() {
        return "添加一条消费记录\n消费 [金额/本日/本月/全部]";
    }
}
