package com.fc.feignInterface;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author yfc
 * @date 2023/3/27 11:13
 */
// 此处的只为提供要调用服务的spring.application.name
@FeignClient("service-provider")
public interface ProviderClient {

    /**
     *  此处的值为调用的接口Url
     */
    @GetMapping("hello")
    String hello();
}
