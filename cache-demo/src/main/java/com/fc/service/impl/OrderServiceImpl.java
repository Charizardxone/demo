package com.fc.service.impl;

import com.fc.service.OrderService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author yfc
 * @date 2023/4/7 10:00
 */
@Service
@CacheConfig(cacheNames = "cache2")
public class OrderServiceImpl implements OrderService {


    @Override
    @Cacheable(key = "#name")
    public String cache2(String name) {
        return "hello!cache2" + name;
    }
}
