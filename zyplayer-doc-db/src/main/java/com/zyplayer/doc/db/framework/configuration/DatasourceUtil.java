package com.zyplayer.doc.db.framework.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.zyplayer.doc.data.repository.manage.entity.DbDatasource;
import com.zyplayer.doc.data.utils.DruidDataSourceUtil;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.interceptor.SqlLogInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class DatasourceUtil {
	private static SqlLogInterceptor sqlLogInterceptor = new SqlLogInterceptor();
	
	public static DatabaseFactoryBean createDatabaseFactoryBean(DbDatasource dbDatasource, boolean breakAfterAcquireFailure) throws Exception {
		Resource[] resources = null;
		// 描述连接信息的对象
		DatabaseFactoryBean databaseFactoryBean = new DatabaseFactoryBean();
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		String dbUrl = dbDatasource.getSourceUrl();
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
		} else if (dbUrl.contains("postgresql")) {
			// jdbc:mysql://192.168.0.1:3306/user_info?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&autoReconnect=true
			String[] urlParamArr = dbUrl.split("\\?");
			String[] urlDbNameArr = urlParamArr[0].split("/");
			if (urlDbNameArr.length >= 2) {
				databaseFactoryBean.setDbName(urlDbNameArr[urlDbNameArr.length - 1]);
				//databaseFactoryBean.setHost(urlDbNameArr[urlDbNameArr.length - 2]);
			}
			databaseFactoryBean.setDatabaseProduct(DatabaseFactoryBean.DatabaseProduct.POSTGRESQL);
			resources = resolver.getResources("classpath:com/zyplayer/doc/db/framework/db/mapper/postgresql/*.xml");
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
		if (resources == null) {
			return null;
		}
		// 数据源配置
		DruidDataSource dataSource = DruidDataSourceUtil.createDataSource(dbDatasource.getDriverClassName(), dbDatasource.getSourceUrl(), dbDatasource.getSourceName(), dbDatasource.getSourcePassword(), breakAfterAcquireFailure);
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
	}
}
