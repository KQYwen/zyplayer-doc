package com.zyplayer.doc.manage.framework.config;

import com.zyplayer.doc.db.framework.configuration.EnableDocDb;
import com.zyplayer.doc.db.framework.db.bean.DatabaseRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author
 * @Date 2018/11/11
 **/
@EnableDocDb
@Configuration
public class DocDatabaseRegistrationConfig {
	
	@Bean
	@ConfigurationProperties(prefix = "zyplayer.doc.db")
	public DatabaseRegistrationBean databaseRegistrationBean(){
		return new DatabaseRegistrationBean();
	}
	
}
