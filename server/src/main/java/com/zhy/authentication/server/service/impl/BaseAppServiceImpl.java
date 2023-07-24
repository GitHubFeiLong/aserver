package com.zhy.authentication.server.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.zhxu.bs.BeanSearcher;
import cn.zhxu.bs.SearchResult;
import cn.zhxu.bs.util.MapUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.goudong.boot.redis.core.RedisTool;
import com.goudong.boot.web.core.ClientException;
import com.goudong.boot.web.core.ServerException;
import com.goudong.core.lang.PageResult;
import com.zhy.authentication.server.constant.RoleConst;
import com.zhy.authentication.server.domain.BaseApp;
import com.zhy.authentication.server.domain.BaseRole;
import com.zhy.authentication.server.domain.BaseUser;
import com.zhy.authentication.server.domain.BaseUserRole;
import com.zhy.authentication.server.repository.BaseAppRepository;
import com.zhy.authentication.server.repository.BaseRoleRepository;
import com.zhy.authentication.server.repository.BaseUserRepository;
import com.zhy.authentication.server.repository.BaseUserRoleRepository;
import com.zhy.authentication.server.rest.req.BaseAppCreate;
import com.zhy.authentication.server.rest.req.BaseAppUpdate;
import com.zhy.authentication.server.rest.req.search.BaseAppPage;
import com.zhy.authentication.server.rest.req.search.BaseRoleDropDown;
import com.zhy.authentication.server.rest.req.search.BaseRoleDropDownPage;
import com.zhy.authentication.server.service.BaseAppService;
import com.zhy.authentication.server.service.dto.BaseAppDTO;
import com.zhy.authentication.server.service.mapper.BaseAppMapper;
import com.zhy.authentication.server.util.PageResultUtil;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.zhy.authentication.server.enums.RedisKeyTemplateProviderEnum.APP_ID;

/**
 * Service Implementation for managing {@link BaseApp}.
 */
@Service
public class BaseAppServiceImpl implements BaseAppService {

    private final Logger log = LoggerFactory.getLogger(BaseAppServiceImpl.class);

    @Resource
    private BaseAppRepository baseAppRepository;
    @Resource
    private BaseAppMapper baseAppMapper;

    @Resource
    private RedisTool redisTool;

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private BeanSearcher beanSearcher;

    @Resource
    private BaseRoleRepository baseRoleRepository;

    @Resource
    private BaseUserRepository baseUserRepository;

    @Resource
    private BaseUserRoleRepository baseUserRoleRepository;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private PasswordEncoder passwordEncoder;

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
        baseApp.setId(IdUtil.getSnowflake().nextId());
        baseApp.setName(req.getName());
        baseApp.setRemark(req.getRemark());
        baseApp.setSecret(UUID.randomUUID().toString().replace("-", ""));
        baseApp.setEnabled(true);


        // 新增管理用户
        BaseUser baseUser = new BaseUser();
        baseUser.setAppId(baseApp.getId());
        baseUser.setUsername("admin");
        baseUser.setPassword(passwordEncoder.encode("123456"));
        baseUser.setEnabled(true);
        baseUser.setLocked(false);
        baseUser.setRemark("创建应用时，初始管理员");

        // 新增角色
        BaseRole baseRole = new BaseRole();
        baseRole.setAppId(baseApp.getId());
        baseRole.setName(RoleConst.ROLE_ADMIN);
        baseRole.setRemark("创建应用时，初始管理员角色");


        BaseUserRole baseUserRole = new BaseUserRole();
        baseUserRole.setUser(baseUser);
        baseUserRole.setRole(baseRole);


        transactionTemplate.execute(status -> {
            try {
                // 新增应用
                baseAppRepository.save(baseApp);
                // 新增管理用户
                baseUserRepository.save(baseUser);
                baseRoleRepository.save(baseRole);
                baseUserRoleRepository.save(baseUserRole);
                return true;
            } catch (Exception e) {
                status.setRollbackOnly();
                throw ServerException.server("创建应用失败", e);
            }
        });

        return baseAppMapper.toDto(baseApp);
    }

    /**
     * 修改
     *
     * @param req
     * @return
     */
    @Override
    public BaseAppDTO update(BaseAppUpdate req) {
        BaseApp baseApp = baseAppRepository.findById(req.getId()).orElseThrow(() -> ClientException
                .builder()
                .clientMessageTemplate("应用id:{}不存在")
                .clientMessageParams(req.getId())
                .build()
        );
        if (StringUtils.isNotBlank(req.getName())) {
            baseApp.setName(req.getName());
        }

        if (req.getRemark() != null) {
            baseApp.setRemark(req.getRemark());
        }

        if (req.getEnabled() != null) {
            baseApp.setEnabled(req.getEnabled());
        }

        baseAppRepository.save(baseApp);

        redisTool.deleteKey(APP_ID, req.getId());
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
    public Optional<BaseAppDTO> findOne(Long id) {
        log.debug("Request to get BaseApp : {}", id);
        String key = APP_ID.getFullKey(id);
        if (redisTool.hasKey(key)) {
            String appStr = (String)redisTool.get(APP_ID, id);
            try {
                BaseAppDTO baseAppDTO = objectMapper.readValue(appStr, BaseAppDTO.class);
                return Optional.of(baseAppDTO);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        synchronized (this) {
            if (redisTool.hasKey(key)) {
                String appStr = (String)redisTool.get(APP_ID, id);
                try {
                    BaseAppDTO baseAppDTO = objectMapper.readValue(appStr, BaseAppDTO.class);
                    return Optional.of(baseAppDTO);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

            Optional<BaseAppDTO> baseAppDTO = baseAppRepository.findById(id).map(baseAppMapper::toDto);
            if (baseAppDTO.isPresent()) {
                try {
                    redisTool.set(APP_ID, objectMapper.writeValueAsString(baseAppDTO.get()), id);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

            return baseAppDTO;
        }
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
        redisTool.deleteKey(APP_ID, id);
    }

    /**
     * 分页
     * @param req
     * @return
     */
    @Override
    public PageResult page(BaseAppPage req) {
        Map<String, Object> build = MapUtils.builder()
                .page(req.getPage(), req.getSize())
                .field(BaseAppPage::getName, req.getName())
                .orderBy(BaseAppPage::getId).asc()
                .build();
        SearchResult<BaseAppPage> search = beanSearcher.search(BaseAppPage.class,  build);
        return PageResultUtil.convert(search, req);
    }

    /**
     * 下拉
     * @param req
     * @return
     */
    @Override
    List<BaseRoleDropDown> pageDropDown(BaseRoleDropDown req) {

    }

    /**
     * 下拉分页
     *
     * @param req
     * @return
     */

    public PageResult pageDropDown(BaseRoleDropDownPage req) {



        Map<String, Object> build = MapUtils.builder()
                .page(req.getPage(), req.getSize())
                .field(BaseRoleDropDownPage::getName, req.getName())
                .build();
        SearchResult<BaseAppPage> search = beanSearcher.search(BaseAppPage.class,  build);
        return PageResultUtil.convert(search, req);
        return null;
    }
}
