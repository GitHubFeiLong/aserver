package com.zhy.authentication.server.rest;

import com.zhy.authentication.server.service.BaseRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * REST controller for managing {@link com.zhy.authentication.server.domain.BaseRole}.
 * TODO 创建，删除，修改，分页，
 */
@RestController
@RequestMapping("/role")
public class BaseRoleResource {

    private final Logger log = LoggerFactory.getLogger(BaseRoleResource.class);

    @Resource
    private BaseRoleService baseRoleService;
}
