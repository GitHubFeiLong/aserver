package com.zhy.authentication.server.service.dto;

import com.goudong.boot.web.core.ClientException;
import com.goudong.core.util.AssertUtil;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.zhy.authentication.server.constant.RoleConst.ROLE_SUPER_ADMIN;

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
     * 登录所选appId,不选择，使用用户所在appId
     */
    private Long selectAppId;

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

    /**
     * 是否是超级管理员
     * @return true 超级管理员，false 不是超级管理员
     */
    public boolean superAdmin() {
        return this.roles.stream().filter(f -> Objects.equals(f.getAuthority(), ROLE_SUPER_ADMIN)).findFirst().isPresent();
    }
    /**
     * 断言
     */
    public void assertSuperAdmin() {
        AssertUtil.isTrue(superAdmin(), () -> ClientException.clientByForbidden());
    }
}
