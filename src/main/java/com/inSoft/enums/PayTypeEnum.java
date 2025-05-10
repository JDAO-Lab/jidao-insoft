package com.inSoft.enums;

/**
 * 订单支付类型枚举
 *  1微信 2支付宝
 */
public enum PayTypeEnum {
    WECHAT_PAY(1, "微信支付"),
    ALIPAY(2, "支付宝支付");

    private int code;
    private String value;

    PayTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static PayTypeEnum getByCode(int code) {
        for (PayTypeEnum e : PayTypeEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}