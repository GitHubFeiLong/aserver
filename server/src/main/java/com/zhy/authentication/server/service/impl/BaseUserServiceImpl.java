package com.zhy.authentication.server.service.impl;

import com.goudong.boot.web.core.ClientException;
import com.goudong.core.util.tree.v2.Tree;
import com.zhy.authentication.common.core.Jwt;
import com.zhy.authentication.common.core.UserToken;
import com.zhy.authentication.server.domain.*;
import com.zhy.authentication.server.repository.*;
import com.zhy.authentication.server.service.BaseRoleMenuService;
import com.zhy.authentication.server.service.BaseUserService;
import com.zhy.authentication.server.service.dto.BaseMenuDTO;
import com.zhy.authentication.server.service.dto.BaseUserDTO;
import com.zhy.authentication.server.service.dto.LoginDTO;
import com.zhy.authentication.server.service.mapper.BaseMenuMapper;
import com.zhy.authentication.server.service.mapper.BaseUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service Implementation for managing {@link BaseUser}.
 */
@Service
@Transactional
public class BaseUserServiceImpl implements BaseUserService {

    private final Logger log = LoggerFactory.getLogger(BaseUserServiceImpl.class);

    @Resource
    private BaseUserRepository baseUserRepository;

    @Resource
    private BaseRoleMenuRepository baseRoleMenuRepository;

    @Resource
    private BaseUserRoleRepository baseUserRoleRepository;

    @Resource
    private BaseRoleRepository baseRoleRepository;

    @Resource
    private BaseAppRepository baseAppRepository;

    @Resource
    private BaseUserMapper baseUserMapper;

    @Resource
    private BaseMenuMapper baseMenuMapper;


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

    /**
     * 登录信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public LoginDTO login(Long id) {
        BaseUser baseUser = baseUserRepository.findById(id).get();

        List<BaseUserRole> allByUserId = baseUserRoleRepository.findAllByUserId(id);
        Set<String> roles = new HashSet<>();
        Map<String, BaseMenuDTO> menuMap = new HashMap<>();
        allByUserId.stream()
                .flatMap(m -> Stream.of(m.getRole().getMenus()))
                .flatMap(Collection::stream)
                .forEach(p -> {
                    // 角色
                    String name = p.getRole().getName();
                    roles.add(name);
                    // 菜单
                    BaseMenu menu = p.getMenu();
                    if (!menuMap.containsKey(menu.getPermissionId())) {
                        BaseMenuDTO baseMenuDTO = baseMenuMapper.toDto(menu);
                        menuMap.put(menu.getPermissionId(), baseMenuDTO);
                    }
                });

        BaseApp baseApp = baseAppRepository.findById(baseUser.getAppId()).orElseThrow(() -> ClientException.client("应用无效"));

        // 创建token
        Jwt jwt = new Jwt(1, TimeUnit.DAYS, baseApp.getSecret());
        String token = jwt.generateToken(new UserToken(baseUser.getId(), baseApp.getId(), baseUser.getUsername(), roles));

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setId(baseUser.getId());
        loginDTO.setAppId(baseUser.getAppId());
        loginDTO.setUsername(baseUser.getUsername());
        loginDTO.setToken(token);
        loginDTO.setRoles(roles);

        List<BaseMenuDTO> menuDTOS = new ArrayList<>(menuMap.values());

        Collections.sort(menuDTOS, (o1, o2) -> ((BaseMenuDTO)o1).getSortNum() - ((BaseMenuDTO)o2).getSortNum());

        // Tree.getInstance().id(BaseMenuDTO::getId).parentId(BaseMenuDTO::getParentId).children(BaseMenuDTO::getChildren).to

        loginDTO.setMenus(menuDTOS);

        return loginDTO;
    }
}
