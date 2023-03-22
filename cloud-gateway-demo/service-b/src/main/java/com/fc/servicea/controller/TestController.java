package com.fc.servicea.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yfc
 * @date 2023/3/22 10:25
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("")
    public String get(){
        return "hello, service b!";
    }
}
