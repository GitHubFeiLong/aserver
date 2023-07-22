package com.zhy.authentication.server.rest.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 类描述：
 * 修改应用
 * @author cfl
 * @version 1.0
 * @date 2023/7/21 11:13
 */
@Data
public class BaseAppUpdate {

    @ApiModelProperty(value = "应用id", required = true)
    private Long id;

    /**
     * 应用名称
     */
    @Size(max = 16)
    @ApiModelProperty(value = "应用名称")
    private String name;

    /**
     * 是否激活应用
     */
    @ApiModelProperty(value = "是否激活应用")
    private Boolean enabled;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
}
