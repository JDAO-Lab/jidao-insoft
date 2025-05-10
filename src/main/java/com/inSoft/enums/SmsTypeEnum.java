package com.inSoft.enums;

/**
 * 类型
 * 1注册 2登录 3找回密码 4实名验证
 */
public enum SmsTypeEnum {
    REGISTER(1, "注册"),
    LOGIN(2, "登录"),
    FIND_PASSWORD(3, "找回密码"),
    REAL_NAME(4, "实名验证");

    private int code;
    private String value;

    SmsTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static SmsTypeEnum getByCode(int code) {
        for (SmsTypeEnum e : SmsTypeEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }

}
