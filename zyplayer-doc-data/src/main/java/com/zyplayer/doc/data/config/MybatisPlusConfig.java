package com.zyplayer.doc.data.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageHelper;
import com.zyplayer.doc.data.repository.support.interceptor.SqlLogInterceptor;
import com.zyplayer.doc.data.utils.DruidDataSourceUtil;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * mybatis plus数据库配置
 */
@Configuration
public class MybatisPlusConfig {
	
	/**
	 * sql日志
	 **/
	private static final SqlLogInterceptor SQL_LOG_INTERCEPTOR = new SqlLogInterceptor();
	/**
	 * MYSQL 分页
	 **/
	private static final PageHelper MYSQL_PAGE_HELPER;
	
	static {
		MYSQL_PAGE_HELPER = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("dialect", "mysql");
		MYSQL_PAGE_HELPER.setProperties(properties);
	}
	
	/**
	 * 数据库配置
	 */
	@Configuration
	@EnableTransactionManagement
	@MapperScan(value = "com.zyplayer.doc.data.repository.manage.mapper", sqlSessionFactoryRef = "manageSqlSessionFactory")
	static class ManageMybatisDbConfig {
		
		@Value("${zyplayer.doc.manage.datasource.driverClassName}")
		private String driverClassName;
		@Value("${zyplayer.doc.manage.datasource.url}")
		private String url;
		@Value("${zyplayer.doc.manage.datasource.username}")
		private String username;
		@Value("${zyplayer.doc.manage.datasource.password}")
		private String password;
		@Resource
		private PaginationInterceptor paginationInterceptor;
		
		@Bean(name = "manageDatasource")
		public DataSource manageDatasource() throws Exception {
			return DruidDataSourceUtil.createDataSource(driverClassName, url, username, password, false);
		}
		
		@Bean(name = "manageSqlSessionFactory")
		public MybatisSqlSessionFactoryBean manageSqlSessionFactory() throws Exception {
			MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
			sqlSessionFactoryBean.setDataSource(manageDatasource());
			sqlSessionFactoryBean.setPlugins(new Interceptor[]{SQL_LOG_INTERCEPTOR, MYSQL_PAGE_HELPER, paginationInterceptor});
			
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/manage/*Mapper.xml"));
			return sqlSessionFactoryBean;
		}
	}
	
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}
}
