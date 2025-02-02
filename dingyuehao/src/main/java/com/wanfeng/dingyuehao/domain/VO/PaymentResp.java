package com.wanfeng.dingyuehao.domain.VO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentResp {
    private String userid;
    private BigDecimal pay;
    private BigDecimal salary;
    private BigDecimal remain;
}
