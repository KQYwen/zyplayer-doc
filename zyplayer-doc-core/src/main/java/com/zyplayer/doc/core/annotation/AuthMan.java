package com.zyplayer.doc.core.annotation;

import java.lang.annotation.*;

/**
 * 用户登录校验注解
 *
 * @author 暮光：城中城
 * @since 2019年5月29日
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthMan {
	String[] value() default {};
	
	boolean all() default false;
}
