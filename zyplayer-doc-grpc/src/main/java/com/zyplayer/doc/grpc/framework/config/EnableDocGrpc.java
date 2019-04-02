package com.zyplayer.doc.grpc.framework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

/**
 * 开启grpc的注解
 *
 * @author 暮光：城中城
 * @since 2019年3月31日
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@ComponentScan(basePackages = {
	"com.zyplayer.doc.grpc",
})
public @interface EnableDocGrpc {
}
