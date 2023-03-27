package com.fc.gateway.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关异常处理
 * @author yfc
 * @date 2023/3/22 14:39
 */
@Slf4j
@Order(-1)
@Configuration
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (exchange.getResponse().isCommitted()){
            return Mono.error(ex);
        }

        String msg;
        if (ex instanceof NotFoundException){
            msg = "服务未找到";
        }else if (ex instanceof ResponseStatusException){
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            msg = responseStatusException.getMessage();
        }else{
            msg = "内部服务器错误";
        }
        log.error("[网关异常处理]请求路径:{},异常信息:{}", exchange.getRequest().getPath(), ex.getMessage());

        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        DataBuffer dataBuffer = response.bufferFactory().wrap(msg.getBytes());
        return response.writeWith(Mono.just(dataBuffer));
    }

}
