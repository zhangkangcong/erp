package com.sxt.system.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

/**
 * mybatisplus的配置类
 * @author ZKC
 *
 */
@Configuration
@ConditionalOnClass(value= {PaginationInterceptor.class})
public class MybatisPlusConfig {
	/**
	 *
	 * 分页插件
	 */
	@Bean
	public PaginationInterceptor  paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		//设置请求的页面大于最大页后操作，true调回到首页，false继续请求，默认false
		//paginationInterceptor.setOverflow(false);
		//设置最大单页限制数量，默认500条，-1不受限制
		//paginationInterceptor.setLimit(500);
		return paginationInterceptor;
	}

}
