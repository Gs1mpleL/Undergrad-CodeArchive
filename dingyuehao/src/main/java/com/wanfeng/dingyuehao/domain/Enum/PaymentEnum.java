package com.wanfeng.dingyuehao.domain.Enum;

import lombok.Data;


public enum PaymentEnum {

    Pay(1),Salary(2);

    private final int type;

    PaymentEnum(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
