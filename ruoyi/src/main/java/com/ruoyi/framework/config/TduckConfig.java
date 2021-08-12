package com.ruoyi.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author:万登枫
 * @date: 2021/8/12
 */
@Component
@ConfigurationProperties(prefix = "tduck")
public class TduckConfig {

    private static String platform;

    private static String loginUrl;

    private static String publicKey;

    public static String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        TduckConfig.platform = platform;
    }

    public static String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        TduckConfig.loginUrl = loginUrl;
    }

    public static String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        TduckConfig.publicKey = publicKey;
    }
}
