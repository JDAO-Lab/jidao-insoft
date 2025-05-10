package com.inSoft.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

public class MapUtils {

    //循环map 根据key 输出 所需的内容 转为字符串
    public static String get(Map<String, Object> map, String key) {
        String value = "";
        if (map.containsKey(key)) {
            value = map.get(key).toString();
        }
        return value;
    }

    /**
     * 根据输入的key和类型输出指定类型参数值 任意
     */
    public static <T> T get(Map<String, Object> map, String key, Class<T> clazz) {
        if (map.containsKey(key)) {
            Object value = map.get(key);

            if (clazz.isInstance(value)) {
                return clazz.cast(value);
            } else {
                try {
                    if (clazz == Integer.class || clazz == int.class) {
                        return clazz.cast(Integer.parseInt(value.toString()));
                    } else if (clazz == Double.class || clazz == double.class) {
                        return clazz.cast(Double.parseDouble(value.toString()));
                    } else if (clazz == Float.class || clazz == float.class) {
                        return clazz.cast(Float.parseFloat(value.toString()));
                    } else if (clazz == Long.class || clazz == long.class) {
                        return clazz.cast(Long.parseLong(value.toString()));
                    } else if (clazz == Boolean.class || clazz == boolean.class) {
                        return clazz.cast(Boolean.parseBoolean(value.toString()));
                    } else if (clazz == BigDecimal.class) {
                        return clazz.cast(new BigDecimal(value.toString()));
                    } else if (clazz == String.class) {
                        return clazz.cast(value.toString());
                    } else if (clazz == Date.class) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        return clazz.cast(sdf.parse(value.toString()));
                    } else if (clazz == LocalDate.class) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        return clazz.cast(LocalDate.parse(value.toString(), formatter));
                    } else if (clazz == LocalDateTime.class) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        return clazz.cast(LocalDateTime.parse(value.toString(), formatter));
                    } else {
                        // 对于其他类型，直接返回null
                        System.err.println("不支持的类型: " + clazz.getName());
                        return null;
                    }
                } catch (NumberFormatException | ParseException e) {
                    // 如果转换失败，打印错误信息并返回null
                    System.err.println("类型转换失败: " + key + " 的值无法转换为 " + clazz.getName());
                    return null;
                }
            }
        }
        return null;
    }

}
