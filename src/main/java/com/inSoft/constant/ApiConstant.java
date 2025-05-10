package com.inSoft.constant;

public class ApiConstant {

    public final static String PATH_PREFIX = "/api";

    //appId 用于access_token 解析与 应用端 appid 对比
    public final static String APP_ID = "insoft_2024";

    //appKey 用于access_token 解析与 应用端 appkey 进行对比
    public final static String APP_KEY = "hzmHCfv@BOM$4LLc";

    //签名 用于加密明文使用
    public final static String SIGN = "sign_qR3qtC*&ge8Ncs6L";

    //有效期 单位秒
    public final static Long EXPIRE_TIME = 1800L;

    //验证登录和令牌错误情况反馈提示
    public static final String LOGIN_AND_TOKEN_ERROR = "登录状态或令牌失效！";

}
