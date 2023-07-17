package com.zhy.authentication.server.service.dto;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.zhy.authentication.server.domain.BaseRoleMenu} entity.
 */
@ApiModel(description = "菜单角色中间表")
public class BaseRoleMenuDTO implements Serializable {

    private Long id;


    private Long roleId;

    private Long menuId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long baseRoleId) {
        this.roleId = baseRoleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long baseMenuId) {
        this.menuId = baseMenuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaseRoleMenuDTO baseRoleMenuDTO = (BaseRoleMenuDTO) o;
        if (baseRoleMenuDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), baseRoleMenuDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BaseRoleMenuDTO{" +
            "id=" + getId() +
            ", role=" + getRoleId() +
            ", menu=" + getMenuId() +
            "}";
    }
}
