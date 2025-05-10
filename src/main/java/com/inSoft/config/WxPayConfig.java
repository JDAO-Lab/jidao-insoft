package com.inSoft.config;

import com.github.wxpay.sdk.WXPayConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.InputStream;


@Configuration
public class WxPayConfig {


    public static String APP_ID;
    public static String API_KEY;
    public static String MCH_ID;
    public static String CERT_PATH;
    //httpConnectTimeoutMs
    public static int HTTP_CONNECT_TIMEOUT_MS;
    public static int HTTP_READ_TIMEOUT_MS;
    public static String NOTIFY_URL;

    @Value("${wxpay.appId}")
    private String appId;
    @Value("${wxpay.apiKey}")
    private String apiKey;
    @Value("${wxpay.mchId}")
    private String mchId;
    @Value("${wxpay.certPath}")
    private String certPath;
    private InputStream certStream;
    @Value("${wxpay.httpConnectTimeoutMs}")
    private Integer httpConnectTimeoutMs;
    @Value("${wxpay.httpReadTimeoutMs}")
    private Integer httpReadTimeoutMs;
    @Value("${wxpay.notifyUrl}")
    private String notifyUrl;

    @PostConstruct
    public void init() {
        APP_ID = appId;
        API_KEY = apiKey;
        MCH_ID = mchId;
        CERT_PATH = certPath;
        HTTP_CONNECT_TIMEOUT_MS = httpConnectTimeoutMs;
        HTTP_READ_TIMEOUT_MS = httpReadTimeoutMs;
        NOTIFY_URL = notifyUrl;
    }

    public String getAppId() {
        return appId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getMchId() {
        return mchId;
    }

    public String getCertPath() {
        return certPath;
    }


    public InputStream getCertStream() {
        return certStream;
    }


    public Integer getHttpConnectTimeoutMs() {
        return httpConnectTimeoutMs;
    }


    public Integer getHttpReadTimeoutMs() {
        return httpReadTimeoutMs;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }
}
