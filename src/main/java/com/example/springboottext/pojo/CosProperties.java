package com.example.springboottext.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Cos配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "tencent.cos")
public class CosProperties {
 
    private String secretId;
    private String secretKey;
    private String bucketName;
    private String folder;
    private String region;
 
}