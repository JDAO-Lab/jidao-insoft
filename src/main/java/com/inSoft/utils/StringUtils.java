package com.inSoft.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class StringUtils {

    /**
     * 纯净字符串函数，过滤特殊符号和空格
     * @param theString
     * @return
     */
    public static String pureString(String theString){
        // 去掉特殊字符和空格
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(theString);
        theString = matcher.replaceAll("");
        return theString;
    }

    /**
     * 判断字符串是否为空
     * @param str 待检查的字符串
     * @return 如果字符串为null或其长度为0，返回true；否则返回false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 生成指定位数的随机数字验证码
     * @param length
     * @return
     */

    public static String getRandomCode(int length) {
        String code = "";
        for (int i = 0; i < length; i++) {
            int num = (int) (Math.random() * 10);
            code += num;
        }
        return code;
    }

    /**
     * 生成指定位数的随机数字
     */
    public static String getRandomNumber(int length) {
        String code = "";
        for (int i = 0; i < length; i++) {
            int num = (int) (Math.random() * 10);
            code += num;
        }
        return code;
    }

    /**
     * 生成唯一的订单号
     */
    public static String getOutTradeNo() {
        // 当前时间戳（精确到毫秒）
        long currentTimeMillis = System.currentTimeMillis();
        //转为当前日期 格式yyyymmddhhiiss
        String date = DateUtils.getDateByMillis(currentTimeMillis, "yyyyMMddHHmmss");
        // 随机数（0-999999之间）
        int randomNum = (int) (Math.random() * 999999);
        // 格式化为指定位数
        String formattedRandomNum = String.format("%04d", randomNum);
        // 拼接订单号
        return ""+ date + formattedRandomNum;
    }

    // 获取微信所需的totalFee
    public static String getTotalFee(String amount) {
        try {
            double amountDouble = Double.parseDouble(amount);
            int amountInCents = (int) Math.round(amountDouble);
            return String.valueOf(amountInCents);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("无效的金额格式: " + amount, e);
        }
    }

    //获取xml数据
    public static Map<String, Object> getXmlData(HttpServletRequest request) throws Exception {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        String line;
        while ((line = reader.readLine()) != null) {
                sb.append(line);
        }
        String xmlData = sb.toString();
        DeBugUtils.println("接收到的 XML 数据: " + xmlData);
        //xml转为map数据
        return XmlUtils.xmlToMap(xmlData);
    }

}
