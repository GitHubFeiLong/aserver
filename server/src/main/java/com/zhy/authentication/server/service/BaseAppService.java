package com.zhy.authentication.server.service;

import com.zhy.authentication.server.rest.req.BaseAppCreate;
import com.zhy.authentication.server.rest.req.BaseAppUpdate;
import com.zhy.authentication.server.service.dto.BaseAppDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.zhy.authentication.server.domain.BaseApp}.
 */
public interface BaseAppService {

    /**
     * 新增
     * @param req
     * @return
     */
    BaseAppDTO save(BaseAppCreate req);

    /**
     * 修改
     * @param req
     * @return
     */
    BaseAppDTO update(BaseAppUpdate req);

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
