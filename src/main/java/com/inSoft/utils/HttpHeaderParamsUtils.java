package com.inSoft.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class HttpHeaderParamsUtils {

    /**
     * 保存用户对象的ThreadLocal  在拦截器操作 添加、删除相关用户数据
     */
    private static final ThreadLocal<String> userThreadLocal = new ThreadLocal<String>();

    /**
     * 添加当前登录用户方法  在拦截器方法执行前调用设置获取用户
     * @param
     */
    public static void add(Map<String, String> params){
        userThreadLocal.set(JsonUtils.toJson(params));
    }

    /**
     * 获取当前登录用户方法
     */
    public static Map<String, String> get(){
        String str = userThreadLocal.get();
        if(str != null){
            return JsonUtils.jsonToMap(str, String.class);
        }
        return null;
    }

    /**
     * 删除当前登录用户方法  在拦截器方法执行后 移除当前用户对象
     */
    public static void remove(){
        userThreadLocal.remove();
    }

}
