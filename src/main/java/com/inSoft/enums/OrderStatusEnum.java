package com.inSoft.enums;

/**
 * 订单状态枚举
 * 0未支付 1已支付 2已取消
 */
public enum OrderStatusEnum {
    NOT_PAID(0, "未支付"),
    PAID(1, "已支付"),
    CANCELLED(2, "已取消"),
    REFUNDED(3, "已退款");

    private int code;
    private String value;

    OrderStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static OrderStatusEnum getByCode(int code) {
        for (OrderStatusEnum e : OrderStatusEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}