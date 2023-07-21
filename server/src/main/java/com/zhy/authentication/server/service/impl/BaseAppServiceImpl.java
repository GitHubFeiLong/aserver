package com.zhy.authentication.server.service.impl;

import com.zhy.authentication.server.domain.BaseApp;
import com.zhy.authentication.server.repository.BaseAppRepository;
import com.zhy.authentication.server.rest.req.BaseAppCreate;
import com.zhy.authentication.server.service.BaseAppService;
import com.zhy.authentication.server.service.dto.BaseAppDTO;
import com.zhy.authentication.server.service.mapper.BaseAppMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

/**
 * Service Implementation for managing {@link BaseApp}.
 */
@Service
@Transactional
public class BaseAppServiceImpl implements BaseAppService {

    private final Logger log = LoggerFactory.getLogger(BaseAppServiceImpl.class);

    private final BaseAppRepository baseAppRepository;

    private final BaseAppMapper baseAppMapper;

    public BaseAppServiceImpl(BaseAppRepository baseAppRepository, BaseAppMapper baseAppMapper) {
        this.baseAppRepository = baseAppRepository;
        this.baseAppMapper = baseAppMapper;
    }

    /**
     * Save a baseApp.
     *
     * @param baseAppDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BaseAppDTO save(BaseAppCreate req) {
        log.debug("Request to save BaseApp : {}", req);
        BaseApp baseApp = new BaseApp();
        baseApp.setName(req.getName());
        baseApp.setRemark(req.getRemark());
        baseApp.setSecret(UUID.randomUUID().toString().replace("-", ""));
        baseApp = baseAppRepository.save(baseApp);
        return baseAppMapper.toDto(baseApp);
    }

    /**
     * Get all the baseApps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BaseAppDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BaseApps");
        return baseAppRepository.findAll(pageable)
            .map(baseAppMapper::toDto);
    }


    /**
     * Get one baseApp by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BaseAppDTO> findOne(Long id) {
        log.debug("Request to get BaseApp : {}", id);
        return baseAppRepository.findById(id)
            .map(baseAppMapper::toDto);
    }

    /**
     * Delete the baseApp by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BaseApp : {}", id);
        baseAppRepository.deleteById(id);
    }
}
