package com.wanfeng.dingyuehao.domain.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("wx.user")
public class TokenProp {
    private String grant_type;
    private String appid;
    private String secret;
}
