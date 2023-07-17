package com.zhy.authentication.server.service;

import com.zhy.authentication.server.service.dto.BaseUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.zhy.authentication.server.domain.BaseUser}.
 */
public interface BaseUserService {

    /**
     * Save a baseUser.
     *
     * @param baseUserDTO the entity to save.
     * @return the persisted entity.
     */
    BaseUserDTO save(BaseUserDTO baseUserDTO);

    /**
     * Get all the baseUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BaseUserDTO> findAll(Pageable pageable);


    /**
     * Get the "id" baseUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BaseUserDTO> findOne(Long id);

    /**
     * Delete the "id" baseUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}