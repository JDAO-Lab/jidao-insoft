package com.inSoft.enums;

/**
 * 性别枚举
 */
public enum SexEnum {
    CONFIDENTIAL(0, "保密"),
    MALE(1, "男"),
    FEMALE(2, "女");

    private int code;
    private String value;

    SexEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static SexEnum getByCode(int code) {
        for (SexEnum e : SexEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}