package com.cpny.common.config;


import com.cpny.common.cache.Cache;
import com.cpny.common.cache.CacheRedisImpl;
import com.cpny.common.lock.DistributedLock;
import com.cpny.common.lock.DistributedLockRedissonImpl;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * redis配置
 */
//@Configuration
public class RedisConfigurtion {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Bean
    public RedisTemplate<String, Object> getRedisTemplate() {
        // 开发和测试环境使用JSON序列化，更加直观
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setDefaultSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);

        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        return redisTemplate;
    }


    /**
     * 加载缓存
     * @return
     */
    @Bean
    public Cache getCache() {
        return new CacheRedisImpl();
    }

    /**
     * 加载分布式锁框架
     * @return
     */
    @Bean
    public RedissonClient getRedisson(){

        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port).setPassword(password);

        return Redisson.create(config);
    }

    /**
     * 加载分布式锁
     * @return
     */
    @Bean
    public DistributedLock getDistributedLock() {
        return new DistributedLockRedissonImpl();
    }

}