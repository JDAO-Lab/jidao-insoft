package com.inSoft.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {

    // 获取文件后缀名
    public static String getFileSuffix(String fileName) {
        if (fileName != null && fileName.lastIndexOf(".") != -1) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }

    // 检测文件类型是否在限制范围内
    public static boolean isFileTypeAllowed(String fileName, String allowedSuffixes) {
        String fileSuffix = getFileSuffix(fileName);
        if (allowedSuffixes.contains(fileSuffix)) {
            return true;
        }
        return false;
    }

    // 读取txt文件的文本内容返回字符串
    public static String readTxtFile(String filePath) {
        String result = "";
        DeBugUtils.println("读取文件路径：",filePath);
        try {
            // 使用 ClassPathResource 加载类路径下的文件
            ClassPathResource resource = new ClassPathResource(filePath);
            if (resource.exists()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
                result = FileCopyUtils.copyToString(reader);
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (IOException e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        DeBugUtils.println("读取出的文件内容：",result);
        return result;
    }

}