package com.zyplayer.doc.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(DocParams.class)
public @interface DocParam {
	
	/**
	 * @return 说明
	 */
	String value();
	
	/**
	 * @return 字段名
	 */
	String name() default "";
}
