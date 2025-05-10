package com.inSoft.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class PayConfig {

    public static String DEFAULT_PAY_TYPE;

    public static boolean ALL_TYPE_ENABLE;

    @Value("${pay.setting.default_pay_type}")
    private String defaultPayType;

    @Value("${pay.setting.all_type_enable}")
    private boolean allTypeEnable;

    @PostConstruct
    public void init() {
        DEFAULT_PAY_TYPE = defaultPayType;
        ALL_TYPE_ENABLE = allTypeEnable;
    }

    public String getDefaultPayType() {
        return defaultPayType;
    }

    public boolean isAllTypeEnable() {
        return allTypeEnable;
    }

}
