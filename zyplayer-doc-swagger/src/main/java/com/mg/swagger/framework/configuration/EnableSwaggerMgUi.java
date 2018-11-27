package com.mg.swagger.framework.configuration;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.mg.swagger.framework.filter.MgUiTestFilter;

@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@Import({ SwaggerCommonConfiguration.class, MgUiTestFilter.class, SpringContextUtil.class })
public @interface EnableSwaggerMgUi {
	
	/**
	 * 是否自动把自身的swagger-resources加进来
	 * @return
	 */
	boolean selfDoc() default true;
	
	/**
	 * 启动后第一次访问没有数据情况下需要加载进来的swagger-resources地址
	 * @return
	 */
	String[] defaultResources() default {};
}
