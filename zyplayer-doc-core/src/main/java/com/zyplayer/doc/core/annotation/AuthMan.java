package com.zyplayer.doc.core.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthMan {
	String[] value() default {};
	String authUrl() default "common/authfailed";
	boolean all() default false;
}
