package com.zhy.authentication.server.rest;

import com.goudong.core.lang.Result;
import com.zhy.authentication.server.rest.req.BaseUserCreate;
import com.zhy.authentication.server.service.BaseUserService;
import com.zhy.authentication.server.service.dto.BaseUserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * REST controller for managing {@link com.zhy.authentication.server.domain.BaseUser}.
 * TODO 创建，删除，修改，分页，
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户")
@Slf4j
public class BaseUserResource {

    @Resource
    private BaseUserService baseUserService;

    /**
     * 登录接口
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录(password)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
    })
    public Result login (String username, String password) {
        return Result.ofSuccess();
    }

    /**
     * 注销接口
     * @return
     */
    @PutMapping("/logout")
    @ApiOperation(value = "注销")
    public Result logout () {
        return Result.ofSuccess();
    }

    @PostMapping("/base-user")
    @ApiOperation(value = "新增用户")
    public Result<BaseUserDTO> create(@RequestBody @Validated BaseUserCreate req) {
        return Result.ofSuccess(baseUserService.save(req));
    }

    // @PostMapping("/base-user")
    // @ApiOperation(value = "新增用户")
    // public Result<BaseUserDTO> create(@RequestBody @Validated BaseUserCreate req) {
    //     return Result.ofSuccess(baseUserService.save(req));
    // }
}
