package com.example.security.auth;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.security.auth.detail.CustomUserDetailsService;
import com.example.security.common.FcResult;
import com.example.security.define.Constant;
import com.example.security.define.ResultCodeEnum;
import com.example.security.utils.SpringContextUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求过滤器
 */
@Slf4j
public class AuthenticationTokenIntercept extends BasicAuthenticationFilter {

    private RedisTemplate redisTemplate;
    private AuthIgnoreConfig authIgnoreConfig;
    private ObjectMapper objectMapper = new ObjectMapper();

    public AuthenticationTokenIntercept(AuthenticationManager authenticationManager, AuthIgnoreConfig authIgnoreConfig, RedisTemplate template) {
        super(authenticationManager);
        this.redisTemplate = template;
        this.authIgnoreConfig = authIgnoreConfig;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(Constant.TOKEN);
        if (StrUtil.isBlank(token) || StrUtil.equals(token, "null")) {
            token = request.getParameter(Constant.TOKEN);
        }

        if (StrUtil.isNotBlank(token) && !StrUtil.equals(token, "null")) {
            final String requestURI = request.getRequestURI();
            if(!authIgnoreConfig.isContains(requestURI)){
                Object userInfo = redisTemplate.opsForValue().get(Constant.AUTHENTICATION_TOKEN + token);
                if (ObjectUtil.isNull(userInfo)) {
                    writer(response, "无效token");
                    return;
                }
                String user[] = userInfo.toString().split(",");
                if (user == null || user.length != 2) {
                    writer(response, "无效token");
                    return;
                }

                String userId = user[0];
                CustomUserDetailsService customUserDetailsService = SpringContextUtils.getBean(CustomUserDetailsService.class);
                UserDetails userDetails = customUserDetailsService.loadUserByUserId(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }


    @SneakyThrows
    public void writer(HttpServletResponse response, String msg) {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        response.getWriter().write(objectMapper.writeValueAsString(new FcResult<String>(ResultCodeEnum.NOT_LOGIN.getCode(), null, msg)));
    }
}
