package com.sxt.system.config;

import com.sxt.system.realm.UserRealm;
import com.sxt.system.shiro.OptionsAccessControlFilter;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.IRedisManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author shiroUtils
 *
 */
@Configuration
@ConditionalOnWebApplication(type = Type.SERVLET)
@ConditionalOnClass(value = { SecurityManager.class })
@EnableConfigurationProperties(ShiroProperties.class)
public class ShiroAutoConfiguration {
	private static final String SHIRO_FILTER = "shiroFilter";
    @Autowired
    private  ShiroProperties shiroProperties;
    @Autowired
    private RedisProperties redisProperties;

	/**
	 * 声明凭证匹配器
	 */
	@Bean("credentialsMatcher")
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		credentialsMatcher.setHashAlgorithmName(shiroProperties.getHashAlgorithmName());
		credentialsMatcher.setHashIterations(shiroProperties.getHashIterations());
		return credentialsMatcher;
	}

	/**
	 * 声明userRealm
	 */
	@Bean("userRealm")
	public UserRealm userRealm(CredentialsMatcher credentialsMatcher) {
		UserRealm userRealm = new UserRealm();
		// 注入凭证匹配器
		userRealm.setCredentialsMatcher(credentialsMatcher);
		return userRealm;
	}

	/**
	 * 配置SecurityManager 声明安全管理器
	 */
	@Bean("securityManager")
	public SecurityManager securityManager(DefaultWebSessionManager defaultWebSessionManager, SessionDAO redisSession,UserRealm userRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 注入userRealm
		securityManager.setRealm(userRealm);
		defaultWebSessionManager.setSessionDAO(redisSession);
		securityManager.setSessionManager(defaultWebSessionManager);
		return securityManager;
	}

	/**
	 * 配置shiro的过滤器,web过滤器的id必须和web.xml里面的shiroFilter的targetBeanName的值一样
	 */
	@Bean(SHIRO_FILTER)
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		// 设置安全管理器
		factoryBean.setSecurityManager(securityManager);

		//创建自定义Filter
		OptionsAccessControlFilter filter = new OptionsAccessControlFilter();
		Map<String, Filter> map=new HashMap<>();
		map.put("options", filter);
//		//配置过滤器
		factoryBean.setFilters(map);

		// 设置未登陆的时要跳转的页面
		factoryBean.setLoginUrl(shiroProperties.getLoginUrl());
		Map<String, String> filterChainDefinitionMap = new HashMap<>();
		// 设置放行的路径
		if (shiroProperties.getAnonUrls() != null && shiroProperties.getAnonUrls().length > 0) {
			for (String anon : shiroProperties.getAnonUrls()) {
				filterChainDefinitionMap.put(anon, "anon");
				//filterChainDefinitionMap.put(anon, "options");
			}
		}
		// 设置登出的路径
		if (null != shiroProperties.getLogOutUrl()) {
			filterChainDefinitionMap.put(shiroProperties.getLogOutUrl(), "logout");
			//filterChainDefinitionMap.put(shiroProperties.getLogOutUrl(), "options");
		}
		// 设置拦截的路径
		if (shiroProperties.getAuthcUlrs() != null && shiroProperties.getAuthcUlrs().length > 0) {
			for (String authc : shiroProperties.getAuthcUlrs()) {
				filterChainDefinitionMap.put(authc, "authc");
				//filterChainDefinitionMap.put(authc, "options");
			}
		}

		factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return factoryBean;
	}

	/**
	 * 注册过滤器
	 */
	@Bean
	public FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBeanDelegatingFilterProxy(){
		FilterRegistrationBean<DelegatingFilterProxy> bean=new FilterRegistrationBean<>();
		//创建过滤器
		DelegatingFilterProxy proxy=new DelegatingFilterProxy();
		proxy.setTargetBeanName("shiroFilter");
		proxy.setTargetFilterLifecycle(true);
		bean.setFilter(proxy);
		List<String> servletNames=new ArrayList<>();
		servletNames.add(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME);
		bean.setServletNames(servletNames);
		return bean;
	}

	/**
	 * 注册shiro的委托过滤器，相当于之前在web.xml里面配置的
	 * 
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<DelegatingFilterProxy> delegatingFilterProxy() {
		FilterRegistrationBean<DelegatingFilterProxy> filterRegistrationBean = new FilterRegistrationBean<DelegatingFilterProxy>();
		DelegatingFilterProxy proxy = new DelegatingFilterProxy();
		proxy.setTargetFilterLifecycle(true);
		proxy.setTargetBeanName(SHIRO_FILTER);
		filterRegistrationBean.setFilter(proxy);
		return filterRegistrationBean;
	}

	/* 加入注解的使用，不加入这个注解不生效--开始 */
	/**
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		advisorAutoProxyCreator.setProxyTargetClass(true);
		return advisorAutoProxyCreator;
	}
	/* 加入注解的使用，不加入这个注解不生效--结束 */


	/**
	 * 使用redis来存储登录信息
	 * sessionDAO 还需要设置给sessionManager
	 */
	@Bean
	public  SessionDAO redisSessionDAO(IRedisManager redisManager){
		RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
		redisSessionDAO.setRedisManager(redisManager);//操作哪个redis
		redisSessionDAO.setExpire(7*24*3600);//用户的登录信息保存多久
		return redisSessionDAO;
	}

	@Bean
	public IRedisManager redisManager(){
		RedisManager redisManager = new RedisManager();
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		//链接池最大量20，并发特别大时
		jedisPoolConfig.setMaxTotal(redisProperties.getJedis().getPool().getMaxActive());
		//链接池的最大剩余量15个，并发不大
		jedisPoolConfig.setMaxIdle(redisProperties.getJedis().getPool().getMaxIdle());
		//链接池初始就10个
		jedisPoolConfig.setMinIdle(redisProperties.getJedis().getPool().getMinIdle());
		JedisPool jedisPool = new JedisPool(jedisPoolConfig,redisProperties.getHost(),redisProperties.getPort(),2000,redisProperties.getPassword());
		redisManager.setJedisPool(jedisPool);
		return redisManager;
	}


}
