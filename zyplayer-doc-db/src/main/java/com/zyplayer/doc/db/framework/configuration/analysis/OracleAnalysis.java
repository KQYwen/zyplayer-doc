package com.zyplayer.doc.db.framework.configuration.analysis;

import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class OracleAnalysis implements AnalysisApi {
	
	@Override
	public Resource[] process(String dbUrl, DatabaseFactoryBean databaseFactoryBean) throws Exception {
		// jdbc:oracle:thin:@127.0.0.1:1521:user_info
		// 代码是写好的，但还没有oracle的库让我测试过~
		String[] urlParamArr = dbUrl.split("\\?")[0].split("@");
		String[] urlDbNameArr = urlParamArr[0].split("/");
		if (urlDbNameArr.length <= 1) {
			urlDbNameArr = urlParamArr[0].split(":");
		}
		databaseFactoryBean.setDbName(urlDbNameArr[urlDbNameArr.length - 1]);
		databaseFactoryBean.setDatabaseProduct(DatabaseProductEnum.ORACLE);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		return resolver.getResources("classpath:com/zyplayer/doc/db/framework/db/mapper/oracle/*.xml");
	}
	
}
