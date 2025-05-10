// RedisConfig.java
package com.inSoft.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RedisConfig {

    //声明常量
    public static String HOST;
    public static int PORT;
    public static String PASSWORD;
    public static int DATABASE;
    public static int TIMEOUT;
    public static String KEY_PREFIX;
    public static int MAX_ACTIVE;
    public static int MAX_IDLE;
    public static int MIN_IDLE;
    public static int MAX_WAIT;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.key-prefix}")
    private String keyPrefix;

    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.lettuce.pool.max-wait}")
    private int maxWait;


    @PostConstruct
    public void init() {
        HOST = host.isEmpty() ? null : host;
        PORT = port;
        PASSWORD = password.isEmpty()? null : password;
        DATABASE = database;
        TIMEOUT = timeout;
        KEY_PREFIX = keyPrefix.isEmpty() ? null :keyPrefix;
        MAX_ACTIVE = maxActive;
        MAX_IDLE = maxIdle;
        MIN_IDLE = minIdle;
        MAX_WAIT = maxWait;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public Integer getDatabase() {
        return database;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

}