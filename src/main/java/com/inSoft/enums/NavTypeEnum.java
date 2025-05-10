package com.inSoft.enums;

/**
 * 导航类型枚举
 * 1图文 2链接
 */
public enum NavTypeEnum {
    IMAGE_TEXT(1, "图文"),
    LINK(2, "链接");

    private int code;
    private String value;

    NavTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static NavTypeEnum getByCode(int code) {
        for (NavTypeEnum e : NavTypeEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}