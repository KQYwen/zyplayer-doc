package com.zyplayer.doc.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DocParams {
	
	/**
	 * @return 多个参数说明
	 */
	DocParam[] value();
}
