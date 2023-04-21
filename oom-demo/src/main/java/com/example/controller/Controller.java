package com.example.controller;

import com.example.domain.SysUser;
import com.example.service.MsgService;
import com.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author yfc
 * @date 2023/4/11 10:54
 */
@Slf4j
@RestController
@RequestMapping
public class Controller {

    private static List<Object> list = new ArrayList<>();

    private final UserService userService;

    private final Map<String, MsgService> msgMap;

    private final MsgService msgService;

    public Controller(UserService userService, MsgService textMsgService, Map<String, MsgService> msgMap){
        this.userService = userService;
        this.msgService = textMsgService;
        this.msgMap = msgMap;
    }


    @GetMapping("oomTest")
    public String oomTest() {

        for (int i = 0; i < 900000000; i++) {
            list.add(new Object());
        }
        return "ss";
    }

    @PostMapping("user/save")
    public Object saveUser() {

        for (int j = 0; j < 200; j++) {
            List<SysUser> userList = new ArrayList<>();
            for (int i = 0; i < 10000; i++) {
                SysUser user = new SysUser();
                user.setInsertTime(new Date());
                user.setName(j + "name" + i);
                user.setAge(i);
                user.setPhone("12199996666");
                userList.add(user);
            }

            userService.batchSave(userList);
        }

        return "保存成功";
    }


    @GetMapping("user/list")
    public Object listUser()  {
        return userService.listUser();
    }

    @GetMapping("user/list1")
    public Object listUser1() {
        return userService.listUser1();
    }


    @PostMapping("user")
    public Object save() {
        SysUser sysUser = new SysUser();
        sysUser.setName("qqqq");
        return userService.save(sysUser);
    }


}
