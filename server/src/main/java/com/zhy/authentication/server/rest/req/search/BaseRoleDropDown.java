package com.zhy.authentication.server.rest.req.search;

import cn.zhxu.bs.bean.DbField;
import cn.zhxu.bs.bean.DbIgnore;
import cn.zhxu.bs.bean.SearchBean;
import cn.zhxu.bs.operator.StartWith;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 类描述：
 * 角色下拉分页
 * @author cfl
 * @version 1.0
 * @date 2023/7/22 19:59
 */
@SearchBean(
        tables="base_role"
)
@Data
public class BaseRoleDropDown {
    //~fields
    //==================================================================================================================
    @DbField(onlyOn = StartWith.class)
    private String name;
}
