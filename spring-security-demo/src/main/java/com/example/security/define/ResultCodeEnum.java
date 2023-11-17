package com.example.security.define;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 返回值状态码
 * @author yfc
 * @date 2022/10/19 10:13
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {

    CODE_SUCCESS("0000", "成功"),
    CODE_FAIL("9999", "失败"),

    CODE_ERROR("9000", "错误"),
    USER_NOTFOUND("9001", "用户不存在"),
    USERNAME_OR_PASSWORD_ERROR("9002", "用户名或密码错误"),
    ACCOUNT_LOCK("9003", "账户被锁定"),
    LOGIN_ERROR_NUM_EXCEEDED("9004", "登录失败次数超过系统最大次数"),
    NOT_LOGIN("9998", "尚未登录或登录已过期");


    private String code;
    private String name;

}
