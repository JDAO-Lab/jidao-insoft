package com.inSoft.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Map;

public class JsonUtils {
    private static Gson gson = new Gson();


    /**
     * 转为json字符串
     * @param s
     * @return
     */
    public static String toJson(Object s) {
        return new Gson().toJson(s);
    }

    /**
     * 自定义创建json对象
     * 示例：
     *  // 创建json对象
     *         JsonObject jsonObject = JsonUtils.createJsonObject();
     *         jsonObject.addProperty("name", "Alice");
     *         jsonObject.addProperty("age", 30);
     * @return Object
     */
    public static JsonObject createJsonObject() {
        return new JsonObject();
    }

    /**
     * 将字符串转为json对象
     * 示例：
     * JsonObject newJsonObject = JsonUtils.stringToJson(jsonString);
     * @param jsonString
     * @return Json
     */
    public static JsonObject stringToJson(String jsonString) {
        return gson.fromJson(jsonString, JsonObject.class);
    }

    /**
     * 将json对象转为json字符串
     * 示例：
     * String jsonString = JsonUtils.jsonToString(jsonObject);
     * @param jsonObject
     * @return String
     */
    public static String jsonToString(JsonObject jsonObject) {
        return gson.toJson(jsonObject);
    }

    /**
     * 将java对象转为json字符串
     * 示例：
     * String json = JsonUtils.objectToJson(new Person("Bob", 25));
     * @param obj
     * @return String
     */
    public static String objectToJson(Object obj) {
        return gson.toJson(obj);
    }


    /**
     * 将json字符串转为java对象
     * @param jsonValue
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T jsonToObject(String jsonValue,  Class<T> clazz) {
        return gson.fromJson(jsonValue, clazz);
    }


    /**
     * 将json字符串转为map
     * @param jsonString
     * @return
     */
    public static Map<String, Object> jsonToMap(String jsonString) {
        return gson.fromJson(jsonString, Map.class);
    }

    /**
     * 转为map对象
     */
    public static Map<String, String> jsonToMap(String jsonString, Class<String> clazz) {
        return new Gson().fromJson(jsonString, Map.class);
    }
}