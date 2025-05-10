package com.inSoft.utils;

import com.inSoft.config.LoginConfig;

import java.util.HashMap;
import java.util.Map;

public class LoginUtils {

    /**
     * 获取有效登录时长
     */
    public static long getExpireTime(Integer remember) {
        Long expire = LoginConfig.getLoginJwtExpire();
        if (remember>0){
            //选中后默认时间为30天
            Integer remeberDay = LoginConfig.getRemeberDay();
            expire = 2*expire*remeberDay; // 过期时间为30天，单位为毫秒
        }
        return expire;
    }

    /**
     * timeOut
     */
    public static Integer getTimeOut(Integer remember) {
        Integer timeOut = LoginConfig.getLoginTimeout();
        if (remember>0){
            //选中后默认时间为30天
            Integer remeberDay = LoginConfig.getRemeberDay();
            timeOut = 2*timeOut*remeberDay;
        }
        return timeOut;
    }

    /**
     * 生成web用户的token
     */
    public static String getWebUserToken(Integer id,String username,String phone,String avatar){
        //生成token自动登录
        long expire = LoginUtils.getExpireTime(0);
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("username",username);
        claims.put("phone", phone);
        claims.put("avatar",avatar);
        //生成jwt令牌
        String jwt = JwtUtils.generateJwt(claims,expire);
        //MD5加密更新用户token，验证时将cookies中的jwt进行加密匹配数据
        String newToken = MD5Util.md5(jwt);
        return newToken;
    }

    /**
     * 生成管理员用户的token
     */
    public static String getAdminUserToken(Integer id, String username, Integer ruleId, Integer isDeleted, String avatar, long expire){
        //组成登录数据
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("username", username);
        claims.put("rule_id", ruleId);
        claims.put("is_deleted", isDeleted);
        claims.put("avatar",avatar);
        //生成jwt令牌
        String jwt = JwtUtils.generateJwt(claims,expire);
        //MD5加密更新用户token，验证时将cookies中的jwt进行加密匹配数据
        String newToken = MD5Util.md5(jwt);
        return newToken;
    }

    /**
     * 对比密码
     */
    public static boolean checkPassword(String password,String md5){
        if (password==null || md5==null){
            return false;
        }
        String newMd5 = encodePassword(password);
        return newMd5.equals(md5);
    }

    /**
     * 加密密码
     */
    public static String encodePassword(String password){
        return MD5Util.md5(password);
    }

}
