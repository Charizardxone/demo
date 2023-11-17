package com.example.security.modules.sys.controller;

import com.example.security.common.FcResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * 登录相关
 *
 * @author czx
 * @email object_czx@163.com
 */
@Slf4j
@RestController
public class SysLoginController {


    @GetMapping(value = "/")
    public FcResult hello(String account) {
        return FcResult.success("hello welcome" + account, null);
    }


    @GetMapping("auth/login")
    public FcResult login(){
        return FcResult.success("login success");
    }


    /**
     * 退出
     */
//    @AuthIgnore
    @GetMapping(value = "/sys/logout")
    public void logout() {

    }
}
