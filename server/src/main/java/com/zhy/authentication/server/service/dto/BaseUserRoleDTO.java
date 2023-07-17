package com.zhy.authentication.server.service.dto;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.zhy.authentication.server.domain.BaseUserRole} entity.
 */
@ApiModel(description = "用户角色中间表")
public class BaseUserRoleDTO implements Serializable {

    private Long id;


    private Long userId;

    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long baseUserId) {
        this.userId = baseUserId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long baseRoleId) {
        this.roleId = baseRoleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BaseUserRoleDTO baseUserRoleDTO = (BaseUserRoleDTO) o;
        if (baseUserRoleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), baseUserRoleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BaseUserRoleDTO{" +
            "id=" + getId() +
            ", user=" + getUserId() +
            ", role=" + getRoleId() +
            "}";
    }
}
