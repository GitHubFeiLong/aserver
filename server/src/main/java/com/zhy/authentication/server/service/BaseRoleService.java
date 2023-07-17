package com.zhy.authentication.server.service;

import com.zhy.authentication.server.service.dto.BaseRoleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.zhy.authentication.server.domain.BaseRole}.
 */
public interface BaseRoleService {

    /**
     * Save a baseRole.
     *
     * @param baseRoleDTO the entity to save.
     * @return the persisted entity.
     */
    BaseRoleDTO save(BaseRoleDTO baseRoleDTO);

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
     * Delete the "id" baseRole.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
