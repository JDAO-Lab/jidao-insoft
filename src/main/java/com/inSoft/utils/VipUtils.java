package com.inSoft.utils;

import com.inSoft.config.VipConfig;
import com.inSoft.pojo.WebUsersVipser;

/**
 * 会员工具类
 */
public class VipUtils {

    /**
     * 判断会员是否过期
     */
    public static boolean isVipExpired(WebUsersVipser vipser) {
        return vipser.getEndTime().getTime() < System.currentTimeMillis();
    }

    /**
     * 判断是否时免费会员
     */
    public static boolean isFreeVip(WebUsersVipser vipser) {
        return vipser==null?true:false;
    }


}
