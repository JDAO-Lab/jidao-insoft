package com.inSoft.enums;

/**
 * 下载类型 下载方式：0本地OSS 1阿里网盘 2蓝奏云 3小鸟云 4百度网盘
 */
public enum DownTypeEnum {
    LOCAL_OSS(0, "本地下载"), // 需验证
    ALI_NET_DISK(1, "阿里网盘"), // 公共的
    LANZOU_CLOUD(2, "蓝奏云"),
    NIAO_YUN(3, "小鸟云"),
    BAIDU_NET_DISK(4, "百度网盘");

    private int code;
    private String value;

    DownTypeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static DownTypeEnum getByCode(int code) {
        for (DownTypeEnum e : DownTypeEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}