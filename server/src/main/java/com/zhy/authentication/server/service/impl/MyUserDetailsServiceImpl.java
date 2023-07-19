package com.zhy.authentication.server.service.impl;

import com.zhy.authentication.server.constant.HttpHeaderConst;
import com.zhy.authentication.server.domain.BaseUser;
import com.zhy.authentication.server.repository.BaseAppRepository;
import com.zhy.authentication.server.repository.BaseUserRepository;
import com.zhy.authentication.server.service.MyUserDetailsService;
import com.zhy.authentication.server.service.dto.MyUserDetails;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 类描述：
 * 登录：1. 查询用户
 * @author cfl
 * @version 1.0
 * @date 2023/6/21 10:02
 */
@Service
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private BaseUserRepository baseUserRepository;

    @Resource
    private BaseAppRepository baseAppRepository;

    /**
     * 加载根据用户名加载用户
     * 当认证失败
     * @see AuthenticationException
     * @param username
     * @return
     * @throws AuthenticationException
     */
    @Override
    @Transactional(readOnly = true)
    public MyUserDetails loadUserByUsername(String username) throws AuthenticationException {
        Long appId = (Long) httpServletRequest.getAttribute(HttpHeaderConst.X_APP_ID);

        BaseUser user = baseUserRepository.findByLogin(appId, username);
        // System.out.println("user = " + user);
        if (user != null) {
            MyUserDetails myUserDetails = new MyUserDetails();
            myUserDetails.setId(user.getId());
            myUserDetails.setAppId(user.getAppId());
            myUserDetails.setUsername(user.getUsername());
            myUserDetails.setPassword(user.getPassword());
            myUserDetails.setEnabled(user.getEnabled());
            myUserDetails.setLocked(user.getLocked());
            myUserDetails.setValidTime(user.getValidTime());
            return myUserDetails;
        }

        return null;
    }
}
