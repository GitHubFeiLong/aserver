package com.zhy.authentication.server.service;

import com.zhy.authentication.server.rest.req.BaseAppCreate;
import com.zhy.authentication.server.service.dto.BaseAppDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.zhy.authentication.server.domain.BaseApp}.
 */
public interface BaseAppService {

    /**
     * Save a baseApp.
     *
     * @param req the entity to save.
     * @return the persisted entity.
     */
    BaseAppDTO save(BaseAppCreate req);

    /**
     * Get all the baseApps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BaseAppDTO> findAll(Pageable pageable);


    /**
     * Get the "id" baseApp.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BaseAppDTO> findOne(Long id);

    /**
     * Delete the "id" baseApp.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
