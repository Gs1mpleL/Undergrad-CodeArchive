package com.wanfeng.myweb.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "aliyun.oss")
@Configuration
@Data
public class OSSConfigurationProperties {

    private String endPoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

}