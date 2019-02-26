//package com.zyplayer.doc.manage.framework.config;
//
//import com.atomikos.icatch.jta.UserTransactionImp;
//import com.atomikos.icatch.jta.UserTransactionManager;
//import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import com.zyplayer.doc.data.repository.support.interceptor.SqlLogInterceptor;
//import org.apache.ibatis.plugin.Interceptor;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.transaction.jta.JtaTransactionManager;
//
//import javax.sql.DataSource;
//import javax.transaction.TransactionManager;
//import javax.transaction.UserTransaction;
//import java.util.Properties;
//
///**
// * mybatis plus数据库配置
// */
////@Configuration
//public class MybatisPlusConfig {
//
//	/**
//	 * sql日志
//	 **/
//	private static final SqlLogInterceptor SQL_LOG_INTERCEPTOR;
//
//	static {
//		SQL_LOG_INTERCEPTOR = new SqlLogInterceptor();
//		Properties properties = new Properties();
//		SQL_LOG_INTERCEPTOR.setProperties(properties);
//	}
//
//	/**
//	 * 分布式事务配置
//	 */
//	@Configuration
//	static class JTATransactionManagerConfig {
//
//		@Bean(name = "userTransaction")
//		public UserTransaction userTransaction() throws Throwable {
//			UserTransactionImp userTransactionImp = new UserTransactionImp();
//			userTransactionImp.setTransactionTimeout(300);
//			return userTransactionImp;
//		}
//
//		@Bean(name = "atomikosTransactionManager")
//		public TransactionManager atomikosTransactionManager() {
//			UserTransactionManager userTransactionManager = new UserTransactionManager();
//			userTransactionManager.setForceShutdown(true);
//			return userTransactionManager;
//		}
//
//		@Bean(name = "transactionManager")
//		public PlatformTransactionManager transactionManager() throws Throwable {
//			UserTransaction userTransaction = userTransaction();
//			TransactionManager atomikosTransactionManager = atomikosTransactionManager();
//
//			JtaTransactionManager jtaTransactionManager = new JtaTransactionManager(userTransaction, atomikosTransactionManager);
//			jtaTransactionManager.setAllowCustomIsolationLevels(true);
//			jtaTransactionManager.setGlobalRollbackOnParticipationFailure(true);
//			jtaTransactionManager.setDefaultTimeout(30);
//
//			return jtaTransactionManager;
//		}
//	}
//
//	/**
//	 * 数据库配置
//	 */
//	@Configuration
//	@EnableTransactionManagement
//	@MapperScan(value = "com.zyplayer.doc.data.repository.manage.mapper", sqlSessionFactoryRef = "manageSqlSessionFactory")
//	static class ManageMybatisDbConfig {
//
//		@Value("${zyplayer.doc.manage.datasource.driverClassName}")
//		private String driverClassName;
//		@Value("${zyplayer.doc.manage.datasource.url}")
//		private String url;
//		@Value("${zyplayer.doc.manage.datasource.username}")
//		private String username;
//		@Value("${zyplayer.doc.manage.datasource.password}")
//		private String password;
//
//		@Bean(name = "manageDatasource")
//		public DataSource manageDatasource() {
//			Properties xaProperties = new Properties();
//			xaProperties.setProperty("driverClassName", driverClassName);
//			xaProperties.setProperty("url", url);
//			xaProperties.setProperty("username", username);
//			xaProperties.setProperty("password", password);
//			xaProperties.setProperty("maxActive", "500");
//			xaProperties.setProperty("testOnBorrow", "true");
//			xaProperties.setProperty("testWhileIdle", "true");
//			xaProperties.setProperty("validationQuery", "select 'x'");
//
//			AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
//			xaDataSource.setXaProperties(xaProperties);
//			xaDataSource.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
//			xaDataSource.setUniqueResourceName("manageDatasource");
//			xaDataSource.setMaxPoolSize(500);
//			xaDataSource.setMinPoolSize(1);
//			xaDataSource.setMaxLifetime(60);
//			return xaDataSource;
//		}
//
//		@Bean(name = "manageSqlSessionFactory")
//		public MybatisSqlSessionFactoryBean manageSqlSessionFactory() throws Exception {
//			MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
//			sqlSessionFactoryBean.setDataSource(manageDatasource());
//			sqlSessionFactoryBean.setPlugins(new Interceptor[]{SQL_LOG_INTERCEPTOR});
//
//			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//			sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/manage/*Mapper.xml"));
//			return sqlSessionFactoryBean;
//		}
//	}
//
//	@Bean
//	public PerformanceInterceptor performanceInterceptor() {
//		PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//		/* <!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 --> */
//		performanceInterceptor.setMaxTime(1000);
//		/* <!--SQL是否格式化 默认false--> */
//		performanceInterceptor.setFormat(true);
//		return performanceInterceptor;
//	}
//
//	@Bean
//	public PaginationInterceptor paginationInterceptor() {
//		return new PaginationInterceptor();
//	}
//}
