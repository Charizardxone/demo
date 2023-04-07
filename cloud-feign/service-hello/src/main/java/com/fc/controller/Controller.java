package com.fc.controller;

import com.fc.feignInterface.ProviderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yfc
 * @date 2023/3/27 11:13
 */
@RestController
@RequestMapping
public class Controller {

    @Autowired
    private ProviderClient providerClient;


    @GetMapping("hi")
    public String hi(){
        return providerClient.hello();
    }
}
