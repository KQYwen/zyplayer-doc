package com.zyplayer.doc.manage.framework.config;

import com.zyplayer.doc.db.framework.configuration.EnableDocDb;
import com.zyplayer.doc.db.framework.db.bean.DatabaseRegistrationBean;
import com.zyplayer.doc.dubbo.framework.config.EnableDocDubbo;
import com.zyplayer.doc.grpc.framework.config.EnableDocGrpc;
import com.zyplayer.doc.swagger.framework.configuration.EnableDocSwagger;
import com.zyplayer.doc.wiki.framework.config.EnableDocWiki;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 按需开启zyplayer-doc所有的服务
 *
 * @author 暮光：城中城
 * @since 2019年3月31日
 */
@Configuration
public class ZyplayerDocConfig {

//	@EnableDocGrpc // grpc默认不开启
//	public class enableDocGrpc{}
	
	@EnableDocWiki
	public class enableDocWiki{}
	
	@EnableDocDubbo
	public class enableDocDubbo{}
	
	@EnableDocDb
	public class enableDocDb{}
	
	@EnableDocSwagger(selfDoc = false)
	public class enableDocSwagger{}
	
	@Bean
	@ConfigurationProperties(prefix = "zyplayer.doc.db")
	public DatabaseRegistrationBean databaseRegistrationBean() {
		return new DatabaseRegistrationBean();
	}
}
