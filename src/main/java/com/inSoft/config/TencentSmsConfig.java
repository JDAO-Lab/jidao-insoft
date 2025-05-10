package com.inSoft.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TencentSmsConfig {


    @Value("${tencentcloud.sms.secret_id}")
    private String secretId;

    @Value("${tencentcloud.sms.secret_key}")
    private String secretKey;

    @Value("${tencentcloud.sms.sign}")
    private String sign;

    @Value("${tencentcloud.sms.template_id}")
    private String templateId;

    @Value("${tencentcloud.sms.endpoint}")
    private String endpoint;

    @Value("${tencentcloud.sms.region}")
    private String region;

    @Value("${tencentcloud.sms.sdk_app_id}")
    private String sdkAppId;


    public String getSecretId() {
        return secretId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getSign() {
		return sign;
	}

    public String getTemplateId() {
		return templateId;
	}

    public String getEndpoint() {
		return endpoint;
	}

    public String getRegion() {
		return region;
	}

    public String getSdkAppId() {
		return sdkAppId;
	}
}
