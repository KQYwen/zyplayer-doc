package com.zyplayer.doc.manage;

import com.zyplayer.doc.core.util.ZyplayerDocVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.util.Optional;

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
		"com.zyplayer.doc.manage",
		"com.zyplayer.doc.data",
		"com.zyplayer.doc.core"
})
public class Application extends SpringBootServletInitializer {
	
	private static Logger logger = LoggerFactory.getLogger(Application.class);
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
	
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext application = SpringApplication.run(Application.class, args);
	}
}

