package com.zyplayer.doc.grpc.framework.config;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 开启grpc的注解
 *
 * @author 暮光：城中城
 * @since 2019年3月31日
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Documented
@ComponentScan(basePackages = {
	"com.zyplayer.doc.grpc",
})
public @interface EnableDocGrpc {
}
