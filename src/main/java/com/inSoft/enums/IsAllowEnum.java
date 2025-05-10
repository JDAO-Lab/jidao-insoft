package com.inSoft.enums;

/**
 * 是否允许通行
 */
public enum IsAllowEnum {
    NO(0, "需验证"),
    YES(1, "公共的");

    private int code;
    private String value;

    IsAllowEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static IsAllowEnum getByCode(int code) {
        for (IsAllowEnum e : IsAllowEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}