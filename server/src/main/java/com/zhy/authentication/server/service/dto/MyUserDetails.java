package com.zhy.authentication.server.service.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 类描述：
 *
 * @author cfl
 * @version 1.0
 * @date 2023/7/18 9:01
 */
@Data
public class MyUserDetails implements UserDetails {

    private Long id;

    private Long appId;

    private String username;

    private String password;

    private Boolean enabled;

    private Boolean locked;

    private Date validTime;

    private List<GrantedAuthority> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return validTime == null || (validTime.after(new Date()));
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
