package com.zhy.authentication.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.goudong.boot.web.core.ClientException;
import com.goudong.core.util.AssertUtil;
import com.mysql.cj.xdevapi.Client;
import com.zhy.authentication.server.domain.BaseRole;
import com.zhy.authentication.server.repository.BaseRoleRepository;
import com.zhy.authentication.server.repository.BaseUserRoleRepository;
import com.zhy.authentication.server.rest.req.BaseRoleCreate;
import com.zhy.authentication.server.rest.req.BaseRoleUpdate;
import com.zhy.authentication.server.service.BaseRoleService;
import com.zhy.authentication.server.service.dto.BaseRoleDTO;
import com.zhy.authentication.server.service.dto.MyAuthentication;
import com.zhy.authentication.server.service.mapper.BaseRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * Service Implementation for managing {@link BaseRole}.
 */
@Service
@Transactional
public class BaseRoleServiceImpl implements BaseRoleService {

    private final Logger log = LoggerFactory.getLogger(BaseRoleServiceImpl.class);

    @Resource
    private BaseRoleRepository baseRoleRepository;

    @Resource
    private BaseRoleMapper baseRoleMapper;

    @Resource
    private BaseUserRoleRepository baseUserRoleRepository;

    // /**
    //  * Save a baseRole.
    //  *
    //  * @param baseRoleDTO the entity to save.
    //  * @return the persisted entity.
    //  */
    // @Override
    // public BaseRoleDTO save(BaseRoleDTO baseRoleDTO) {
    //     log.debug("Request to save BaseRole : {}", baseRoleDTO);
    //     BaseRole baseRole = baseRoleMapper.toEntity(baseRoleDTO);
    //     baseRole = baseRoleRepository.save(baseRole);
    //     return baseRoleMapper.toDto(baseRole);
    // }

    /**
     * 新增角色
     *
     * @param req
     * @return
     */
    @Override
    public BaseRoleDTO save(BaseRoleCreate req) {
        MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication();
        // 不是超级管理员
        if (!authentication.superAdmin()) {
            AssertUtil.isEquals(authentication.getAppId(), req.getAppId(), () -> ClientException.clientByForbidden("无权添加应用角色"));
        }
        BaseRole baseRole = BeanUtil.copyProperties(req, BaseRole.class);

        baseRoleRepository.save(baseRole);

        return baseRoleMapper.toDto(baseRole);
    }

    /**
     * 修改角色
     *
     * @param req
     * @return
     */
    @Override
    public BaseRoleDTO save(BaseRoleUpdate req) {
        BaseRole baseRole = baseRoleRepository.findById(req.getId()).orElseThrow(() -> ClientException.client("角色不存在"));

        MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication();
        // 不是超级管理员
        if (!authentication.superAdmin()) {
            AssertUtil.isEquals(authentication.getAppId(), baseRole.getAppId(), () -> ClientException.clientByForbidden("无权修改应用角色"));
        }

        baseRole.setRemark(req.getRemark());

        baseRoleRepository.save(baseRole);

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
     * 删除角色
     * @param id
     * @return
     */
    @Override
    public boolean delete(Long id) {
        log.debug("Request to delete BaseRole : {}", id);
        BaseRole baseRole = baseRoleRepository.findById(id).orElseThrow(() -> ClientException.client("角色不存在"));
        MyAuthentication authentication = (MyAuthentication) SecurityContextHolder.getContext().getAuthentication();
        // 不是超级管理员
        if (!authentication.superAdmin()) {
            AssertUtil.isEquals(authentication.getAppId(), baseRole.getAppId(), () -> ClientException.clientByForbidden("无权删除应用角色"));
        }

        // 查询角色下的用户
        int count = baseUserRoleRepository.countByRoleId(id);

        AssertUtil.isTrue(count == 0, () -> ClientException.client("删除角色失败"));
        baseRoleRepository.deleteById(id);

        return true;
    }
}
