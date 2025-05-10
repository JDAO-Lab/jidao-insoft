package com.inSoft.enums;

/**
 * 系统日志访问状态枚举
 */
public enum SysLogResultEnum {
    UNKNOWN(0, "未知"), // 未知
    SUCCESS(1, "成功"), // 成功
    FAILURE(2, "失败"); // 失败

    private int code;
    private String value;

    SysLogResultEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static SysLogResultEnum getByCode(int code) {
        for (SysLogResultEnum e : SysLogResultEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}