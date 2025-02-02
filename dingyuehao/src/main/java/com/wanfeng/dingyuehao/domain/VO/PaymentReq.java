package com.wanfeng.dingyuehao.domain.VO;



import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentReq {

    private String userid;

    private BigDecimal amount;

    private int type;
}
