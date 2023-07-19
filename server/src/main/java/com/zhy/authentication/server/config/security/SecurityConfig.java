package com.zhy.authentication.server.config.security;import com.zhy.authentication.server.service.BaseAppService;import com.zhy.authentication.server.service.BaseUserService;import lombok.extern.slf4j.Slf4j;import org.springframework.context.annotation.Bean;import org.springframework.security.authentication.AuthenticationManager;import org.springframework.security.config.annotation.ObjectPostProcessor;import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;import org.springframework.security.config.annotation.web.builders.HttpSecurity;import org.springframework.security.config.annotation.web.builders.WebSecurity;import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;import org.springframework.security.config.http.SessionCreationPolicy;import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;import org.springframework.security.web.context.SecurityContextPersistenceFilter;import javax.annotation.Resource;/** * SpringSecurity配置 * Created by macro on 2019/10/8. */@Slf4j@EnableWebSecurity@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // 开启方法注解权限配置public class SecurityConfig extends WebSecurityConfigurerAdapter {    /**     * 自定义权限不足处理器：返回状态码403     */    @Resource    private AccessDeniedHandlerImpl accessDeniedHandler;    /**     * 自定义未登录时：返回状态码401     */    @Resource    private AuthenticationEntryPointImpl authenticationEntryPoint;    /**     * 自定义登录失败处理器：返回状态码402     */    @Resource    private AuthenticationFailureHandlerImpl authenticationFailureHandler;    /**     * 自定义登录成功处理器：返回状态码200     */    @Resource    private AuthenticationSuccessHandlerImpl authenticationSuccessHandler;    /**     * 自定义注销成功处理器：返回状态码200     */    @Resource    private LogoutSuccessHandlerImpl logoutSuccessHandler;    /**     * 动态获取url权限配置     */    @Resource    private FilterInvocationSecurityMetadataSourceImpl filterInvocationSecurityMetadataSource;    /**     * 自定义权限判断管理器     */    @Resource    private AccessDecisionManagerImpl accessDecisionManager;    /**     * 认证时详细信息     */    @Resource    private AuthenticationDetailsSourceImpl authenticationDetailsSource;    /**     * 自定义上下文过滤器     */    @Resource    private MySecurityContextPersistenceFilter mySecurityContextPersistenceFilter;    @Bean    public PasswordEncoder passwordEncoder() {        return new BCryptPasswordEncoder();    }    @Bean    @Override    public AuthenticationManager authenticationManagerBean() throws Exception {        return super.authenticationManagerBean();    }    /**     * 扫描系统的白名单     * @param web     * @throws Exception     */    @Override    public void configure(WebSecurity web) throws Exception {        super.configure(web);        WebSecurity.IgnoredRequestConfigurer ignoring = web.ignoring();        // ignoring.antMatchers(HttpMethod.valueOf(method), whitelist.getPattern());    }    @Override    public void configure(HttpSecurity http) throws Exception {        // 未认证时：返回状态码401        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);        // 无权访问时：返回状态码403        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);        // url权限认证处理        http                // 支持EL表达式                // https://docs.spring.io/spring-security/site/docs/5.3.13.RELEASE/reference/html5/#el-access                .authorizeRequests()                //所有请求都需要认证                .anyRequest()                .authenticated()                // 使用自定义的 accessDecisionManager ⭐                /// .accessDecisionManager(accessDecisionManager)                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {                    @Override                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {                        o.setSecurityMetadataSource(filterInvocationSecurityMetadataSource); //动态获取url权限配置                        o.setAccessDecisionManager(accessDecisionManager); //权限判断                        return o;                    }                }) ;        // 将session策略设置为无状态的,通过token进行权限认证,并关闭默认的SecurityContextPersistenceFilter        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)                .and()                // 关闭SecurityContextPersistenceFilter                .securityContext().disable();        // 开启自动配置的登录功能        http.formLogin() //开启登录                //自定义登录请求路径(post)                .loginProcessingUrl("/base-user/login")                //自定义登录用户名密码属性名,默认为username和password                .usernameParameter("username").passwordParameter("password")                //验证成功处理器(前后端分离)：返回状态码200                // 也可以使用下面这种方式定时认证成功和认证失败处理                // https://docs.spring.io/spring-security/site/docs/5.3.13.RELEASE/reference/html5/#servlet-events                .successHandler(authenticationSuccessHandler)                //验证失败处理器(前后端分离)：返回状态码402                .failureHandler(authenticationFailureHandler)                //身份验证详细信息源(登录验证中增加额外字段)                .authenticationDetailsSource(authenticationDetailsSource)                .permitAll();        // 开启自动配置的注销功能        http.logout() //用户注销, 清空session                //自定义注销请求路径                .logoutUrl("/base-user/logout")                //注销成功处理器(前后端分离)：返回状态码200                .logoutSuccessHandler(logoutSuccessHandler);        // 添加Jwt过滤器        http.addFilterAfter(mySecurityContextPersistenceFilter, SecurityContextPersistenceFilter.class);        // 禁用csrf防御机制(跨域请求伪造)，这么做在测试和开发会比较方便。        http.csrf().disable();    }}