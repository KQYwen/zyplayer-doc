package com.zyplayer.doc.db.framework.configuration.analysis;

import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import org.springframework.core.io.Resource;

public interface AnalysisApi {
	
	Resource[] process(String dbUrl, DatabaseFactoryBean databaseFactoryBean) throws Exception;
	
}
