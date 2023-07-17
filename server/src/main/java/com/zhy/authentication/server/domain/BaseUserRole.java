package com.zhy.authentication.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户角色中间表
 */
@Entity
@Table(name = "base_user_role")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BaseUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("roles")
    private BaseUser user;

    @ManyToOne
    @JsonIgnoreProperties("users")
    private BaseRole role;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BaseUser getUser() {
        return user;
    }

    public BaseUserRole user(BaseUser baseUser) {
        this.user = baseUser;
        return this;
    }

    public void setUser(BaseUser baseUser) {
        this.user = baseUser;
    }

    public BaseRole getRole() {
        return role;
    }

    public BaseUserRole role(BaseRole baseRole) {
        this.role = baseRole;
        return this;
    }

    public void setRole(BaseRole baseRole) {
        this.role = baseRole;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseUserRole)) {
            return false;
        }
        return id != null && id.equals(((BaseUserRole) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BaseUserRole{" +
            "id=" + getId() +
            "}";
    }
}
