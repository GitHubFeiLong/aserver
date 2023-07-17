package com.zhy.authentication.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 菜单角色中间表
 */
@Entity
@Table(name = "base_role_menu")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BaseRoleMenu extends BasePO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("menus")
    private BaseRole role;

    @ManyToOne
    @JsonIgnoreProperties("roles")
    private BaseMenu menu;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BaseRole getRole() {
        return role;
    }

    public BaseRoleMenu role(BaseRole baseRole) {
        this.role = baseRole;
        return this;
    }

    public void setRole(BaseRole baseRole) {
        this.role = baseRole;
    }

    public BaseMenu getMenu() {
        return menu;
    }

    public BaseRoleMenu menu(BaseMenu baseMenu) {
        this.menu = baseMenu;
        return this;
    }

    public void setMenu(BaseMenu baseMenu) {
        this.menu = baseMenu;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseRoleMenu)) {
            return false;
        }
        return id != null && id.equals(((BaseRoleMenu) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "BaseRoleMenu{" +
            "id=" + getId() +
            "}";
    }
}
