package com.inSoft;

import com.google.gson.JsonObject;
import com.inSoft.utils.JsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JsonUtilTests {

    @Test
    public void testJsonUtils(){
        // 创建json对象
        JsonObject jsonObject = JsonUtils.createJsonObject();
        jsonObject.addProperty("name", "Alice");
        jsonObject.addProperty("age", 30);

        // 将json对象转为json字符串
        String jsonString = JsonUtils.jsonToString(jsonObject);
        System.out.println("Json String: " + jsonString);

        // 将字符串转为json对象
        JsonObject newJsonObject = JsonUtils.stringToJson(jsonString);
        System.out.println("Name: " + newJsonObject.get("name").getAsString());
        System.out.println("Age: " + newJsonObject.get("age").getAsInt());
    }
}
