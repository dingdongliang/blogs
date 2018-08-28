package net.htjs.blog.config;

import lombok.extern.slf4j.Slf4j;
import net.htjs.blog.constant.SystemConstant;
import net.htjs.blog.shiro.KickoutSessionControlFilter;
import net.htjs.blog.shiro.AuthenticationFilter;
import net.htjs.blog.shiro.ShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * blog/net.htjs.blog.config
 *
 * @Description: 权限控制，主要配置shiroFilter里面的内容
 * @Author: dingdongliang
 * @Date: 2018/8/14 10:25
 */
@Configuration
@Slf4j
public class ShiroConfigure {

    @Bean
    public EhCacheManager getEhCacheManager() {
        EhCacheManager ehcacheManager = new EhCacheManager();
        ehcacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return ehcacheManager;
    }

    /**
     * 设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等
     *
     * @return org.apache.shiro.web.servlet.SimpleCookie
     * @author dingdongliang
     * @date 2018/4/18 14:27
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //cookie生效时间30天 ,单位秒
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * 生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     *
     * @return org.apache.shiro.web.mgt.CookieRememberMeManager
     * @author dingdongliang
     * @date 2018/4/17 17:02
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //加密的密钥,默认AES算法,密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode(SystemConstant.AES_KEY));
        return cookieRememberMeManager;
    }

    /**
     * Shiro的Web过滤器Factory 命名:shiroFilter
     *
     * @param securityManager 安全管理器
     * @return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     * @author dingdongliang
     * @date 2018/4/18 14:27
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        //这里的配置适用于SpringMVC，不适用于SpringBoot
        shiroFilter.setLoginUrl(SystemConstant.LOGIN_PATH);
        //制定登录成功后的连接，但有时候并没用作用，具体细节见源码onLoginSuccess、redirectToSaveRequest方法
        shiroFilter.setSuccessUrl(SystemConstant.LOGIN_PATH);
        //未授权时跳转的页面
        shiroFilter.setUnauthorizedUrl(SystemConstant.LOGIN_PATH);

        //自定义拦截器，包含限制同一帐号同时在线的个数，添加casFilter到shiroFilter中，未授权的跳转等
        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        filtersMap.put(SystemConstant.KICKOUT, kickoutSessionControlFilter());
        filtersMap.put("authc", new AuthenticationFilter());

        shiroFilter.setFilters(filtersMap);

        Map<String, String> filterChainMap = new LinkedHashMap<>();

        // 有关swagger2的配置，生产环境中注意修改
        filterChainMap.put("/v2/**", SystemConstant.ANON);
        filterChainMap.put("/swagger/**", SystemConstant.ANON);
        filterChainMap.put("/swagger-ui.html", SystemConstant.ANON);
        filterChainMap.put("/swagger-resources/**", SystemConstant.ANON);
        filterChainMap.put("/webjars/**", SystemConstant.ANON);

        //静态资源忽略权限审查配置
        filterChainMap.put("/css/**", SystemConstant.ANON);
        filterChainMap.put("/images/**", SystemConstant.ANON);
        filterChainMap.put("/lib/**", SystemConstant.ANON);
        filterChainMap.put("/js/**", SystemConstant.ANON);
        filterChainMap.put("/fonts/**", SystemConstant.ANON);
        filterChainMap.put("/plugins/**", SystemConstant.ANON);
        filterChainMap.put("/upload/**", SystemConstant.ANON);
        filterChainMap.put("/json/**", SystemConstant.ANON);
        filterChainMap.put("/favicon.ico", SystemConstant.ANON);

        //数据源druid访问的控制，生产环境中注意修改
        filterChainMap.put("/druid/**", SystemConstant.ANON);

        //前端页面访问不需要权限,登录页面以及登录验证不需要权限
        filterChainMap.put("/", "anon");
        filterChainMap.put("/sort/**", SystemConstant.ANON);
        filterChainMap.put("/detail/**", SystemConstant.ANON);
        filterChainMap.put(SystemConstant.LOGIN_PATH, SystemConstant.ANON);
        filterChainMap.put("/login", SystemConstant.ANON);

        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainMap.put("/logout", "logout");

        filterChainMap.put("/**", "authc");
        shiroFilter.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilter;
    }

    /**
     * 设定自定义Realm 、缓存和rememberme管理
     *
     * @return org.apache.shiro.mgt.SecurityManager
     * @author dingdongliang
     * @date 2018/4/18 14:27
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
        //用户授权/认证信息Cache, 采用EhCache 缓存
        securityManager.setCacheManager(getEhCacheManager());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    /**
     * 限制同一账号登录同时登录人数控制
     *
     * @return net.htjs.blog.shiro.KickoutSessionControlFilter
     * @author dingdongliang
     * @date 2018/4/18 14:28
     */
    private KickoutSessionControlFilter kickoutSessionControlFilter() {
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();

        kickoutSessionControlFilter.setCacheManager(getEhCacheManager());
        kickoutSessionControlFilter.setKickoutAfter(SystemConstant.KICKOUT_AFTER);
        kickoutSessionControlFilter.setMaxSession(SystemConstant.ACCOUNT_MAX_SESSION);

        return kickoutSessionControlFilter;
    }

    /**
     * 凭证匹配器，这里用来检测密码，注意加密格式，可以扩展实现输入密码错误次数后锁定等功能
     *
     * @return org.apache.shiro.authc.credential.HashedCredentialsMatcher
     * @author dingdongliang
     * @date 2018/4/10 8:49
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(SystemConstant.ALGORITHMNAME);
        credentialsMatcher.setHashIterations(SystemConstant.HASHITERATIONS);
        return credentialsMatcher;
    }

    /**
     * 自定义Realm，设置加密算法
     *
     * @return net.htjs.blog.shiro.ShiroRealm
     * @author dingdongliang
     * @date 2018/4/18 14:28
     */
    @Bean
    public ShiroRealm userRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return shiroRealm;
    }

    /**
     * Shiro生命周期处理器
     *
     * @return org.apache.shiro.spring.LifecycleBeanPostProcessor
     * @author dingdongliang
     * @date 2018/4/18 14:28
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    /**
     * 开启shiro aop注解支持.必须设置生命周期处理器
     *
     * @param securityManager 安全管理器
     * @return org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     * @author dingdongliang
     * @date 2018/4/18 14:28
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}

