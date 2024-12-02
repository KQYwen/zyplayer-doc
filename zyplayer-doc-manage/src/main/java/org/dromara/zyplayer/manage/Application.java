package org.dromara.zyplayer.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 程序启动器
 *
 * @author 暮光：城中城
 * @author Sh1yu 2023年6月15日
 * @since 2018-11-27
 */
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {
		"org.dromara.zyplayer.manage",
		"org.dromara.zyplayer.data",
		"org.dromara.zyplayer.core"
})
public class Application extends SpringBootServletInitializer {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}

