package com.wanfeng.dingyuehao.domain.VO;

import lombok.Data;

@Data
public class WxBaseVo {
    private String signature;
    private String timestamp;
    private String nonce;
    private String echostr;
}
