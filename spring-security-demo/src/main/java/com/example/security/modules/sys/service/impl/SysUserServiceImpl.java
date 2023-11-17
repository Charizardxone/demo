package com.example.security.modules.sys.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.security.modules.sys.entity.SysUser;
import com.example.security.modules.sys.mapper.SysUserMapper;
import com.example.security.modules.sys.service.SysUserService;
import org.springframework.stereotype.Service;


@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Override
    public SysUser loadUserByUsername(String username) {
        SysUser sysUser = baseMapper.selectOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getName, username));
        return sysUser;
    }


}
