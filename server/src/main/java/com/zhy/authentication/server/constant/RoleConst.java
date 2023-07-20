package com.zhy.authentication.server.constant;

/**
 * 类描述：
 * 角色相关的常量类
 * @author msi
 * @version 1.0
 * @date 2022/1/8 11:22
 */
public class RoleConst {

    /**
     * 超级管理员角色
     */
    public static final String ROLE_SUPER_ADMIN = "ROLE_SUPER_ADMIN";

    /**
     * 应用理员角色
     */
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    /**
     * 普通用户角色
     */
    public static final String ROLE_USER = "ROLE_USER";

    /**
     * 匿名角色
     * 注意，创建角色时不能再创建该同名角色。
     */
    public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
}