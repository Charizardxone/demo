package com.fc.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yfc
 * @date 2023/3/22 11:13
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String url = request.getURI().getPath();
        log.info("url: {}", url);

        // 可在此处做一些如 令牌校验、登录校验等工作

        return chain.filter(exchange);
    }

    /**
     * 可自定义多个GlobalFilter，根据order顺序执行，order越小优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
