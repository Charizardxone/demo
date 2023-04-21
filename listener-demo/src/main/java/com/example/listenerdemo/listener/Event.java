package com.example.listenerdemo.listener;

import org.springframework.context.ApplicationEvent;

/**
 * @author yfc
 * @date 2023/4/10 15:58
 */
public class Event extends ApplicationEvent {


    public Event(Object source) {
        super(source);
    }
}
