package com.inSoft.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {

    //短信默认配置
    @Value("${sms.default.type}")
    private String defaultType;

    //一定时间内可发送数量
    @Value("${sms.default.limit_count}")
    private Integer limitCount;

    //限制时间 此处为系统限制 如果设置后无效 请检查云端设置
    @Value("${sms.default.limit_time}")
    private Long limitTime;

    //短信有效时间
    @Value("${sms.default.expire_time}")
    private Long expireTime;

    public String getDefaultType() {
        return defaultType;
    }

    public Integer getLimitCount() {
        return limitCount;
    }

    public Long getLimitTime() {
        return limitTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

}
