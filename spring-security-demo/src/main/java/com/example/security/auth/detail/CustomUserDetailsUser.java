package com.example.security.auth.detail;

import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 */
@EqualsAndHashCode(callSuper = true)
public class CustomUserDetailsUser extends User implements Serializable {

    private String userId;

    public CustomUserDetailsUser(String userId,String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

}
