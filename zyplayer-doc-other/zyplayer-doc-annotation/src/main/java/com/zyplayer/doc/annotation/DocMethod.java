package com.zyplayer.doc.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DocMethod {
	
	/**
	 * @return 方法的说明
	 */
	String value();
	
	/**
	 * @return 返回结果的说明
	 */
	String response() default "";
}
