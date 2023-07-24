package com.zhy.authentication.server.service;

import com.zhy.authentication.server.rest.req.BaseRoleCreate;
import com.zhy.authentication.server.rest.req.BaseRoleUpdate;
import com.zhy.authentication.server.service.dto.BaseRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.zhy.authentication.server.domain.BaseRole}.
 */
public interface BaseRoleService {

    /**
     * 新增角色
     * @param req
     * @return
     */
    BaseRoleDTO save(BaseRoleCreate req);

    /**
     * 修改角色
     * @param req
     * @return
     */
    BaseRoleDTO save(BaseRoleUpdate req);

    /**
     * Get all the baseRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BaseRoleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" baseRole.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BaseRoleDTO> findOne(Long id);

    /**
     * 删除角色
     * @param id
     * @return
     */
    boolean delete(Long id);


}
