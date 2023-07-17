package com.zhy.authentication.server.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * 菜单表
 */
@Entity
@Table(name = "base_menu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BaseMenu extends BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 父级主键id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 应用id
     */
    @NotNull
    @Column(name = "app_id", nullable = false)
    private Long appId;

    /**
     * 权限标识
     */
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "permission_id", length = 64, nullable = false)
    private String permissionId;

    /**
     * 菜单名称
     */
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "name", length = 64, nullable = false)
    private String name;

    /**
     * 菜单类型（1：菜单；2：按钮；3：接口）
     */
    @NotNull
    @Min(value = 1)
    @Max(value = 3)
    @Column(name = "jhi_type", nullable = false)
    private Integer type;

    /**
     * 路由或接口地址
     */
    @Size(max = 255)
    @Column(name = "path", length = 255)
    private String path;

    /**
     * 请求方式
     */
    @Column(name = "method")
    private String method;

    /**
     * 排序字段（值越小越靠前，仅仅针对前端路由）
     */
    @Min(value = 1)
    @Max(value = 2147483647)
    @Column(name = "sort_num")
    private Integer sortNum;

    /**
     * 是否是隐藏菜单
     */
    @NotNull
    @Column(name = "hide", nullable = false)
    private Boolean hide;

    /**
     * 前端菜单元数据
     */
    @Size(max = 255)
    @Column(name = "meta", length = 255)
    private String meta;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "created_date")
    private LocalDate createdDate;

    /**
     * 最后修改时间
     */
    @Column(name = "last_modified_date")
    private LocalDate lastModifiedDate;

    /**
     * 创建人
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * 最后修改人
     */
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @OneToMany(mappedBy = "menu")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BaseRoleMenu> roles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public BaseMenu parentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getAppId() {
        return appId;
    }

    public BaseMenu appId(Long appId) {
        this.appId = appId;
        return this;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public BaseMenu permissionId(String permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getName() {
        return name;
    }

    public BaseMenu name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public BaseMenu type(Integer type) {
        this.type = type;
        return this;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public BaseMenu path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public BaseMenu method(String method) {
        this.method = method;
        return this;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public BaseMenu sortNum(Integer sortNum) {
        this.sortNum = sortNum;
        return this;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Boolean isHide() {
        return hide;
    }

    public BaseMenu hide(Boolean hide) {
        this.hide = hide;
        return this;
    }

    public void setHide(Boolean hide) {
        this.hide = hide;
    }

    public String getMeta() {
        return meta;
    }

    public BaseMenu meta(String meta) {
        this.meta = meta;
        return this;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getRemark() {
        return remark;
    }

    public BaseMenu remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public BaseMenu createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public BaseMenu lastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public BaseMenu createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public BaseMenu lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<BaseRoleMenu> getRoles() {
        return roles;
    }

    public BaseMenu roles(Set<BaseRoleMenu> baseRoleMenus) {
        this.roles = baseRoleMenus;
        return this;
    }

    public BaseMenu addRoles(BaseRoleMenu baseRoleMenu) {
        this.roles.add(baseRoleMenu);
        baseRoleMenu.setMenu(this);
        return this;
    }

    public BaseMenu removeRoles(BaseRoleMenu baseRoleMenu) {
        this.roles.remove(baseRoleMenu);
        baseRoleMenu.setMenu(null);
        return this;
    }

    public void setRoles(Set<BaseRoleMenu> baseRoleMenus) {
        this.roles = baseRoleMenus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseMenu)) {
            return false;
        }
        return id != null && id.equals(((BaseMenu) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BaseMenu{" +
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
