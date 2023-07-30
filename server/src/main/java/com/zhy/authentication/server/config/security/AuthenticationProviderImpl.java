package com.zhy.authentication.server.config.security;

import cn.hutool.core.date.DateUtil;
import com.goudong.boot.web.core.ClientException;
import com.goudong.boot.web.enumerate.ClientExceptionEnum;
import com.goudong.core.util.AssertUtil;
import com.zhy.authentication.server.constant.RoleConst;
import com.zhy.authentication.server.constant.UserConst;
import com.zhy.authentication.server.domain.BaseUser;
import com.zhy.authentication.server.repository.BaseUserRepository;
import com.zhy.authentication.server.service.MyUserDetailsService;
import com.zhy.authentication.server.service.dto.MyAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * 类描述：
 * 自定义认证处理
 *
 * @author msi
 * @date 2022/1/15 20:26
 * @version 1.0
 */
@Slf4j
@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

    /**
     * BCrypt格式的字符串
     * {@code BCryptPasswordEncoder#BCRYPT_PATTERN}
     */
    private Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");

    @Lazy
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 用户服务接口
     */
    @Resource
    private MyUserDetailsService myUserDetailsService;

    @Resource
    private BaseUserRepository baseUserRepository;

    @Resource
    private JdbcTemplate jdbcTemplate;
    /**
     * 自定义登录认证
     *
     * @param authentication 前端传递的认证参数，包含用户名密码
     * @throws UsernameNotFoundException
     * @throws AccountExpiredException
     * @throws BadCredentialsException
     * @return 认证成功对象
     */
    @Override
    public Authentication authenticate(Authentication authentication) {
        /*
            获取登录参数
         */
        // 表单输入的用户名
        String username = (String) authentication.getPrincipal();
        // 表单输入的密码
        String password = (String) authentication.getCredentials();
        AssertUtil.isFalse(StringUtils.isBlank(username) || StringUtils.isBlank(password), () -> ClientException.client(ClientExceptionEnum.BAD_REQUEST, "请输入正确的用户名和密码"));

        // 获取其它参数
        WebAuthenticationDetailsImpl webAuthenticationDetails = (WebAuthenticationDetailsImpl) authentication.getDetails();
        // 用户选择的应用id
        Long selectAppId = webAuthenticationDetails.getSelectAppId();
        // 客户端请求头上的应用id
        Long xAppId = webAuthenticationDetails.getXAppId();
        AssertUtil.isNotNull(xAppId, () -> ClientException.client(ClientExceptionEnum.BAD_REQUEST, "请求头X-App-Id丢失"));
        log.debug("登录参数如下，username={},password={},selectAppId={},xAppId={}", username, password, selectAppId, xAppId);

        RuntimeException selectAppIdException = null;
        RuntimeException xAppIdException = null;
        //======== 登录逻辑
        // 选择应用id登录逻辑
        MyAuthentication myAuthentication = selectAppIdAuthentication(selectAppId, username, password, selectAppIdException);
        if (myAuthentication != null) {
            return myAuthentication;
        }
        // 请求头应用id登录逻辑
        myAuthentication = xAppIdAuthentication(selectAppId, xAppId, username, password, selectAppIdException, xAppIdException);
        if (myAuthentication != null) {
            return myAuthentication;
        }

        throw throwException(selectAppIdException, xAppIdException);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    /**
     * 选择应用id进行认证，
     * @param selectAppId           所选应用id
     * @param username              用户名
     * @param password              密码
     * @param selectAppIdException  记录认证失败的异常
     * @return 认证成功时，返回认证成功对象，认证失败返回null，并给{@code selectAppIdException}赋值
     */
    public MyAuthentication selectAppIdAuthentication(Long selectAppId, String username, String password, RuntimeException selectAppIdException) {
        // 选择了应用
        if (selectAppId != null) {
            log.info("选择了应用：{}", selectAppId);
            BaseUser user = findByUsernameAndAppId(selectAppId, username);
            // 所选用户存在
            if (user != null) {
                log.info("选择了应用,并且用户存在");
                boolean passwordMatches = BCRYPT_PATTERN.matcher(password).matches()
                        // 是密码格式，直接比较值
                        ? Objects.equals(password, user.getPassword())
                        // 使用 BCrypt 加密的方式进行匹配
                        : passwordEncoder.matches(password, user.getPassword());
                // 用户身份信息校验
                if (!passwordMatches) {
                    log.warn("选择了应用,用户密码错误");
                    selectAppIdException = new BadCredentialsException("用户密码错误");
                } else if (!user.getEnabled()) {
                    log.warn("选择了应用,用户未激活");
                    selectAppIdException = new DisabledException("用户未激活");
                } else if (user.getValidTime().before(new Date())) {
                    log.warn("选择了应用,账户已过期");
                    selectAppIdException = new AccountExpiredException("账户已过期");
                } else {
                    // 用户校验通过，选择应用（管理员和应用下普通用户）
                    log.info("选择了应用,用户校验成功");
                    MyAuthentication myAuthentication = new MyAuthentication();
                    myAuthentication.setId(user.getId());
                    myAuthentication.setAppId(user.getAppId());
                    myAuthentication.setUsername(user.getUsername());
                    myAuthentication.setRoles(listRoleNameByUserId(user.getId()));
                    return myAuthentication;
                }
            }
        }
        return null;
    }


    /**
     * 选择应用id进行认证，
     * @param selectAppId           所选应用id
     * @param username              用户名
     * @param password              密码
     * @param selectAppIdException  记录认证失败的异常
     * @return 认证成功时，返回认证成功对象，认证失败返回null，并给{@code selectAppIdException}赋值
     */
    @Transactional
    public MyAuthentication xAppIdAuthentication(Long selectAppId, Long xAppId, String username, String password, RuntimeException selectAppIdException, RuntimeException xAppIdException) {
        log.info("开始根据X-App-Id校验用户");
        // 未选择应用,或者选择的应用校验用户失败
        BaseUser user = findByUsernameAndAppId(xAppId, username);
        log.info("根据X-App-Id查询用户：{}", user);
        // 所选用户存在
        if (user != null) {
            log.info("根据X-App-Id查询用户，用户存在");
            boolean passwordMatches = BCRYPT_PATTERN.matcher(password).matches()
                    // 是密码格式，直接比较值
                    ? Objects.equals(password, user.getPassword())
                    // 使用 BCrypt 加密的方式进行匹配
                    : passwordEncoder.matches(password, user.getPassword());
            // 用户身份信息校验
            if (!passwordMatches) {
                log.warn("根据X-App-Id查询用户，用户密码错误");
                xAppIdException = new BadCredentialsException("用户密码错误");
            } else if (!user.getEnabled()) {
                log.warn("根据X-App-Id查询用户，用户未激活");
                xAppIdException = new DisabledException("用户未激活");
            } else if (user.getValidTime().before(new Date())) {
                log.warn("根据X-App-Id查询用户，账户已过期");
                xAppIdException = new AccountExpiredException("账户已过期");
            } else {
                log.info("根据X-App-Id校验用户成功");
                // 如果选择应用，并在选择应用下用户没有校验成功，此时需要校验是否是超级管理员
                if (selectAppId != null) {
                    log.info("根据X-App-Id校验用户成功,但是选择了应用，需要再次校验用户角色");
                    // 用户是超级管理员，就通过
                    if (user.getRoleNames().contains(RoleConst.ROLE_SUPER_ADMIN)) {
                        log.info("登录成功：选了应用，所选应用下用户校验失败，但是是超级管理员");
                        // 查询该应用下管理员
                        BaseUser adminUser = findByUsernameAndAppId(selectAppId, UserConst.ADMIN);
                        MyAuthentication myAuthentication = new MyAuthentication();
                        myAuthentication.setId(adminUser.getId());
                        myAuthentication.setAppId(selectAppId);
                        myAuthentication.setUsername(adminUser.getUsername());
                        ArrayList<GrantedAuthority> roles = new ArrayList<>(1);
                        roles.add(new SimpleGrantedAuthority(RoleConst.ROLE_ADMIN));
                        myAuthentication.setRoles(roles);
                        return myAuthentication;
                    }
                    // 选择了应用，并不是超级管理员
                    log.warn("登录失败：选了应用，所选应用下用户校验失败，并且不是超级管理员");
                    selectAppIdException = new BadCredentialsException("用户密码错误");
                } else {
                    // 未选择应用
                    log.info("登录成功：未选择应用，用户校验成功");
                    MyAuthentication myAuthentication = new MyAuthentication();
                    myAuthentication.setId(user.getId());
                    myAuthentication.setAppId(user.getAppId());
                    myAuthentication.setUsername(user.getUsername());
                    myAuthentication.setRoles(listRoleNameByUserId(user.getId()));
                    return myAuthentication;
                }
            }
        }

        return null;
    }

    /**
     * 返回异常
     * @param selectAppIdException
     * @param xAppIdException
     */
    private RuntimeException throwException(RuntimeException selectAppIdException, RuntimeException xAppIdException) {
        if (selectAppIdException != null) {
            log.error("登录失败");
            return selectAppIdException;
        } else if (xAppIdException != null) {
            log.error("登录失败");
            return xAppIdException;
        }

        return new UsernameNotFoundException("用户不存在");
    }

    /**
     * 根据应用id和用户名查询用户基本信息
     * @param appId
     * @param username
     * @return
     */
    public BaseUser findByUsernameAndAppId(Long appId, String username) {
        String sql = "\nSELECT\n" +
                "\tbu.id,\n" +
                "\tbu.app_id,\n" +
                "\tbu.username,\n" +
                "\tbu.password,\n" +
                "\tbu.enabled,\n" +
                "\tbu.locked,\n" +
                "\tbu.valid_time\n" +
                "FROM\n" +
                "\tbase_user bu\n" +
                "\twhere bu.app_id=? and bu.username=? limit 1\n" +
                "\t";
        log.debug("query sql: {}", sql);
        BaseUser baseUser = jdbcTemplate.queryForObject(sql, new Object[]{appId, username}, (rs, rowNum) -> {
            BaseUser user = new BaseUser();
            user.setId(rs.getLong("id"));
            user.setAppId(rs.getLong("app_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setLocked(rs.getBoolean("locked"));
            user.setValidTime( DateUtil.parse(rs.getString("valid_time")));
            return user;
        });

        return baseUser;
    }

    /**
     * 根据用户id查询对应的角色
     * @param userId
     * @return
     */
    public List<GrantedAuthority> listRoleNameByUserId(Long userId) {
        String sql = "\nSELECT\n" +
                "\tbr.NAME roleName \n" +
                "FROM\n" +
                "\tbase_user_role bur\n" +
                "\tINNER JOIN base_role br ON bur.role_id = br.id \n" +
                "WHERE\n" +
                "\tbur.user_id =?";
        log.debug("query sql: {}", sql);
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> new SimpleGrantedAuthority(rs.getString("roleName")));
    }

    /**
     * 根据用户id查询对应的角色
     * @param userId
     * @return
     */
    public List<GrantedAuthority> listMenusByUserId(Long userId) {
        String sql = "\nSELECT\n" +
                "\tbr.NAME roleName \n" +
                "FROM\n" +
                "\tbase_user_role bur\n" +
                "\tINNER JOIN base_role br ON bur.role_id = br.id \n" +
                "WHERE\n" +
                "\tbur.user_id =?";
        log.debug("query sql: {}", sql);
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> new SimpleGrantedAuthority(rs.getString("roleName")));
    }
}
