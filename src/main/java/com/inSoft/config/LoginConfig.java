package com.inSoft.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginConfig {
    //login登录配置信息
    public static Integer loginTimeout = 3600*12;//登录失效时间 单位秒 默认12小时
    public static Long loginJwtExpire = 12 * 60 * 60 * 1000L;//Jwt默认失效时间 单位毫秒 默认12小时
    public static Integer remeberDay = 30;//选中记住时的存储时长 单位天
    public static Integer errLockNum =5;//输入密码错误数，用于上锁
    public static Integer errLockHour =2;//单位小时，锁定时长
    public static String adminTokenName = "admin_token";//token名用于前台cookies存储的
    public static String userTokenName = "user_token";//token名用于前台cookies存储的


    public static Integer getLoginTimeout(){
        return loginTimeout;
    }

    public static Long getLoginJwtExpire(){
        return loginJwtExpire;
    }

    public static Integer getRemeberDay(){
        return remeberDay;
    }

    public static Integer getErrLockHour() {
        return errLockHour;
    }

    public static Integer getErrLockNum() {
        return errLockNum;
    }

    public static String getAdminTokenName() {
        return adminTokenName;
    }

    public static String getUserTokenName() {
        return userTokenName;
    }
}
