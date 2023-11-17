package com.example.security.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.security.modules.sys.entity.SysUser;

public interface SysUserService extends IService<SysUser>{

    SysUser loadUserByUsername(String username);

}
