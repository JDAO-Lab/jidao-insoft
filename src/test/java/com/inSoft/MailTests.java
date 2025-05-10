package com.inSoft;

import com.inSoft.utils.MailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailTests {

    @Autowired
    private MailUtils mailUtils;

    @Test
    void testSendMail() throws Exception {
        mailUtils.sendSimpleEmail("861157525@qq.com", "测试邮件", "测试邮件");
    }
}
