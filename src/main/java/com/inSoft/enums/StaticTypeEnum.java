package com.inSoft.enums;

/**
 * 统计类型枚举，在此处添加即可，数据库中无需修改注释
 * 枚举信息：
 * 0.浏览次数
 * 1.增加人数 当日注册用户
 * 2.活跃人数
 * 3.订单数目 含未付款-总数目
 * 4.付款人数 只有已付款的订单
 * 5.Api调用
 * 6.收入总额 付款完毕时进行累计相加
 */
public enum StaticTypeEnum {

    /**
     * 浏览次数
     */
    VIEW_COUNT(0, "浏览次数"),

    /**
     * 增加人数
     */
    INCREASED_USERS(1, "增加人数"),

    /**
     * 活跃人数
     */
    ACTIVE_USERS(2, "活跃人数"),

    /**
     * 订单数目（含未付款-总数目）
     */
    TOTAL_ORDERS(3, "订单数目"),

    /**
     * 付款人数（只有已付款的订单）
     */
    PAID_USERS(4, "付款人数"),

    /**
     * Api调用
     */
    API_CALLS(5, "Api调用"),

    /**
     * 收入总额（付款完毕时进行累计相加）
     */
    TOTAL_INCOME(6, "收入总额"),

    /**
     * 使用次数
     */
    PLAY_TOTAL_COUNT(7, "使用次数"),

    /**
     * 安装量
     */
    INSTALL_COUNT(8, "安装量");

    private final int code;
    private final String value;

    StaticTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static StaticTypeEnum getByCode(int code) {
        for (StaticTypeEnum e : StaticTypeEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}
