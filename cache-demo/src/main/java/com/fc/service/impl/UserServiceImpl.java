package com.fc.service.impl;

import com.fc.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author yfc
 * @date 2023/4/4 16:33
 */
@Service
@Slf4j
@CacheConfig(cacheNames = "cache1")
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Cacheable(key = "#name")
    public String cache1(String name) {

        return "hello!cache1" + name;
    }


}
