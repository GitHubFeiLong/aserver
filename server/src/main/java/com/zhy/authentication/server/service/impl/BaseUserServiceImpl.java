package com.zhy.authentication.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.zhxu.bs.BeanSearcher;
import cn.zhxu.bs.SearchResult;
import cn.zhxu.bs.operator.InList;
import cn.zhxu.bs.util.MapUtils;
import com.goudong.boot.web.core.ClientException;
import com.goudong.core.lang.PageResult;
import com.goudong.core.util.AssertUtil;
import com.goudong.core.util.CollectionUtil;
import com.zhy.authentication.common.core.Jwt;
import com.zhy.authentication.common.core.UserToken;
import com.zhy.authentication.server.domain.BaseApp;
import com.zhy.authentication.server.domain.BaseMenu;
import com.zhy.authentication.server.domain.BaseUser;
import com.zhy.authentication.server.domain.BaseUserRole;
import com.zhy.authentication.server.repository.*;
import com.zhy.authentication.server.rest.req.BaseUserCreate;
import com.zhy.authentication.server.rest.req.BaseUserUpdate;
import com.zhy.authentication.server.rest.req.search.BaseUserPage;
import com.zhy.authentication.server.rest.req.search.SelectUsersRoleNames;
import com.zhy.authentication.server.service.BaseUserService;
import com.zhy.authentication.server.service.dto.BaseMenuDTO;
import com.zhy.authentication.server.service.dto.BaseUserDTO;
import com.zhy.authentication.server.service.dto.LoginDTO;
import com.zhy.authentication.server.service.dto.MyAuthentication;
import com.zhy.authentication.server.service.mapper.BaseMenuMapper;
import com.zhy.authentication.server.service.mapper.BaseUserMapper;
import com.zhy.authentication.server.util.PageResultUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @Resource
    private HttpServletRequest request;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private BeanSearcher beanSearcher;

    /**
     * 新增用户
     * @param req
     * @return
     */
    @Override
    public BaseUserDTO save(BaseUserCreate req) {
        MyAuthentication authentication = (MyAuthentication)SecurityContextHolder.getContext().getAuthentication();
        // 不是超级管理员不能新增其它app用户只能新增自己app用户
        if (!authentication.superAdmin()) {
            AssertUtil.isEquals(authentication.getAppId(), req.getAppId(), () -> ClientException.clientByForbidden("无权添加应用用户"));
        }

        BaseUser baseUser = BeanUtil.copyProperties(req, BaseUser.class);
        // 密码加密
        baseUser.setPassword(passwordEncoder.encode(req.getPassword()));
        baseUser.setEnabled(true);
        baseUser.setLocked(false);
        baseUserRepository.save(baseUser);
        return baseUserMapper.toDto(baseUser);
    }

    /**
     * 修改用户
     *
     * @param req
     * @return
     */
    @Override
    public BaseUserDTO save(BaseUserUpdate req) {
        MyAuthentication authentication = (MyAuthentication)SecurityContextHolder.getContext().getAuthentication();

        BaseUser baseUser = baseUserRepository.findById(req.getId()).orElseThrow(() -> ClientException.client("用户不存在"));

        // 不是超级管理员不能新增其它app用户只能修改自己app用户
        if (!authentication.superAdmin()) {
            AssertUtil.isEquals(authentication.getAppId(), baseUser.getAppId(), () -> ClientException.clientByForbidden("无权修改应用用户"));
        }
        baseUser.setRemark(req.getRemark());
        if (StringUtils.isNotBlank(req.getPassword())) {
            baseUser.setPassword(passwordEncoder.encode(req.getPassword()));
        }

        baseUser.setEnabled(Optional.ofNullable(req.getEnabled()).orElseGet(() -> baseUser.getLocked()));
        baseUser.setLocked(Optional.ofNullable(req.getLocked()).orElseGet(() -> baseUser.getLocked()));
        baseUser.setValidTime(Optional.ofNullable(req.getValidTime()).orElseGet(() -> baseUser.getValidTime()));

        baseUserRepository.save(baseUser);
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
    public Boolean delete(Long id) {
        log.debug("Request to delete BaseUser : {}", id);
        MyAuthentication authentication = (MyAuthentication)SecurityContextHolder.getContext().getAuthentication();

        BaseUser baseUser = baseUserRepository.findById(id).orElseThrow(() -> ClientException.client("用户不存在"));

        // 不是超级管理员不能新增其它app用户只能删除自己app用户
        if (!authentication.superAdmin()) {
            AssertUtil.isEquals(authentication.getAppId(), baseUser.getAppId(), () -> ClientException.clientByForbidden("无权删除应用用户"));
        }

        baseUserRepository.deleteById(id);

        return true;
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

    /**
     * 分页查询
     *
     * @param req
     * @return
     */
    @Override
    public PageResult page(BaseUserPage req) {
        Map<String, Object> build = MapUtils.builder()
                .page(req.getPage(), req.getSize())
                .field(BaseUserPage::getAppId, req.getAppId())
                .build();
        SearchResult<BaseUserPage> search = beanSearcher.search(BaseUserPage.class,  build);

        if (search.getTotalCount().longValue() > 0) {
            // 用户id集合
            List<Long> userIds = search.getDataList().stream().map(BaseUserPage::getId).collect(Collectors.toList());

            // 查询角色
            List<SelectUsersRoleNames> selectUsersRoleNames = beanSearcher.searchAll(SelectUsersRoleNames.class, MapUtils.builder()
                    .field(SelectUsersRoleNames::getUserId, userIds).op(InList.class)
                    .build());

            Map<Long, List<SelectUsersRoleNames>> map = selectUsersRoleNames.stream().collect(Collectors.groupingBy(SelectUsersRoleNames::getUserId));

            search.getDataList().stream().forEach(p -> {
                List<SelectUsersRoleNames> roles = map.get(p.getId());
                if (CollectionUtil.isNotEmpty(roles)) {
                    p.setRoles(roles);
                }
            });
        }

        return PageResultUtil.convert(search, req);
    }

    /**
     * 查询用户id详情
     *
     * @param id
     * @return
     */
    @Override
    public BaseUserDTO getById(Long id) {
        BaseUser baseUser = baseUserRepository.findById(id).orElseThrow(() -> ClientException.client("用户不存在"));
        MyAuthentication authentication = (MyAuthentication)SecurityContextHolder.getContext().getAuthentication();
        // 不是超级管理员
        if (!authentication.superAdmin()) {
            AssertUtil.isEquals(authentication.getAppId(), baseUser.getAppId(), () -> ClientException.client("用户不存在"));
        }

        BaseUserDTO baseUserDTO = baseUserMapper.toDto(baseUser);

        // 设置角色

        return baseUserDTO;
    }
}
