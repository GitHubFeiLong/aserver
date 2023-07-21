package com.zhy.authentication.server.rest.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 类描述：
 * 新增应用
 * @author cfl
 * @version 1.0
 * @date 2023/7/21 11:13
 */
@Data
public class BaseAppCreate {
    /**
     * 应用名称
     */
    @NotNull
    @Size(max = 16)
    @ApiModelProperty(value = "应用名称", required = true)
    private String name;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
