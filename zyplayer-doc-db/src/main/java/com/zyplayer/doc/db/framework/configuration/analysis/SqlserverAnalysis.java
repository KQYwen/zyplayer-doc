package com.zyplayer.doc.db.framework.configuration.analysis;

import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Sqlserver链接url解析
 *
 * @author 暮光：城中城
 * @since 2021-05-13
 */
public class SqlserverAnalysis implements AnalysisApi {
	
	@Override
	public Resource[] process(String dbUrl, DatabaseFactoryBean databaseFactoryBean) throws Exception {
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
		databaseFactoryBean.setDatabaseProduct(DatabaseProductEnum.SQLSERVER);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		return resolver.getResources("classpath:com/zyplayer/doc/db/framework/db/mapper/sqlserver/*.xml");
	}
	
}
