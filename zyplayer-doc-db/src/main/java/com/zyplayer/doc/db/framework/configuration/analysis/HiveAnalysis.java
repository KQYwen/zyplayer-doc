package com.zyplayer.doc.db.framework.configuration.analysis;

import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class HiveAnalysis implements AnalysisApi {
	
	@Override
	public Resource[] process(String dbUrl, DatabaseFactoryBean databaseFactoryBean) throws Exception {
		// jdbc:hive2://127.0.0.1:21050/ads_data;auth=noSasl
		String[] urlParamArr = dbUrl.split(";");
		String[] urlDbNameArr = urlParamArr[0].split("/");
		if (urlDbNameArr.length >= 2) {
			databaseFactoryBean.setDbName(urlDbNameArr[urlDbNameArr.length - 1]);
		}
		databaseFactoryBean.setDatabaseProduct(DatabaseProductEnum.HIVE);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		return resolver.getResources("classpath:com/zyplayer/doc/db/framework/db/mapper/hive/*.xml");
	}
	
}
