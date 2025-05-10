package com.inSoft.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class AliSmsConfig {

    @Value("${alicloud.sms.access-key}")
    private String accessKey;

    @Value("${alicloud.sms.secret-key}")
    private String secretKey;

    @Value("${alicloud.sms.endpoint}")
    private String endpoint;

    @Value("${alicloud.sms.sign-name}")
    private String signName;

    @Value("${alicloud.sms.template-code}")
    private String templateCode;

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getEndpoint() {
        return endpoint;
    }

    //希望获取到的签名不是乱码
    public String getSignName() {
        return signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

}
