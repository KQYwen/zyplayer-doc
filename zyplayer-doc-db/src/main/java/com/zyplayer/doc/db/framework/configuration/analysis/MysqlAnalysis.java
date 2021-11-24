package com.zyplayer.doc.db.framework.configuration.analysis;

import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * mysql链接url解析
 *
 * @author 暮光：城中城
 * @since 2021-05-13
 */
public class MysqlAnalysis implements AnalysisApi {
	
	@Override
	public Resource[] process(String dbUrl, DatabaseFactoryBean databaseFactoryBean) throws Exception {
		// jdbc:mysql://192.168.0.1:3306/user_info?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&autoReconnect=true
		String[] urlParamArr = dbUrl.split("\\?");
		String[] urlDbNameArr = urlParamArr[0].split("/");
		if (urlDbNameArr.length >= 2) {
			databaseFactoryBean.setDbName(urlDbNameArr[urlDbNameArr.length - 1]);
			//databaseFactoryBean.setHost(urlDbNameArr[urlDbNameArr.length - 2]);
		}
		databaseFactoryBean.setDatabaseProduct(DatabaseProductEnum.MYSQL);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		return resolver.getResources("classpath:com/zyplayer/doc/db/framework/db/mapper/mysql/*.xml");
	}
	
}
