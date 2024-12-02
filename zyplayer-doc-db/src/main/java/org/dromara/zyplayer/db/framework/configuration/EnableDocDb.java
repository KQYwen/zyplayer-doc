package org.dromara.zyplayer.db.framework.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

/**
 * 开启db模块的注解
 *
 * @author 暮光：城中城
 * @since 2018-11-27
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@ComponentScan(basePackages = {
		"org.dromara.zyplayer.db",
})
public @interface EnableDocDb {
}
