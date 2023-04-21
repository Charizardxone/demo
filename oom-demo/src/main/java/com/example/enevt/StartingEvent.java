package com.example.enevt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.*;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yfc
 * @date 2023/4/17 14:42
 */
@Slf4j
@Component
public class StartingEvent implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        for (String arg : args) {
            log.error(":::::::::   {}", arg);
        }
        log.error("::::::::::::::::::::CommandLineRunner");
    }
}
