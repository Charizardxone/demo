package com.fc.controller;

import com.fc.service.OrderService;
import com.fc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yfc
 * @date 2023/4/4 14:29
 */
@RestController
@RequestMapping("")
public class Controller {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping("hi/{name}")
    public Object hi(@PathVariable String name){
        userService.cache1(name);
        orderService.cache2(name);
        return "hi!" + name;

    }

}
