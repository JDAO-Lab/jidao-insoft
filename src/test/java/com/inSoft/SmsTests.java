package com.inSoft;

import com.inSoft.pojo.SmsCode;
import com.inSoft.service.SmsCodeService;
import com.inSoft.utils.AliSmsUtils;
import com.inSoft.utils.TencentSmsUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class SmsTests {

    @Autowired
    private AliSmsUtils smsUtils;

    @Autowired
    private TencentSmsUtils tencentSmsUtils;

    @Autowired
    private SmsCodeService smsCodeService;

    @Test
    void getConfig(){
        System.out.println(smsUtils.getAccessKey());
        System.out.println(smsUtils.getSecretKey());
        System.out.println(smsUtils.getEndpoint());
        System.out.println(smsUtils.getSignName());
        System.out.println(smsUtils.getTemplateCode());
    }

    @Test
    void testAliSendCode() throws Exception {
        smsUtils.sendCode("18511092960", "8886");
    }

    @Test
    void testAliSaveCode(){
        SmsCode smsCode = new SmsCode(null,"18511092960","8886",new Date(),"127.0.0.1",1);
        boolean save = smsCodeService.save(smsCode);
        System.out.println(save);
    }

    @Test
    void testTencentSendCode() throws Exception {
        tencentSmsUtils.sendCode("18511092960", "8886");
    }

    @Test
    void testCheckLimit(){
        boolean b = smsCodeService.checkLimit("18511092960", 1);
        System.out.println("检测是否超出限制："+b);
    }

    @Test
    void testCheckCode(){
        boolean b = smsCodeService.checkCode("18511092960", "103158", 1);
        System.out.println("检测验证码是否正确："+b);
    }

    @Test
    void testUsedCode(){
        boolean b = smsCodeService.usedCode("18511092960", "103158", 1);
        System.out.println("使用验证码："+b);
    }

}
