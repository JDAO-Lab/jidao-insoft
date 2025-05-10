package com.inSoft;

import com.inSoft.config.RedisConfig;
import com.inSoft.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisUtilTests {

    @Autowired
    private RedisUtil redisUtil;


    @Autowired
    private RedisConfig redisConfig;

    @Test
    public void testSetRedis(){
        redisUtil.set("ren", "1000000000000000000000000000");
    }

    @Test
    public void testGetRedis(){
        String one = redisUtil.get("ren");
        System.out.print("获取get参数："+one);
    }

    @Test
    public void testIsRedis(){
       boolean isTrue = redisUtil.exists("ren");
       System.out.print("判断是否存在："+isTrue);
    }

    @Test
    public void testDelRedis(){
       redisUtil.del("ren");
    }

    @Test
    public void testGetConfig(){
        System.out.println("地址："+redisConfig.getHost());
        System.out.println("端口："+redisConfig.getPort());
        System.out.println("前缀："+redisConfig.getKeyPrefix());
        System.out.println("数据库："+redisConfig.getDatabase());
        System.out.println("密码："+redisConfig.getPassword());
        System.out.println("超时时间："+redisConfig.getTimeout());
    }

    //获取常量
    @Test
    public void testGetConstant(){
        System.out.println("地址："+RedisConfig.HOST);
        System.out.println("端口："+RedisConfig.PORT);
        System.out.println("前缀："+RedisConfig.KEY_PREFIX);
        System.out.println("数据库："+RedisConfig.DATABASE);
        System.out.println("密码："+RedisConfig.PASSWORD);
        System.out.println("超时时间："+RedisConfig.TIMEOUT);
    }


}
