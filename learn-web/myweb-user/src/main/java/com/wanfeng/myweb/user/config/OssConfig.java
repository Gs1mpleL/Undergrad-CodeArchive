package com.wanfeng.myweb.user.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OssConfig {
    @Autowired
    private OSSConfigurationProperties ossConfigurationProperties;

    @Bean
    public OSS getOssClient() {
        //获取相关配置
        String bucketName = ossConfigurationProperties.getBucketName();
        String endPoint = ossConfigurationProperties.getEndPoint();
        String accessKeyId = ossConfigurationProperties.getAccessKeyId();
        String accessKeySecret = ossConfigurationProperties.getAccessKeySecret();

        //创建OSS对象
        return new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
    }
}
