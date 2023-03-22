package com.fc.servicea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author yfc
 * @date 2023/3/22 10:20
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServicebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicebApplication.class);
    }
}
