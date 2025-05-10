package com.inSoft.enums;

/**
 * 是否隐藏
 */
public enum IsHideEnum {
    SHOW(0, "显示"),
    HIDE(1, "隐藏");

    private int code;
    private String value;

    IsHideEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static IsHideEnum getByCode(int code) {
        for (IsHideEnum e : IsHideEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}