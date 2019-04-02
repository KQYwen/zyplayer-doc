package com.zyplayer.doc.manage.framework.config;

import com.zyplayer.doc.db.framework.configuration.EnableDocDb;
import com.zyplayer.doc.db.framework.db.bean.DatabaseRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 开启zyplayer-doc-db服务
 *
 * @author 暮光：城中城
 * @since 2018年11月11日
 */
@EnableDocDb
@Configuration
public class DocDatabaseRegistrationConfig {
	
	@Bean
	@ConfigurationProperties(prefix = "zyplayer.doc.db")
	public DatabaseRegistrationBean databaseRegistrationBean(){
		return new DatabaseRegistrationBean();
	}
	
}
