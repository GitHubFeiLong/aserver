package com.zhy.authentication.server.service;

import com.goudong.core.lang.PageResult;
import com.zhy.authentication.server.rest.req.BaseUserCreate;
import com.zhy.authentication.server.rest.req.BaseUserUpdate;
import com.zhy.authentication.server.rest.req.search.BaseUserPage;
import com.zhy.authentication.server.service.dto.BaseUserDTO;
import com.zhy.authentication.server.service.dto.LoginDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.zhy.authentication.server.domain.BaseUser}.
 */
public interface BaseUserService {

    /**
     * 新增用户
     * @param req
     * @return
     */
    BaseUserDTO save(BaseUserCreate req);

    /**
     * 修改用户
     * @param req
     * @return
     */
    BaseUserDTO save(BaseUserUpdate req);

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
    Boolean delete(Long id);

    /**
     * 登录信息
     * @param id
     * @return
     */
    LoginDTO login(Long id);

    /**
     * 分页查询
     * @param req
     * @return
     */
    PageResult page(BaseUserPage req);

    /**
     * 查询用户id详情
     * @param id
     * @return
     */
    @Deprecated
    BaseUserDTO getById(Long id);
}
