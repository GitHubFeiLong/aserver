package com.zhy.authentication.server.service.dto;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * 类描述：
 *
 * @author cfl
 * @version 1.0
 * @date 2023/7/18 9:12
 */
@Data
public class MyAuthentication implements Authentication {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 角色
     */
    private List<GrantedAuthority> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * Stores additional details about the authentication request. These might be an IP
     * address, certificate serial number etc.
     *
     * @return additional details about the authentication request, or <code>null</code>
     * if not used
     */
    @Override
    public Object getDetails() {
        return null;
    }


    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    /**
     * Returns the name of this principal.
     *
     * @return the name of this principal.
     */
    @Override
    public String getName() {
        return this.username;
    }
}
