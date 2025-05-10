package com.inSoft;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.inSoft.utils.AliPayUtils;
import com.inSoft.utils.DeBugUtils;
import com.inSoft.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class PayTests {

    @Test
    public void aliPayTest() throws AlipayApiException {
         String privateKey = "";
          String alipayPublicKey = "";
          AlipayConfig alipayConfig = new AlipayConfig();
          alipayConfig.setServerUrl("");
          alipayConfig.setAppId("");
          alipayConfig.setPrivateKey(privateKey);
          alipayConfig.setFormat("json");
          alipayConfig.setAlipayPublicKey(alipayPublicKey);
          alipayConfig.setCharset("UTF-8");
          alipayConfig.setSignType("RSA2");
          AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);
          AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
          AlipayTradePagePayModel model = new AlipayTradePagePayModel();
          model.setOutTradeNo("");
          model.setTotalAmount("88.88");
          model.setSubject("Iphone6 16G");
          model.setProductCode("FAST_INSTANT_TRADE_PAY");
          request.setBizModel(model);
          request.setReturnUrl("http://www.insoft.com/");
          request.setNotifyUrl("http://www.insoft.com/");
          AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "POST");
          // 如果需要返回GET请求，请使用
          // AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "GET");
          String pageRedirectionData = response.getBody();
          System.out.println(pageRedirectionData);
          if (response.isSuccess()) {
              System.out.println("调用成功");
          } else {
              System.out.println("调用失败");
              // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
          }
    }

    //使用工具获取表单数据
    @Test
    public void aliPayTest2() throws AlipayApiException {
         try {
            String response = AliPayUtils.createOrder(
                "ORDER_20150320010101001",
                "88.88",
                "Iphone6 16G"
            );
            Map<String,Object> res = JsonUtils.jsonToMap(response);
            DeBugUtils.println(res.get("body"));
        } catch (AlipayApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
             throw new RuntimeException(e);
         }
    }

}
