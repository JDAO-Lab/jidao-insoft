package com.inSoft.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DeBugConfig {

    public static boolean ENABLED;
    public static boolean PRINT_ENABLED;

    @Value("${debug.enable}")
    private boolean enable;

    @Value("${debug.print.enable}")
    private boolean printEnable;

    @PostConstruct
    public void init() {
        ENABLED = enable;
        PRINT_ENABLED = printEnable;
    }

    public boolean isEnable() {
        return enable;
    }

    public boolean isPrintEnable() {
        return printEnable;
    }

}
