package com.inSoft.utils;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import com.inSoft.config.WxPayConfig;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


public class WxPayUtils {

    private static WXPay wxPay;
    private static String NOTIFY_URL = WxPayConfig.NOTIFY_URL;
    private static String MCH_ID = WxPayConfig.MCH_ID;

    // 初始化 WXPay
    static {
        try {
            Map<String, Object> configMap = new HashMap<>();
            configMap.put("appId", WxPayConfig.APP_ID);
            configMap.put("apiKey", WxPayConfig.API_KEY);
            configMap.put("mchId", WxPayConfig.MCH_ID);
            configMap.put("certPath", WxPayConfig.CERT_PATH);
            configMap.put("httpConnectTimeoutMs", WxPayConfig.HTTP_CONNECT_TIMEOUT_MS);
            configMap.put("httpReadTimeoutMs", WxPayConfig.HTTP_READ_TIMEOUT_MS);
            DeBugUtils.println("微信支付配置: " + JsonUtils.toJson(configMap));
            // 检测是否缺少配置参数
            if (configMap.containsValue(null) || configMap.containsValue("")) {
                throw new RuntimeException("微信支付配置不完整");
            }

            // 初始化 wxPay
            wxPay = new WXPay(new MyWXPayConfig(configMap));
        } catch (Exception e) {
            throw new RuntimeException("初始化微信支付失败", e);
        }
    }

    // 自定义 WXPayConfig 实现 配置信息
    private static class MyWXPayConfig implements WXPayConfig {
        private final Map<String, Object> configMap;

        public MyWXPayConfig(Map<String, Object> configMap) {
            this.configMap = configMap;
        }

        @Override
        public String getAppID() {
            return (String) configMap.get("appId");
        }

        @Override
        public String getMchID() {
            return (String) configMap.get("mchId");
        }

        @Override
        public String getKey() {
            return (String) configMap.get("apiKey");
        }

        @Override
        public InputStream getCertStream() {
            return getClass().getResourceAsStream((String) configMap.get("certPath"));
        }

        @Override
        public int getHttpConnectTimeoutMs() {
            return (Integer) configMap.get("httpConnectTimeoutMs");
        }

        @Override
        public int getHttpReadTimeoutMs() {
            return (Integer) configMap.get("httpReadTimeoutMs");
        }
    }

    // 创建订单
    public static String createOrder(String outTradeNo, String body, String totalFee) throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("out_trade_no", outTradeNo);
        data.put("body", body);
        data.put("total_fee", totalFee);
        data.put("spbill_create_ip", getIpAddress());
        data.put("notify_url", NOTIFY_URL);
        data.put("trade_type", "NATIVE"); // NATIVE 支付方式生成二维码

        Map<String, String> response = wxPay.unifiedOrder(data);
        DeBugUtils.println("微信订单提交参数: " + JsonUtils.toJson(data));
        if ("SUCCESS".equals(response.get("return_code")) && "SUCCESS".equals(response.get("result_code"))) {
            DeBugUtils.println("生成订单成功: " + JsonUtils.toJson(response));
            // 将 JSON 对象转换为字符串返回
            return JsonUtils.toJson(response);
        } else {
            throw new RuntimeException("生成微信支付订单失败: " + response.get("return_msg"));
        }
    }

    // 获取客户端 IP 地址
    private static String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    // 监听支付结果
    public static boolean listenForPaymentResult(Map<String,Object> xmlData) throws Exception {
        DeBugUtils.println("微信支付回调参数: " + JsonUtils.toJson(xmlData));
        if ("SUCCESS".equals(xmlData.get("return_code")) && "SUCCESS".equals(xmlData.get("result_code"))) {
            // 支付成功，处理业务逻辑
            System.out.println("支付成功: " + xmlData);
            return true;
        } else {
            System.out.println("支付失败: " + xmlData.get("err_code_des"));
            return false;
        }
    }

    // 处理退款
    public static boolean handleRefund(String outTradeNo, String refundFee) throws Exception {
        Map<String, String> data = new HashMap<>();
        data.put("out_trade_no", outTradeNo);
        data.put("refund_fee", refundFee);
        data.put("total_fee", refundFee); // 根据实际情况填写
        data.put("op_user_id", MCH_ID); // 填写商户号

        Map<String, String> response = wxPay.refund(data);
        if ("SUCCESS".equals(response.get("return_code")) && "SUCCESS".equals(response.get("result_code"))) {
            // 退款成功，更新退款状态
            System.out.println("退款成功: " + response);
            return true;
        } else {
            System.out.println("退款失败: " + response.get("err_code_des"));
            return false;
        }
    }
}
