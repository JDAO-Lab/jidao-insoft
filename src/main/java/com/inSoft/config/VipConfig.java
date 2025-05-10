package com.inSoft.config;

import com.inSoft.pojo.WebUsersVipser;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VipConfig {

    public static final String FREE_VIP_NAME = "免费会员";
    //增值服务限制次数 免费vip
    public static Integer FREE_VIP_LIMIT_NUM = 50;//次数 每使用一次增加reids 而后比对这个参数

    //限制周期
    public static Integer FREE_VIP_LIMIT_TIME = 30;//单位：天  同redis缓存时间

    //免费vip权限参数 格式为 名称：状态 1允许 0不允许
    public static String FREE_VIP_PRIVILEGE = "二次开发授权:0,商用开发授权:0,二次授权资质:0,系统源代码:0,专属客服:0,是否永久有效:0";

    //免费版用户使用次数记录key
    public static String FREE_VIP_RECORD_KEY = "free_vip_record";//用于redis存储

}
