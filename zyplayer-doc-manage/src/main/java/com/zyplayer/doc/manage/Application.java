package com.zyplayer.doc.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 */
@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = {"com.zyplayer.doc.manage", "com.zyplayer.doc.data"})
public class Application extends SpringBootServletInitializer {

	private static Logger logger = LoggerFactory.getLogger(Application.class);

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext application = SpringApplication.run(Application.class, args);
		Environment env = application.getEnvironment();
		String contextPath = env.getProperty("server.servlet.context-path");
		contextPath = Optional.ofNullable(contextPath).orElse("").replaceFirst("/", "");
		contextPath = (contextPath.length() <= 0 || contextPath.endsWith("/")) ? contextPath : contextPath + "/";
		String hostAddress = InetAddress.getLocalHost().getHostAddress();
		String serverPort = env.getProperty("server.port");
		String urlCtx = hostAddress + ":" + serverPort + "/" + contextPath;
		logger.info("\n----------------------------------------------------------\n\t" +
						"\t\t地址列表\n\t" +
						"管理地址：http://{}\n" +
						"----------------------------------------------------------",
				urlCtx
		);
	}
}

