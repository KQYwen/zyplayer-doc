package com.zyplayer.doc.wiki.framework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

/**
 * 开启wiki模块注解
 *
 * @author 暮光：城中城
 * @since 2019-02-17
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@ComponentScan(basePackages = {
		"com.zyplayer.doc.wiki",
})
public @interface EnableDocWiki {
}
