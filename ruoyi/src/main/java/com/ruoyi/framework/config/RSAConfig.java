package com.ruoyi.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author:万登枫
 * @date: 2021/8/19
 */
@Component
@ConfigurationProperties(prefix = "rsa")
public class RSAConfig {

    private static String publicKey;

    private static String privateKey;

    public static String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        RSAConfig.publicKey = publicKey;
    }

    public static String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        RSAConfig.privateKey = privateKey;
    }
}
