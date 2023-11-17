package com.example.security.modules.sys.service.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.security.modules.sys.entity.SysUser;
import com.example.security.modules.sys.mapper.SysUserMapper;
import com.example.security.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统用户
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Override
    public SysUser loadUserByUsername(String username) {
        SysUser sysUser = baseMapper.selectOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getName, username));
        return sysUser;
    }


}
