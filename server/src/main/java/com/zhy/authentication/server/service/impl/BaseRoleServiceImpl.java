package com.zhy.authentication.server.service.impl;

import com.zhy.authentication.server.domain.BaseRole;
import com.zhy.authentication.server.repository.BaseRoleRepository;
import com.zhy.authentication.server.service.BaseRoleService;
import com.zhy.authentication.server.service.dto.BaseRoleDTO;
import com.zhy.authentication.server.service.mapper.BaseRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BaseRole}.
 */
@Service
@Transactional
public class BaseRoleServiceImpl implements BaseRoleService {

    private final Logger log = LoggerFactory.getLogger(BaseRoleServiceImpl.class);

    private final BaseRoleRepository baseRoleRepository;

    private final BaseRoleMapper baseRoleMapper;

    public BaseRoleServiceImpl(BaseRoleRepository baseRoleRepository, BaseRoleMapper baseRoleMapper) {
        this.baseRoleRepository = baseRoleRepository;
        this.baseRoleMapper = baseRoleMapper;
    }

    /**
     * Save a baseRole.
     *
     * @param baseRoleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BaseRoleDTO save(BaseRoleDTO baseRoleDTO) {
        log.debug("Request to save BaseRole : {}", baseRoleDTO);
        BaseRole baseRole = baseRoleMapper.toEntity(baseRoleDTO);
        baseRole = baseRoleRepository.save(baseRole);
        return baseRoleMapper.toDto(baseRole);
    }

    /**
     * Get all the baseRoles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BaseRoleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BaseRoles");
        return baseRoleRepository.findAll(pageable)
            .map(baseRoleMapper::toDto);
    }


    /**
     * Get one baseRole by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BaseRoleDTO> findOne(Long id) {
        log.debug("Request to get BaseRole : {}", id);
        return baseRoleRepository.findById(id)
            .map(baseRoleMapper::toDto);
    }

    /**
     * Delete the baseRole by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BaseRole : {}", id);
        baseRoleRepository.deleteById(id);
    }
}
