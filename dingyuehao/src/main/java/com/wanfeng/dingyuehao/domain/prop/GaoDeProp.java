package com.wanfeng.dingyuehao.domain.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties("gaode")
public class GaoDeProp {
    private String key;
    private List<String> cityAndCode;
}
