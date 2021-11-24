package com.zyplayer.doc.swaggerplus.framework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

/**
 * 开启swagger模块注解
 *
 * @author 暮光：城中城
 * @since 2021-11-04
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@ComponentScan(basePackages = {
		"com.zyplayer.doc.swaggerplus",
})
public @interface EnableDocSwaggerPlus {

}
