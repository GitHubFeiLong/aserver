package com.zhy.authentication.server.service.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 类描述：
 * 登录返回信息
 * @author cfl
 * @version 1.0
 * @date 2023/7/18 13:44
 */
@Data
public class LoginDTO {

    private Long id;

    private Long appId;

    private String username;

    private String token;

    private Set<String> roles;

    private List<BaseMenuDTO> menus;

    /**
     * 选择应用的首页
     */
    private String selectAppIdHomePage;

    /**
     * 请求头应用的首页
     */
    private String xAppIdHomePage;
}
