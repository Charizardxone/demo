package com.fc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yfc
 * @date 2023/3/27 11:11
 */
@RestController
@RequestMapping("")
public class Controller {

    @GetMapping("hello")
    public String hello(){
        return "provider: hello hello hello!";
    }

}
