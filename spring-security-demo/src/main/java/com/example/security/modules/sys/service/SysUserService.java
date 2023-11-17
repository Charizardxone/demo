package com.example.security.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.security.modules.sys.entity.SysUser;

/**
 * 系统用户
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2016年9月18日 上午9:43:39
 */
public interface SysUserService extends IService<SysUser>{

    SysUser loadUserByUsername(String username);

}
