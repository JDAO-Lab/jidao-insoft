package com.inSoft.enums;

/**
 * 会员状态枚举
 * 状态: 激活(1)、未激活(0)、已过期(2)
 */
public enum VipserStatusEnum {
    INACTIVE(0, "未激活"),
    ACTIVE(1, "激活中"),
    EXPIRED(2, "已过期");

    private int code;
    private String value;

    VipserStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static VipserStatusEnum getByCode(int code) {
        for (VipserStatusEnum e : VipserStatusEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}