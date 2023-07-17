package com.zhy.authentication.server.service.impl;

import com.zhy.authentication.server.domain.BaseUser;
import com.zhy.authentication.server.repository.BaseUserRepository;
import com.zhy.authentication.server.service.BaseUserService;
import com.zhy.authentication.server.service.dto.BaseUserDTO;
import com.zhy.authentication.server.service.mapper.BaseUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link BaseUser}.
 */
@Service
@Transactional
public class BaseUserServiceImpl implements BaseUserService {

    private final Logger log = LoggerFactory.getLogger(BaseUserServiceImpl.class);

    private final BaseUserRepository baseUserRepository;

    private final BaseUserMapper baseUserMapper;

    public BaseUserServiceImpl(BaseUserRepository baseUserRepository, BaseUserMapper baseUserMapper) {
        this.baseUserRepository = baseUserRepository;
        this.baseUserMapper = baseUserMapper;
    }

    /**
     * Save a baseUser.
     *
     * @param baseUserDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BaseUserDTO save(BaseUserDTO baseUserDTO) {
        log.debug("Request to save BaseUser : {}", baseUserDTO);
        BaseUser baseUser = baseUserMapper.toEntity(baseUserDTO);
        baseUser = baseUserRepository.save(baseUser);
        return baseUserMapper.toDto(baseUser);
    }

    /**
     * Get all the baseUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BaseUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BaseUsers");
        return baseUserRepository.findAll(pageable)
            .map(baseUserMapper::toDto);
    }


    /**
     * Get one baseUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BaseUserDTO> findOne(Long id) {
        log.debug("Request to get BaseUser : {}", id);
        return baseUserRepository.findById(id)
            .map(baseUserMapper::toDto);
    }

    /**
     * Delete the baseUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete BaseUser : {}", id);
        baseUserRepository.deleteById(id);
    }
}
