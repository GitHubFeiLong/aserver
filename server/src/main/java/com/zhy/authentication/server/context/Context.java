package com.zhy.authentication.server.context;

import com.goudong.core.util.CollectionUtil;
import lombok.Data;

import java.util.List;

/**
 * 接口描述：
 * 上下文对象，尽可能的简洁
 * @author cfl
 * @version 1.0
 * @date 2022/11/4 11:36
 */
@Data
public class Context {

    /**
     * 应用id
     */
    private Long appId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户的角色集合
     */
    private List<String> roles;

    /**
     * 获取用户的{@code sessionId}
     * 如果用户登录，应该将请求令牌，设置到{@code sessionId}，避免分布式环境下值不正确。
     * @return
     */
    private String sessionId;

    // ~ construction
    //==================================================================================================================
    public Context() {
    }

    public Context(Long appId, Long userId, String username, List<String> roles, String sessionId) {
        this.appId = appId;
        this.userId = userId;
        this.username = username;
        this.roles = roles;
        this.sessionId = sessionId;
    }

    // ~ 自定义方法
    //==================================================================================================================
    /**
     * 是否拥有admin权限
     * @return
     */
    public boolean hasSuperAdmin() {
        return CollectionUtil.isNotEmpty(roles) ? roles.contains("ROLE_SUPER_ADMIN") : false;
    }

    /**
     * 是否拥有admin权限
     * @return
     */
    public boolean hasAdmin() {
        return CollectionUtil.isNotEmpty(roles) ? roles.contains("ROLE_ADMIN") : false;
    }

}
