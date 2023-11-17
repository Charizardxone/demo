package com.example.security.utils;

import com.example.security.auth.detail.CustomUserDetailsUser;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 获取登录用户工具类
 */
public final class UserUtil {

    public static CustomUserDetailsUser getUser() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(object != null){
            return (CustomUserDetailsUser) object;
        }
        return null;
    }

    @SneakyThrows
    public static String getUserId() {
        return getUser() == null ? null :getUser().getUserId();
    }

}
