package com.wanfeng.dingyuehao.domain.VO;

import lombok.Data;

@Data
public class WxClientResq {
    private Integer errcode;
    private String errmsg;
    private Object data;
}
