package com.example.security.auth;

import com.example.security.auth.detail.CustomUserDetailsService;
import com.example.security.auth.handler.CustomAuthenticationFailHandler;
import com.example.security.auth.handler.CustomAuthenticationSuccessHandler;
import com.example.security.auth.handler.CustomLogoutSuccessHandler;
import com.example.security.auth.handler.TokenAuthenticationFailHandler;
import com.example.security.define.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * @author yfc
 * @date 2023/10/26 15:03
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurer  {


    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AuthIgnoreConfig authIgnoreConfig;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        List<String> permitAll = authIgnoreConfig.getIgnoreUrls();
        permitAll.add("/error");
        permitAll.add("/v3/**");
        permitAll.add("/swagger-ui/**");
        permitAll.add("/swagger-resources/**");
        permitAll.add(Constant.TOKEN_ENTRY_POINT_URL);
        permitAll.add(Constant.TOKEN_LOGOUT_URL);
        String[] urls = permitAll.stream().distinct().toArray(String[]::new);

        // 基于 token，不需要 csrf
        http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/**").permitAll();
        // 跨域配置
        http.cors().configurationSource(corsConfigurationSource());

        // 基于 token，不需要 session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 权限
        http.authorizeRequests(authorize ->
                // 开放权限
                authorize.antMatchers(urls).permitAll()
                        // 其他地址的访问均需验证权限
                        .anyRequest().authenticated());
        // 设置登录URL
        http.formLogin()
                .loginProcessingUrl(Constant.TOKEN_ENTRY_POINT_URL)
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler);
        // 设置退出URL
        http.logout()
                .logoutUrl(Constant.TOKEN_LOGOUT_URL)
                .logoutSuccessUrl("/mapper/sys/logout")
                .addLogoutHandler(customLogoutSuccessHandler);

        // 如果不用验证码，注释这个过滤器即可
//        http.addFilterBefore(new ValidateCodeFilter(redisTemplate, authenticationFailureHandler()), UsernamePasswordAuthenticationFilter.class);
        // token 验证过滤器
        http.addFilterBefore(new AuthenticationTokenIntercept(authenticationManager(), authIgnoreConfig, redisTemplate), UsernamePasswordAuthenticationFilter.class);
        // 认证异常处理
        http.exceptionHandling().authenticationEntryPoint(new TokenAuthenticationFailHandler());
        // 用户管理service
        http.userDetailsService(userDetailsService);
        return http.build();

    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(authenticationProvider()));
    }

    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }


    @Bean
    public CustomDaoAuthenticationProvider authenticationProvider() {
        CustomDaoAuthenticationProvider authenticationProvider = new CustomDaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }






}
