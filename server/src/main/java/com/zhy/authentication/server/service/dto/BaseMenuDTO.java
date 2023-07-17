package com.zhy.authentication.server.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.zhy.authentication.server.domain.BaseMenu} entity.
 */
@ApiModel(description = "菜单表")
public class BaseMenuDTO implements Serializable {

    private Long id;

    /**
     * 父级主键id
     */
    @ApiModelProperty(value = "父级主键id")
    private Long parentId;

    /**
     * 应用id
     */
    @NotNull
    @ApiModelProperty(value = "应用id", required = true)
    private Long appId;

    /**
     * 权限标识
     */
    @NotNull
    @Size(min = 1, max = 64)
    @ApiModelProperty(value = "权限标识", required = true)
    private String permissionId;

    /**
     * 菜单名称
     */
    @NotNull
    @Size(min = 1, max = 64)
    @ApiModelProperty(value = "菜单名称", required = true)
    private String name;

    /**
     * 菜单类型（1：菜单；2：按钮；3：接口）
     */
    @NotNull
    @Min(value = 1)
    @Max(value = 3)
    @ApiModelProperty(value = "菜单类型（1：菜单；2：按钮；3：接口）", required = true)
    private Integer type;

    /**
     * 路由或接口地址
     */
    @Size(max = 255)
    @ApiModelProperty(value = "路由或接口地址")
    private String path;

    /**
     * 请求方式
     */
    @ApiModelProperty(value = "请求方式")
    private String method;

    /**
     * 排序字段（值越小越靠前，仅仅针对前端路由）
     */
    @Min(value = 1)
    @Max(value = 2147483647)
    @ApiModelProperty(value = "排序字段（值越小越靠前，仅仅针对前端路由）")
    private Integer sortNum;

    /**
     * 是否是隐藏菜单
     */
    @NotNull
    @ApiModelProperty(value = "是否是隐藏菜单", required = true)
    private Boolean hide;

    /**
     * 前端菜单元数据
     */
    @Size(max = 255)
    @ApiModelProperty(value = "前端菜单元数据")
    private String meta;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDate createdDate;

    /**
     * 最后修改时间
     */
    @ApiModelProperty(value = "最后修改时间")
    private LocalDate lastModifiedDate;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createdBy;

    /**
     * 最后修改人
     */
    @ApiModelProperty(value = "最后修改人")
    private String lastModifiedBy;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Boolean isHide() {
        return hide;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaseMenuDTO baseMenuDTO = (BaseMenuDTO) o;
        if (baseMenuDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), baseMenuDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BaseMenuDTO{" +
            "id=" + getId() +
            ", parentId=" + getParentId() +
            ", appId=" + getAppId() +
            ", permissionId='" + getPermissionId() + "'" +
            ", name='" + getName() + "'" +
            ", type=" + getType() +
            ", path='" + getPath() + "'" +
            ", method='" + getMethod() + "'" +
            ", sortNum=" + getSortNum() +
            ", hide='" + isHide() + "'" +
            ", meta='" + getMeta() + "'" +
            ", remark='" + getRemark() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
