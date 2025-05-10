package com.inSoft.utils;

import com.inSoft.constant.ApiConstant;

import java.util.HashMap;
import java.util.Map;

public class ApiUtils {

    private static final String APP_ID = ApiConstant.APP_ID;
    private static final String APP_KEY = ApiConstant.APP_KEY;
    private static final String SIGN = ApiConstant.SIGN;
    private static final Long EXPIRE_TIME = ApiConstant.EXPIRE_TIME;

    //生成access_token
    public static String createAccessToken(){
        Map<String, Object> params = new HashMap<>();
        params.put("sign", SIGN);
        params.put("app_id", APP_ID);
        params.put("app_key", APP_KEY);
        Long timestamp = DateUtils.getCurrentTimestampInSeconds()+EXPIRE_TIME;
        params.put("timestamp", timestamp.toString());
        //jwt创建令牌
        return EncryptionUtils.encryption(JsonUtils.toJson(params), SIGN);
    }

    //解析access_token
    public static Map<String, Object> parseAccessToken(String access_token){
        return JsonUtils.jsonToMap(EncryptionUtils.decryption(access_token, SIGN));
    }

    //验证access_token
    public static boolean checkAccessToken(String access_token){
        //解析access_token
        Map<String, Object> map = parseAccessToken(access_token);
        String appId = MapUtils.get(map, "app_id");
        String appKey = MapUtils.get(map, "app_key");
        Long timestamp = MapUtils.get(map, "timestamp", Long.class);
        Long nowTime = DateUtils.getCurrentTimestampInSeconds();
        if (appId.equals(APP_ID)&&appKey.equals(APP_KEY)&&timestamp>=nowTime){
            return true;
        }
        return false;
    }

}
