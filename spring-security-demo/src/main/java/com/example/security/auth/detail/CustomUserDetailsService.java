package com.example.security.auth.detail;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.security.modules.sys.entity.SysUser;
import com.example.security.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private SysUserService sysUserService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.loadUserByUsername(username);
        if (ObjectUtil.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return getDetail(sysUser);
    }



    private UserDetails getDetail(SysUser sysUser) {
//        Set<String> permissions = permissionsService.getUserPermissions(sysUser.getUserId());
        Set<String> permissions = new HashSet<>();
        String[] roles = new String[0];
        if (CollUtil.isNotEmpty(permissions)) {
            roles = permissions.stream().map(role -> "ROLE_" + role).toArray(String[]::new);
        }
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);
        return new CustomUserDetailsUser(sysUser.getId(), sysUser.getName(), sysUser.getPassword(), authorities);
    }

    public UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getById(userId);
        if (ObjectUtil.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return getDetail(sysUser);
    }
}
