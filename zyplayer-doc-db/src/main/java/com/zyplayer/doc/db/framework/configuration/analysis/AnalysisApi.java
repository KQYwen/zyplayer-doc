package com.zyplayer.doc.db.framework.configuration.analysis;

import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import org.springframework.core.io.Resource;

/**
 * 数据库链接url解析api
 *
 * @author 暮光：城中城
 * @since 2021-05-13
 */
public interface AnalysisApi {
	
	Resource[] process(String dbUrl, DatabaseFactoryBean databaseFactoryBean) throws Exception;
	
}
