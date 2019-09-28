package com.zyplayer.doc.db.framework.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.data.repository.manage.entity.DbDatasource;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.interceptor.SqlLogInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class DatasourceUtil {
	private static SqlLogInterceptor sqlLogInterceptor = new SqlLogInterceptor();
	
	public static DatabaseFactoryBean createDatabaseFactoryBean(DbDatasource dbDatasource){
		try {
			// 数据源配置
			DruidDataSource dataSource = new DruidDataSource();
			dataSource.setDriverClassName(dbDatasource.getDriverClassName());
			dataSource.setUrl(dbDatasource.getSourceUrl());
			dataSource.setUsername(dbDatasource.getSourceName());
			dataSource.setPassword(dbDatasource.getSourcePassword());
			dataSource.setInitialSize(2);
			dataSource.setMinIdle(2);
			dataSource.setMaxActive(50);
			dataSource.setTestWhileIdle(true);
			dataSource.setTestOnBorrow(false);
			dataSource.setTestOnReturn(false);
			dataSource.setValidationQuery("select 1");
			dataSource.setMaxWait(3000);
			dataSource.setTimeBetweenEvictionRunsMillis(60000);
			dataSource.setMinEvictableIdleTimeMillis(3600000);
			// 重试3次，失败退出，源码里是errorCount > connectionErrorRetryAttempts，所以写成2就是3次、、、
			// CreateConnectionThread 源码在这个类里面
			dataSource.setConnectionErrorRetryAttempts(2);
			dataSource.setBreakAfterAcquireFailure(true);
			dataSource.setName("zyplayer-doc-db-" + dbDatasource.getId());
			DruidPooledConnection connection = dataSource.getConnection(3000);
			if (connection == null) {
				throw new ConfirmException("尝试获取该数据源连接失败：" + dbDatasource.getSourceUrl());
			}
			// 描述连接信息的对象
			DatabaseFactoryBean databaseFactoryBean = new DatabaseFactoryBean();
			Resource[] resources = null;
			String dbUrl = dbDatasource.getSourceUrl();
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			if (dbUrl.contains("mysql")) {
				// jdbc:mysql://192.168.0.1:3306/user_info?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&autoReconnect=true
				String[] urlParamArr = dbUrl.split("\\?");
				String[] urlDbNameArr = urlParamArr[0].split("/");
				if (urlDbNameArr.length >= 2) {
					databaseFactoryBean.setDbName(urlDbNameArr[urlDbNameArr.length - 1]);
					//databaseFactoryBean.setHost(urlDbNameArr[urlDbNameArr.length - 2]);
				}
				databaseFactoryBean.setDatabaseProduct(DatabaseFactoryBean.DatabaseProduct.MYSQL);
				resources = resolver.getResources("classpath:com/zyplayer/doc/db/framework/db/mapper/mysql/*.xml");
			} else if (dbUrl.contains("sqlserver")) {
				// jdbc:jtds:sqlserver://192.168.0.1:33434;socketTimeout=60;DatabaseName=user_info;
				String[] urlParamArr = dbUrl.split(";");
				//String[] urlDbNameArr = urlParamArr[0].split("/");
				//databaseFactoryBean.setHost(urlDbNameArr[urlDbNameArr.length - 1]);
				for (String urlParam : urlParamArr) {
					String[] keyValArr = urlParam.split("=");
					if (keyValArr.length >= 2 && keyValArr[0].equalsIgnoreCase("DatabaseName")) {
						databaseFactoryBean.setDbName(keyValArr[1]);
					}
				}
				databaseFactoryBean.setDatabaseProduct(DatabaseFactoryBean.DatabaseProduct.SQLSERVER);
				resources = resolver.getResources("classpath:com/zyplayer/doc/db/framework/db/mapper/sqlserver/*.xml");
			} else if (dbUrl.contains("oracle")) {
				// jdbc:oracle:thin:@127.0.0.1:1521:user_info
				// jdbc:oracle:thin:@127.0.0.1:1521/user_info
				// 代码是写好的，但还没有oracle的库让我测试过~
				String[] urlParamArr = dbUrl.split("\\?")[0].split("@");
				String[] urlDbNameArr = urlParamArr[0].split("/");
				if (urlDbNameArr.length <= 1) {
					urlDbNameArr = urlParamArr[0].split(":");
				}
				databaseFactoryBean.setDbName(urlDbNameArr[urlDbNameArr.length - 1]);
				databaseFactoryBean.setDatabaseProduct(DatabaseFactoryBean.DatabaseProduct.ORACLE);
				resources = resolver.getResources("classpath:com/zyplayer/doc/db/framework/db/mapper/oracle/*.xml");
			}
			connection.recycle();
			if (resources == null) {
				return null;
			}
			// 创建sqlSessionTemplate
			SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
			sqlSessionFactoryBean.setDataSource(dataSource);
			sqlSessionFactoryBean.setMapperLocations(resources);
			sqlSessionFactoryBean.setPlugins(new Interceptor[]{sqlLogInterceptor});
			SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactoryBean.getObject());
			// 组装自定义的bean
			databaseFactoryBean.setId(dbDatasource.getId());
			databaseFactoryBean.setCnName(dbDatasource.getName());
			databaseFactoryBean.setDataSource(dataSource);
			databaseFactoryBean.setSqlSessionTemplate(sqlSessionTemplate);
			databaseFactoryBean.setUrl(dbUrl);
			return databaseFactoryBean;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
}
