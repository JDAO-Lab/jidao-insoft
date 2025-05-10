package com.inSoft.utils;

import com.inSoft.config.DeBugConfig;

import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class DeBugUtils {


    private static PrintWriter writer;

    static {
        try {
            // 创建PrintWriter对象，使用UTF-8编码输出 仅在调试模式下才输出
            if (DeBugConfig.PRINT_ENABLED){
                writer = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"), true);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调试模式是否开启
     */
    public static boolean isEnable() {
        return DeBugConfig.ENABLED;
    }

    /**
     * 打印单个文本串
     * @param obj
     */
    public static void print(Object obj) {
        if (isEnable()){
            writer.println(obj);
        }
    }


    /**
     * 打印调试信息串
     * @param obj
     */
    public static void printBock(Object obj) {
        if (isEnable()) {
            writer.println("=== 调试信息 ===");
            writer.println(obj);
            writer.println("===================");
        }
    }

    /**
     * 打印多个对象每行一条
     */
    public static void println(Object... objs) {
        if (isEnable()) {
            for (Object obj : objs) {
                writer.println(obj);
            }
        }
    }

    /**
     * 打印多个对象 调试串信息
     * @param objs
     */
    public static void printlnBlock(Object... objs) {
        if (isEnable()) {
            writer.println("=== 调试信息 ===");
            for (Object obj : objs) {
                writer.println(obj);
            }
            writer.println("===================");
        }
    }

}