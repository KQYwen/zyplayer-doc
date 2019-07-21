package com.zyplayer.doc.elasticsearch.framework.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@ComponentScan(basePackages = {
	"com.zyplayer.doc.elasticsearch",
})
public @interface EnableDocEs {
}
