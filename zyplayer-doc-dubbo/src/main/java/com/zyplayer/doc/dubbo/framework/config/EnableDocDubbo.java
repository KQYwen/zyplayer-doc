package com.zyplayer.doc.dubbo.framework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;


/**
 * 开启dubbo模块注解
 *
 * @author 暮光：城中城
 * @since 2019年2月12日
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@ComponentScan(basePackages = {
	"com.zyplayer.doc.dubbo",
})
public @interface EnableDocDubbo {
}
