package com.example.service;

import com.example.domain.SysUser;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * @author yfc
 * @date 2023/4/17 10:45
 */
public interface UserService {
    boolean batchSave(List<SysUser> userList);

    int listUser();

    int listUser1();

    int save(SysUser user);
}
