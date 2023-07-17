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
 * 基础用户
 */
@Entity
@Table(name = "base_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BaseUser implements Serializable {

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
     * 用户名
     */
    @NotNull
    @Size(max = 16)
    @Column(name = "username", length = 16, nullable = false)
    private String username;

    /**
     * 密码
     */
    @NotNull
    @Size(max = 128)
    @Column(name = "jhi_password", length = 128, nullable = false)
    private String password;

    /**
     * 激活状态：true 激活；false 未激活
     */
    @NotNull
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    /**
     * 锁定状态：true 锁定；false 未锁定
     */
    @NotNull
    @Column(name = "jhi_locked", nullable = false)
    private Boolean locked;

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

    @OneToMany(mappedBy = "user")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BaseUserRole> roles = new HashSet<>();

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

    public BaseUser appId(Long appId) {
        this.appId = appId;
        return this;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getUsername() {
        return username;
    }

    public BaseUser username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public BaseUser password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public BaseUser enabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isLocked() {
        return locked;
    }

    public BaseUser locked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getRemark() {
        return remark;
    }

    public BaseUser remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public BaseUser createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastModifiedDate() {
        return lastModifiedDate;
    }

    public BaseUser lastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(LocalDate lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public BaseUser createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public BaseUser lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<BaseUserRole> getRoles() {
        return roles;
    }

    public BaseUser roles(Set<BaseUserRole> baseUserRoles) {
        this.roles = baseUserRoles;
        return this;
    }

    public BaseUser addRoles(BaseUserRole baseUserRole) {
        this.roles.add(baseUserRole);
        baseUserRole.setUser(this);
        return this;
    }

    public BaseUser removeRoles(BaseUserRole baseUserRole) {
        this.roles.remove(baseUserRole);
        baseUserRole.setUser(null);
        return this;
    }

    public void setRoles(Set<BaseUserRole> baseUserRoles) {
        this.roles = baseUserRoles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseUser)) {
            return false;
        }
        return id != null && id.equals(((BaseUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BaseUser{" +
            "id=" + getId() +
            ", appId=" + getAppId() +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", enabled='" + isEnabled() + "'" +
            ", locked='" + isLocked() + "'" +
            ", remark='" + getRemark() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
