package com.wanfeng.dingyuehao.domain.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ConfigurationProperties("command")
public class CommandProp {
    List<String> regex;
}
