package com.zyplayer.doc.grpc.framework.config;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * grpc服务的注解
 *
 * @author 暮光：城中城
 * @since 2019年3月31日
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface DocGrpcService {
	
	String service() default "";
}
