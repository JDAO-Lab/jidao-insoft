package com.inSoft.utils;

import com.inSoft.config.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

@Component
@Slf4j
public class RedisUtil {

    private static class JedisPoolHolder {
        private static final JedisPoolConfig poolConfig = new JedisPoolConfig();

        //线程池配置
        static {
            poolConfig.setMaxTotal(RedisConfig.MAX_ACTIVE); // 最大连接数
            poolConfig.setMaxIdle(RedisConfig.MAX_IDLE); // 最大空闲连接数
            poolConfig.setMinIdle(RedisConfig.MIN_IDLE); // 最小空闲连接数
            poolConfig.setMaxWaitMillis(RedisConfig.MAX_WAIT); // 获取连接的最大等待时间
        }

        // 延迟到实例化时获取配置数据
        private static final JedisPool INSTANCE = new JedisPool(
                poolConfig,
                RedisConfig.HOST,   // 动态获取
                RedisConfig.PORT,   // 动态获取
                RedisConfig.TIMEOUT,
                RedisConfig.PASSWORD,
                RedisConfig.DATABASE
        );
    }

    public static JedisPool getJedisPool() {
        return JedisPoolHolder.INSTANCE;
    }


    /**
     * 生成带有前缀的键
     * @param key 键
     * @return 带有前缀的完整键
     */
    public String prefixedKey(String key) {
        return RedisConfig.KEY_PREFIX + "_" + key;
    }

    /**
     * 设置键值对，支持对象类型和过期时间
     * @param key 键
     * @param value 值，可以是字符串或任何可序列化的对象
     * @param expireTime 过期时间（秒）
     */
    public void set(String key, Object value, int expireTime) {
        try (Jedis jedis = getJedisPool().getResource()) {
            // 将对象序列化为JSON字符串
            String serializedValue = JsonUtils.toJson(value);
            // 设置键值对及过期时间
            String prefixedKey = prefixedKey(key);
            jedis.setex(prefixedKey, expireTime, serializedValue);
        } catch (Exception e) {
            log.error("设置Redis键值对失败", e);
        }
    }

    public void set(String key, String value, int expireTime) {
        try (Jedis jedis = getJedisPool().getResource()) {
            // 设置键值对及过期时间
            String prefixedKey = prefixedKey(key);
            jedis.setex(prefixedKey, expireTime, value);
        } catch (Exception e) {
            log.error("设置Redis键值对失败", e);
        }
    }

    public void set(String key, Object value) {
        try (Jedis jedis = getJedisPool().getResource()) {
            // 将对象序列化为JSON字符串
            String serializedValue = JsonUtils.toJson(value);
            // 设置键值对
            String prefixedKey = prefixedKey(key);
            jedis.set(prefixedKey, serializedValue);
        } catch (Exception e) {
            log.error("设置Redis键值对失败", e);
        }
    }

    public void set(String key, String value) {
        try (Jedis jedis = getJedisPool().getResource()) {
            String prefixedKey = prefixedKey(key);
            jedis.set(prefixedKey, value);
        } catch (Exception e) {
            log.error("设置Redis键值对失败", e);
        }
    }

    // 删除
    public void del(String key) {
        try (Jedis jedis = getJedisPool().getResource()) {
            String prefixedKey = prefixedKey(key);
            jedis.del(prefixedKey);
        } catch (Exception e) {
            log.error("删除Redis键值对失败", e);
        }
    }

    // 查询
    public String get(String key) {
        try (Jedis jedis = getJedisPool().getResource()) {
            String prefixedKey = prefixedKey(key);
            return jedis.get(prefixedKey);
        } catch (Exception e) {
            log.error("查询Redis键值对失败", e);
            return "";
        }
    }

    /**
     * 获取对象数据
     * @param key 键
     * @param clazz 对象类型
     * @param <T> 泛型
     * @return 对象
     */
    public <T> T get(String key, Class<T> clazz) {
        try (Jedis jedis = getJedisPool().getResource()) {
            String prefixedKey = prefixedKey(key);
            String jsonObj = jedis.get(prefixedKey);
            if (jsonObj != null && !jsonObj.isEmpty()) {
                return JsonUtils.jsonToObject(jsonObj, clazz);
            }
            return null;
        } catch (Exception e) {
            log.error("获取Redis对象失败", e);
            return null;
        }
    }

    /**
     * 读取并转为map对象
     */
    public Map<String, Object> getMap(String key) {
        try (Jedis jedis = getJedisPool().getResource()) {
            String prefixedKey = prefixedKey(key);
            String jsonObj = jedis.get(prefixedKey);
            if (jsonObj != null && !jsonObj.isEmpty()) {
                return JsonUtils.jsonToMap(jsonObj);
            }
            return null;
        } catch (Exception e) {
            log.error("读取Redis map失败", e);
            return null;
        }
    }

    /**
     * 判断redis中是否真实存在某一个key
     */
    public boolean exists(String key) {
        try (Jedis jedis = getJedisPool().getResource()) {
            String prefixedKey = prefixedKey(key);
            return jedis.exists(prefixedKey);
        } catch (Exception e) {
            log.error("判断Redis键值对是否存在失败", e);
            return false;
        }
    }
}
