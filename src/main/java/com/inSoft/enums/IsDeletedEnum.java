package com.inSoft.enums;

/**
 * 删除状态枚举类
 * 备注：所有数据不进行真实删除，所有数据的删除状态都在这个枚举中！
 */
public enum IsDeletedEnum {

    // 未删除状态 正常类型
    NOT_DELETED(0, "未删除"),
    // 已删除状态 可恢复类型，开发了恢复能力即可进行操作，如果没有则可数据库中进行操作恢复
    DELETED(1, "已删除"),
    // 已清除 只存在数据库，不显示在任何地方，必要时在数据库内进行恢复
    NON_EXISTENT(2, "已清除");

    private final int code;
    private final String value;

    IsDeletedEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static IsDeletedEnum getByCode(int code) {
        for (IsDeletedEnum e : IsDeletedEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}
