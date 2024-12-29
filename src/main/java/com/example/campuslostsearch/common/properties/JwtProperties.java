package com.example.campuslostsearch.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    /**
     * 生成jwt令牌相关配置
     */
    private String secret;
    private long expiration;
    private String token;
}
