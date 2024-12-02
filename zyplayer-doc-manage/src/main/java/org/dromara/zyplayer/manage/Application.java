package org.dromara.zyplayer.manage;

import org.dromara.zyplayer.core.util.ZyplayerDocVersion;
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
		ConfigurableApplicationContext application = new SpringApplicationBuilder(Application.class)
				.beanNameGenerator(new CustomGenerator()).run(args);
		Environment env = application.getEnvironment();
		String contextPath = env.getProperty("server.servlet.context-path");
		contextPath = Optional.ofNullable(contextPath).orElse("").replaceFirst("/", "");
		contextPath = (contextPath.length() <= 0 || contextPath.endsWith("/")) ? contextPath : contextPath + "/";
		String hostAddress = getLocalHostAddress();
		String serverPort = env.getProperty("server.port");
		String urlCtx = hostAddress + ":" + serverPort + "/" + contextPath;
		logger.info("\n" +
						"-------------------------------------------------------------\n" +
						"    zyplayer-doc Launch completed, Current version: {}\n" +
						"    Access address: http://{}\n" +
						"-------------------------------------------------------------",
				ZyplayerDocVersion.version, urlCtx
		);
	}
	
	public static class CustomGenerator implements BeanNameGenerator {
		@Override
		public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
			return definition.getBeanClassName();
		}
	}
	
	private static String getLocalHostAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			logger.error("获取本机IP失败：" + e.getMessage());
		}
		return "127.0.0.1";
	}
}

