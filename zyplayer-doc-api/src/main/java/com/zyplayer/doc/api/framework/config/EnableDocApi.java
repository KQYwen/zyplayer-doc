package com.zyplayer.doc.api.framework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

/**
 * 开启api接口文档模块注解
 *
 * @author 暮光：城中城
 * @since 2021-11-04
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@ComponentScan(basePackages = {
		"com.zyplayer.doc.api",
})
public @interface EnableDocApi {

}
