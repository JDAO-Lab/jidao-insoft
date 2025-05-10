package com.inSoft.enums;

/**
 * 启用状态枚举
 */
public enum IsEnableEnum {
    NO(0, "禁用"),
    YES(1, "启用");

    private int code;
    private String value;

    IsEnableEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static IsEnableEnum getByCode(int code) {
        for (IsEnableEnum e : IsEnableEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}