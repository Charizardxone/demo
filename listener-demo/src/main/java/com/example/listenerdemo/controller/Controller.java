package com.example.listenerdemo.controller;

import com.example.listenerdemo.listener.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yfc
 * @date 2023/4/10 15:51
 */
@RestController
@RequestMapping
public class Controller {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private static List<String> list = new ArrayList<>();

    @GetMapping("hello")
    public String hello(){

        applicationEventPublisher.publishEvent(new Event("hello world"));

        return "hello world";
    }

    @GetMapping("oom")
    public void oomTest(){

        while (true){
            list.add("oomString");
        }

    }



}
