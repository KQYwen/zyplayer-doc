package com.zyplayer.doc.db.framework.configuration;

import java.sql.DatabaseMetaData;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import com.zyplayer.doc.db.framework.db.bean.DbConfigBean;
import com.zyplayer.doc.db.framework.db.interceptor.SqlLogInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.ApplicationListener;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean.DatabaseProduct;
import com.zyplayer.doc.db.framework.db.bean.DatabaseRegistrationBean;

@Component
public class ApplicationListenerBean implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	DatabaseRegistrationBean databaseRegistrationBean;
	
	private volatile static boolean IS_INIT = false;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (databaseRegistrationBean == null || IS_INIT) {
			return;
		}
		// 会被调用两次
		IS_INIT = true;
		Integer dataSourceIndex = 0;
		SqlLogInterceptor sqlLogInterceptor = new SqlLogInterceptor();
		List<DatabaseFactoryBean> databaseFactoryBeanList = new LinkedList<>();
		for (DbConfigBean dbConfigBean : databaseRegistrationBean.getDbConfigList()) {
			try {
				// 数据源配置
				Properties xaProperties = new Properties();
				xaProperties.setProperty("driverClassName", dbConfigBean.getDriverClassName());
				xaProperties.setProperty("url", dbConfigBean.getUrl());
				xaProperties.setProperty("username", dbConfigBean.getUsername());
				xaProperties.setProperty("password", dbConfigBean.getPassword());
				xaProperties.setProperty("maxActive", "500");
				xaProperties.setProperty("testOnBorrow", "true");
				xaProperties.setProperty("testWhileIdle", "true");
				xaProperties.setProperty("validationQuery", "select 'x'");
				// 数据源
				AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
				dataSource.setXaProperties(xaProperties);
				dataSource.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
				dataSource.setUniqueResourceName("zyplayer-doc-db" + (dataSourceIndex++));
				dataSource.setMaxPoolSize(500);
				dataSource.setMinPoolSize(1);
				dataSource.setMaxLifetime(60);
				// 描述连接信息的对象
				DatabaseFactoryBean databaseFactoryBean = new DatabaseFactoryBean();
				DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
				String productName = metaData.getDatabaseProductName().toLowerCase();
				Resource[] resources = null;
				String dbUrl = metaData.getURL();
				PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
				if (productName.indexOf("mysql") >= 0) {
					// jdbc:mysql://192.168.0.1:3306/user_info?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&autoReconnect=true
					String[] urlParamArr = dbUrl.split("\\?");
					String[] urlDbNameArr = urlParamArr[0].split("/");
					if (urlDbNameArr.length >= 2) {
						databaseFactoryBean.setDbName(urlDbNameArr[urlDbNameArr.length - 1]);
						databaseFactoryBean.setHost(urlDbNameArr[urlDbNameArr.length - 2]);
					}
					databaseFactoryBean.setDatabaseProduct(DatabaseProduct.MYSQL);
					resources = resolver.getResources("classpath:com/zyplayer/doc/db/framework/db/mapper/mysql/*.xml");
				} else if (productName.indexOf("sql server") >= 0) {
					// jdbc:jtds:sqlserver://192.168.0.1:33434;socketTimeout=60;DatabaseName=user_info;
					String[] urlParamArr = dbUrl.split(";");
					String[] urlDbNameArr = urlParamArr[0].split("/");
					databaseFactoryBean.setHost(urlDbNameArr[urlDbNameArr.length - 1]);
					for (String urlParam : urlParamArr) {
						String[] keyValArr = urlParam.split("=");
						if (keyValArr.length >= 2 && keyValArr[0].equalsIgnoreCase("DatabaseName")) {
							databaseFactoryBean.setDbName(keyValArr[1]);
						}
					}
					databaseFactoryBean.setDatabaseProduct(DatabaseProduct.SQLSERVER);
					resources = resolver.getResources("classpath:com/zyplayer/doc/db/framework/db/mapper/sqlserver/*.xml");
				}
				if (resources == null) {
					continue;
				}
				// 创建sqlSessionTemplate
				SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
				sqlSessionFactoryBean.setDataSource(dataSource);
				sqlSessionFactoryBean.setMapperLocations(resources);
				sqlSessionFactoryBean.setPlugins(new Interceptor[]{sqlLogInterceptor});
				SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
				// 组装自定义的bean
				databaseFactoryBean.setDataSource(dataSource);
				databaseFactoryBean.setSqlSessionTemplate(sqlSessionTemplate);
				databaseFactoryBean.setUrl(dbUrl);
				databaseFactoryBeanList.add(databaseFactoryBean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		databaseRegistrationBean.setDatabaseFactoryBeanList(databaseFactoryBeanList);
	}
	
}
