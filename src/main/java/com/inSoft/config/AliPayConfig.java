package com.inSoft.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class AliPayConfig {

    public static String SYSTEM_URL;
    public static String ALIPAY_NOTIFY_URL;
    public static String ALIPAY_RETURN_URL;
    public static String ALIPAY_APP_ID;
    public static String ALIPAY_PRIVATE_PATH;
    public static String ALIPAY_PUBLIC_PATH;
    public static String ALIPAY_FORMAT;
    public static String ALIPAY_CHARSET;
    public static String ALIPAY_ALIPAY_PUBLIC_PATH;
    public static String ALIPAY_SIGN_TYPE;


    @Value("${alipay.serverUrl}")
    private String serverUrl;

    @Value("${alipay.appId}")
    private String appId;

    @Value("${alipay.privatePath}")
    private String privatePath;

    @Value("${alipay.publicPath}")
    private String publicPath;

    @Value("${alipay.format}")
    private String format;

    @Value("${alipay.charset}")
    private String charset;

    @Value("${alipay.alipayPublicPath}")
    private String alipayPublicPath;

    @Value("${alipay.signType}")
    private String signType;

    @Value("${alipay.returnUrl}")
    private String returnUrl;

    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    @PostConstruct
    public void init() {
        ALIPAY_NOTIFY_URL = notifyUrl;
        ALIPAY_RETURN_URL = returnUrl;
        ALIPAY_APP_ID = appId;
        ALIPAY_PRIVATE_PATH = privatePath;
        ALIPAY_PUBLIC_PATH = publicPath;
        ALIPAY_FORMAT = format;
        ALIPAY_CHARSET = charset;
        ALIPAY_ALIPAY_PUBLIC_PATH = alipayPublicPath;
        ALIPAY_SIGN_TYPE = signType;
        SYSTEM_URL = serverUrl;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public String getAppId() {
        return appId;
    }

    public String getFormat() {
        return format;
    }

    public String getCharset() {
        return charset;
    }

    public String getSignType() {
        return signType;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public String getPrivatePath() {
        return privatePath;
    }

    public String getPublicPath() {
    	return publicPath;
    }

    public String getAlipayPublicPath() {
    	return alipayPublicPath;
    }

}
