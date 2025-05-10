package com.inSoft.utils;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.inSoft.config.AliPayConfig;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

public class AliPayUtils {

    private static AlipayClient alipayClient;
    private static String RETURN_URL = AliPayConfig.ALIPAY_RETURN_URL;
    private static String NOTIFY_URL = AliPayConfig.ALIPAY_NOTIFY_URL;

    // 初始化 AlipayClient
    static {
        try{
            String serverUrl = AliPayConfig.SYSTEM_URL;
            String appId = AliPayConfig.ALIPAY_APP_ID;
            String format = AliPayConfig.ALIPAY_FORMAT;
            String charset = AliPayConfig.ALIPAY_CHARSET;
            String privateKey = FileUtils.readTxtFile(AliPayConfig.ALIPAY_PRIVATE_PATH);
            String publicKey = FileUtils.readTxtFile(AliPayConfig.ALIPAY_PUBLIC_PATH);
            String alipayPublicKey = FileUtils.readTxtFile(AliPayConfig.ALIPAY_ALIPAY_PUBLIC_PATH);
            String signType = AliPayConfig.ALIPAY_SIGN_TYPE;
            //检查配置信息
            if (StringUtils.isEmpty(serverUrl) || StringUtils.isEmpty(appId)
                    || StringUtils.isEmpty(privateKey)
                    || StringUtils.isEmpty(format) || StringUtils.isEmpty(charset)
                    || StringUtils.isEmpty(alipayPublicKey)
                    || StringUtils.isEmpty(publicKey)
                    || StringUtils.isEmpty(signType)) {
                throw new RuntimeException("支付宝配置不完整");
            }

            AlipayConfig alipayConfig = new AlipayConfig();
            alipayConfig.setServerUrl(serverUrl);
            alipayConfig.setAppId(appId);
            alipayConfig.setPrivateKey(privateKey);
            alipayConfig.setAlipayPublicKey(alipayPublicKey);
            alipayConfig.setCharset(charset);
            alipayConfig.setFormat(format);
            alipayConfig.setSignType(signType);

            alipayClient = new DefaultAlipayClient(alipayConfig );
        }catch (Exception e){
            throw new RuntimeException("初始化支付宝支付失败", e);
        }
    }


    // 创建订单 使用当面付获取二维码连接
   public static String createOrder(String outTradeNo, String subject, String totalAmount) throws Exception {
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(RETURN_URL);
        alipayRequest.setNotifyUrl(NOTIFY_URL);
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(outTradeNo);
        model.setTotalAmount(totalAmount);
        model.setSubject(subject);
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        alipayRequest.setBizModel(model);
        AlipayTradePagePayResponse response = alipayClient.pageExecute(alipayRequest, "POST");

        if (response.isSuccess()) {
            DeBugUtils.println("生成订单成功: " + JsonUtils.toJson(response));
            // 将 JSON 对象转换为字符串返回
            return JsonUtils.toJson(response);
        } else {
            throw new RuntimeException("生成订单失败: " + response.getSubMsg());
        }
    }




    // 监听订单支付结果
   public static boolean listenForPaymentResult(HttpServletRequest request) throws AlipayApiException {
    AlipayTradeQueryRequest queryRequest = new AlipayTradeQueryRequest();
    Map<String, String[]> params = request.getParameterMap();
    JSONObject bizContent = new JSONObject();

    for (String key : params.keySet()) {
        String[] values = params.get(key);
        if (values != null && values.length > 0) {
            bizContent.put(key, values[0]);
        }
    }

    queryRequest.setBizContent(bizContent.toJSONString());

    AlipayTradeQueryResponse response = alipayClient.execute(queryRequest);
    if (response.isSuccess()) {
        System.out.println("支付成功: " + response.getBody());
        return true;
    } else {
        System.out.println("支付失败: " + response.getBody());
        return false;
    }
}


    // 处理退款
    public static boolean handleRefund(String outTradeNo, String refundAmount) throws AlipayApiException {
        AlipayTradeRefundRequest refundRequest = new AlipayTradeRefundRequest();
        refundRequest.setBizContent("{" +
                "   \"out_trade_no\":\"" + outTradeNo + "\"," +
                "   \"refund_amount\":\"" + refundAmount + "\"}");
        refundRequest.setBizContent(refundRequest.getBizContent());
        DeBugUtils.println("退款请求参数: " + refundRequest.getBizContent());

        AlipayTradeRefundResponse refundResponse = alipayClient.execute(refundRequest);
        if (refundResponse.isSuccess()) {
            // 更新退款状态
            System.out.println("退款成功: " + refundResponse.getBody());
            return true;
        } else {
            System.out.println("退款失败: " + refundResponse.getBody());
            return false;
        }
    }
}
