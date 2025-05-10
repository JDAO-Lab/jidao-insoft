package com.inSoft;

import com.inSoft.utils.ApiUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class ApiTests {

    //生成accesstoken及解析
    @Test
    public void testAccessToken(){
        String access_token = ApiUtils.createAccessToken();
        System.out.println("access_token:"+access_token);
        Map<String, Object> map = ApiUtils.parseAccessToken(access_token);
        System.out.println("map:"+map);
        //验证access_token
        boolean b = ApiUtils.checkAccessToken(access_token);
        System.out.println("b:"+b);
    }

}
