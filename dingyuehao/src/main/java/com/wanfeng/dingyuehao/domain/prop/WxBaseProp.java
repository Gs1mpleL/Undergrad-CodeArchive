package com.wanfeng.dingyuehao.domain.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("wx")
public class WxBaseProp {
    private String token;
}
