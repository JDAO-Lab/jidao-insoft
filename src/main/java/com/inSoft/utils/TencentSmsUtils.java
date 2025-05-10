package com.inSoft.utils;

import com.inSoft.config.TencentSmsConfig;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TencentSmsUtils {

    @Autowired
    private TencentSmsConfig tenSmsConfig;


    public String getSecretId() {
        return tenSmsConfig.getSecretId();
    }

    public String getSecretKey() {
        return tenSmsConfig.getSecretKey();
    }

    public String getSign() {
        return tenSmsConfig.getSign();
    }

    public String getTemplateId() {
        return tenSmsConfig.getTemplateId();
    }

    public String getEndpoint() {
        return tenSmsConfig.getEndpoint();
    }

    public String getRegion() {
        return tenSmsConfig.getRegion();
    }

    public String getSdkAppId() {
        return tenSmsConfig.getSdkAppId();
    }

    /**
     * 实例化连接认证对象
     * @return client
     */
    public SmsClient createClient() throws TencentCloudSDKException {
        Credential cred = new Credential(getSecretId(), getSecretKey());
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(getEndpoint());
        httpProfile.setConnTimeout(10); // 请求连接超时时间，单位为秒

        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        return new SmsClient(cred, getRegion(), clientProfile);
    }


    /**
     * 发送短信验证码
     * @param phone 接收短信的手机号码
     * @param code 短信模板参数
     * @return boolean
     */
    public boolean sendCode(String phone, String code) throws TencentCloudSDKException {
        SmsClient client = createClient();
        SendSmsRequest req = new SendSmsRequest();
        req.setSmsSdkAppid(getSdkAppId());
        req.setSign(getSign());
        req.setTemplateID(getTemplateId());
        req.setTemplateParamSet(new String[]{code});
        req.setPhoneNumberSet(new String[]{phone});
        SendSmsResponse res = client.SendSms(req);
        // 解析发送状态
        SendStatus sendStatus = res.getSendStatusSet()[0];
        // 记录日志
        log.info("短信发送结果: {}", SendSmsResponse.toJsonString(res));
        return sendStatus.getCode().equals("Ok");
    }


}
