package com.zyplayer.doc.db.framework.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.data.repository.manage.entity.DbDatasource;
import com.zyplayer.doc.data.utils.DruidDataSourceUtil;
import com.zyplayer.doc.db.framework.configuration.analysis.*;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import com.zyplayer.doc.db.framework.db.interceptor.SqlLogInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库连接工具类
 *
 * @author 暮光：城中城
 * @since 2019-07-04
 */
public class DatasourceUtil {
	private static final SqlLogInterceptor sqlLogInterceptor = new SqlLogInterceptor();
	// url解析
	private static final Map<String, AnalysisApi> analysisApiMap = new HashMap<String, AnalysisApi>() {{
		put(DatabaseProductEnum.MYSQL.getDriverClassName(), new MysqlAnalysis());
		put(DatabaseProductEnum.HIVE.getDriverClassName(), new HiveAnalysis());
		put(DatabaseProductEnum.ORACLE.getDriverClassName(), new OracleAnalysis());
		put(DatabaseProductEnum.POSTGRESQL.getDriverClassName(), new PostgresqlAnalysis());
		put(DatabaseProductEnum.SQLSERVER.getDriverClassName(), new SqlserverAnalysis());
	}};
	
	public static DatabaseFactoryBean createDatabaseFactoryBean(DbDatasource dbDatasource, boolean breakAfterAcquireFailure) throws Exception {
		// 描述连接信息的对象
		DatabaseFactoryBean databaseFactoryBean = new DatabaseFactoryBean();
		String dbUrl = dbDatasource.getSourceUrl();
		String driverClassName = dbDatasource.getDriverClassName();
		AnalysisApi analysisApi = analysisApiMap.get(driverClassName);
		if (analysisApi == null) {
			throw new ConfirmException("暂未支持的数据源类型");
		}
		Resource[] resources = analysisApi.process(dbUrl, databaseFactoryBean);
		// 数据源配置
		DruidDataSource dataSource = DruidDataSourceUtil.createDataSource(dbDatasource.getDriverClassName(), dbDatasource.getSourceUrl(), dbDatasource.getSourceName(), dbDatasource.getSourcePassword(), false);
		if (breakAfterAcquireFailure) {
			dataSource.close();
			// 先关闭会自动关闭的数据源，再创建一个会重连的
			dataSource = DruidDataSourceUtil.createDataSource(dbDatasource.getDriverClassName(), dbDatasource.getSourceUrl(), dbDatasource.getSourceName(), dbDatasource.getSourcePassword(), true);
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
	}
}
