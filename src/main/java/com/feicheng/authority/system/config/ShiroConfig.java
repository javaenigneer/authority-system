package com.feicheng.authority.system.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.feicheng.authority.system.filter.URLPathMatchingFilter;
import com.feicheng.authority.system.properties.FeiChengProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro 配置类
 *
 * @author MrBird
 */
@Configuration
public class ShiroConfig {

    @Autowired
    private FeiChengProperties feiChengProperties;
//
//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.port}")
//    private int port;
//    @Value("${spring.redis.password:}")
//    private String password;
//    @Value("${spring.redis.timeout}")
//    private int timeout;
//    @Value("${spring.redis.database:0}")
//    private int database;
//
//    /**
//     * shiro 中配置 redis 缓存
//     *
//     * @return RedisManager
//     */
//    private RedisManager redisManager() {
//
//        RedisManager redisManager = new RedisManager();
//
//        redisManager.setHost(host + ":" + port);
//
//        if (StringUtils.isNotBlank(password))
//
//            redisManager.setPassword(password);
//
//        redisManager.setTimeout(timeout);
//
//        redisManager.setDatabase(database);
//
//        return redisManager;
//    }

//    private RedisCacheManager cacheManager() {
//
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//
//        redisCacheManager.setRedisManager(redisManager());
//
//        return redisCacheManager;
//    }

//    @Bean
//    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
//        return new LifecycleBeanPostProcessor();
//    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置 securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 登录的 url
        shiroFilterFactoryBean.setLoginUrl(feiChengProperties.getShiro().getLoginUrl());
        // 登录成功后跳转的 url
        shiroFilterFactoryBean.setSuccessUrl(feiChengProperties.getShiro().getSuccessUrl());
//        // 未授权 url
//        shiroFilterFactoryBean.setUnauthorizedUrl("/admin/login.html");

        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //设置免认证 url
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(feiChengProperties.getShiro().getAnonUrl(), ",");

        for (String url : anonUrls) {

            filterChainDefinitionMap.put(url, "anon");

        }

        Map<String, Filter> customisedFilter = new HashMap<>();

        customisedFilter.put("url", getURLPathMatchingFilter());

        filterChainDefinitionMap.put("/static/**", "anon");

        filterChainDefinitionMap.put("/swagger/**", "anon");

        filterChainDefinitionMap.put("/swagger-resources/**", "anon");

        filterChainDefinitionMap.put("/v2/**", "anon");

        filterChainDefinitionMap.put("/webjars/**", "anon");

//        filterChainDefinitionMap.put("/swagger-resources/configuration/ui/**", "anon");
//
//        filterChainDefinitionMap.put("/swagger-resources/configuration/security/**", "anon");

//        filterChainDefinitionMap.put("/admin/login.html", "anon");
//
//        filterChainDefinitionMap.put("/admin/register.html", "anon");
//
//        filterChainDefinitionMap.put("/admin/activate/**", "anon");
//
//        filterChainDefinitionMap.put("/login", "anon");
//
//        filterChainDefinitionMap.put("/register", "anon");
//
//        filterChainDefinitionMap.put("/logout", "anon");
//
//        filterChainDefinitionMap.put("/getCode", "anon");
//
//        filterChainDefinitionMap.put("/index.html", "anon");
//
//        filterChainDefinitionMap.put("/about.html", "anon");
//
//        filterChainDefinitionMap.put("/list.html", "anon");
//
//        filterChainDefinitionMap.put("/gbook.html", "anon");
//
//        filterChainDefinitionMap.put("/editor.html", "anon");
//
//        filterChainDefinitionMap.put("/article/detail/**", "anon");
//
//        filterChainDefinitionMap.put("/article/type/**", "anon");
//
//        filterChainDefinitionMap.put("/comment/add", "anon");
//
//        filterChainDefinitionMap.put("/contact/list", "anon");
//
//        filterChainDefinitionMap.put("/contact/add", "anon");
//
//        filterChainDefinitionMap.put("/swagger-ui.html#/", "anon");
//
//        filterChainDefinitionMap.put("/api/**", "anon");
//
//        filterChainDefinitionMap.put("/css/**", "anon");
//
//        filterChainDefinitionMap.put("/js/**", "anon");
//
//        filterChainDefinitionMap.put("/images/**", "anon");
//
//        filterChainDefinitionMap.put("/lib/**", "anon");
//
//        filterChainDefinitionMap.put("/myJs/**", "anon");
//
//        filterChainDefinitionMap.put("/Content/**", "anon");


        // 配置退出过滤器，其中具体的退出代码 Shiro已经替我们实现了
//        filterChainDefinitionMap.put(feiChengProperties.getShiro().getLogoutUrl(), "logout");

        // 除上以外所有 url都必须认证通过才可以访问，未通过认证自动访问 LoginUrl
        filterChainDefinitionMap.put("/**", "url");

        shiroFilterFactoryBean.setFilters(customisedFilter);

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    public URLPathMatchingFilter getURLPathMatchingFilter() {

        return new URLPathMatchingFilter();

    }

    @Bean
    public SecurityManager securityManager(ShiroRealm shiroRealm) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 配置 SecurityManager，并注入 shiroRealm
        securityManager.setRealm(shiroRealm);

        // 配置 shiro session管理器
//        securityManager.setSessionManager(sessionManager());

        // 配置 缓存管理类 cacheManager
//        securityManager.setCacheManager(cacheManager());

        // 配置 rememberMeCookie
//        securityManager.setRememberMeManager(rememberMeManager());

        return securityManager;
    }

//    /**
//     * rememberMe cookie 效果是重开浏览器后无需重新登录
//     *
//     * @return SimpleCookie
//     */
//    private SimpleCookie rememberMeCookie() {
//        // 设置 cookie 名称，对应 login.html 页面的 <input type="checkbox" name="rememberMe"/>
//        SimpleCookie cookie = new SimpleCookie("rememberMe");
//        // 设置 cookie 的过期时间，单位为秒，这里为一天
//        cookie.setMaxAge(febsProperties.getShiro().getCookieTimeout());
//        return cookie;
//    }

//    /**
//     * cookie管理对象
//     *
//     * @return CookieRememberMeManager
//     */
//    private CookieRememberMeManager rememberMeManager() {
//        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
//        cookieRememberMeManager.setCookie(rememberMeCookie());
//        // rememberMe cookie 加密的密钥
//        String encryptKey = "febs_shiro_key";
//        byte[] encryptKeyBytes = encryptKey.getBytes(StandardCharsets.UTF_8);
//        String rememberKey = Base64Utils.encodeToString(Arrays.copyOf(encryptKeyBytes, 16));
//        cookieRememberMeManager.setCipherKey(Base64.decode(rememberKey));
//        return cookieRememberMeManager;
//    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 用于开启 Thymeleaf 中的 shiro 标签的使用
     *
     * @return ShiroDialect shiro 方言对象
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * session 管理对象
     *
     * @return DefaultWebSessionManager
     */
//    @Bean
//    public DefaultWebSessionManager sessionManager() {
//        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
//        Collection<SessionListener> listeners = new ArrayList<>();
//        listeners.add(new ShiroSessionListener());
//        // 设置 session超时时间
//        sessionManager.setGlobalSessionTimeout(feiChengProperties.getShiro().getSessionTimeout() * 1000L);
//        sessionManager.setSessionListeners(listeners);
////        sessionManager.setSessionDAO(redisSessionDAO());
//        sessionManager.setSessionIdUrlRewritingEnabled(false);
//        return sessionManager;
//    }
}