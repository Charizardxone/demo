package com.example.listenerdemo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author yfc
 * @date 2023/4/10 15:59
 */
@Component
@Slf4j
public class ListenerHandler {


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handler(Event event){

        log.info("source: {} ", event.getSource());

    }
}
