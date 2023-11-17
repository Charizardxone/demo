package com.example.security.auth.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.security.auth.detail.CustomUserDetailsUser;
import com.example.security.common.FcResult;
import com.example.security.define.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token;
        String userId = "";
        String userName = "";
        if (authentication.getPrincipal() instanceof CustomUserDetailsUser) {
            CustomUserDetailsUser userDetailsUser = (CustomUserDetailsUser) authentication.getPrincipal();
            token = SecureUtil.md5(userDetailsUser.getUsername() + System.currentTimeMillis());
            userId = userDetailsUser.getUserId();
            userName = userDetailsUser.getUsername();
        } else {
            token = SecureUtil.md5(String.valueOf(System.currentTimeMillis()));
        }
        // 保存token
        redisTemplate.opsForValue().set(Constant.AUTHENTICATION_TOKEN + token, userId + "," + userName, Constant.TOKEN_EXPIRE, TimeUnit.SECONDS);
        log.info("用户ID:{},用户名:{},登录成功！  token:{}", userId, userName, token);


        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter printWriter = response.getWriter();
        Map<String, String> data = new HashMap<String, String>(){{
           put(Constant.TOKEN, token);
        }};
        printWriter.append(FcResult.success("", data).toString());
    }
}
