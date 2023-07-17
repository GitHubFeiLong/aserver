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
 * 角色表
 */
@Entity
@Table(name = "base_role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BaseRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 应用id
     */
    @NotNull
    @Column(name = "app_id", nullable = false)
    private Long appId;

    /**
     * 角色名称
     */
    @NotNull
    @Size(min = 4, max = 16)
    @Column(name = "name", length = 16, nullable = false)
    private String name;

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

    @OneToMany(mappedBy = "role")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BaseUserRole> users = new HashSet<>();

    @OneToMany(mappedBy = "role")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BaseRoleMenu> menus = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppId() {
        return appId;
    }

    public BaseRole appId(Long appId) {
        this.appId = appId;
        return this;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getName() {
        return name;
    }

    public BaseRole name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public BaseRole remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public BaseRole createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public BaseRole lastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public BaseRole createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public BaseRole lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<BaseUserRole> getUsers() {
        return users;
    }

    public BaseRole users(Set<BaseUserRole> baseUserRoles) {
        this.users = baseUserRoles;
        return this;
    }

    public BaseRole addUsers(BaseUserRole baseUserRole) {
        this.users.add(baseUserRole);
        baseUserRole.setRole(this);
        return this;
    }

    public BaseRole removeUsers(BaseUserRole baseUserRole) {
        this.users.remove(baseUserRole);
        baseUserRole.setRole(null);
        return this;
    }

    public void setUsers(Set<BaseUserRole> baseUserRoles) {
        this.users = baseUserRoles;
    }

    public Set<BaseRoleMenu> getMenus() {
        return menus;
    }

    public BaseRole menus(Set<BaseRoleMenu> baseRoleMenus) {
        this.menus = baseRoleMenus;
        return this;
    }

    public BaseRole addMenus(BaseRoleMenu baseRoleMenu) {
        this.menus.add(baseRoleMenu);
        baseRoleMenu.setRole(this);
        return this;
    }

    public BaseRole removeMenus(BaseRoleMenu baseRoleMenu) {
        this.menus.remove(baseRoleMenu);
        baseRoleMenu.setRole(null);
        return this;
    }

    public void setMenus(Set<BaseRoleMenu> baseRoleMenus) {
        this.menus = baseRoleMenus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseRole)) {
            return false;
        }
        return id != null && id.equals(((BaseRole) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BaseRole{" +
            "id=" + getId() +
            ", appId=" + getAppId() +
            ", name='" + getName() + "'" +
            ", remark='" + getRemark() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
