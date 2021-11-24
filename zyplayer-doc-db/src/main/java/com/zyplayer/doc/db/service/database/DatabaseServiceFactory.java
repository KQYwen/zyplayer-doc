package com.zyplayer.doc.db.service.database;

import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.bean.DatabaseRegistrationBean;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import com.zyplayer.doc.db.service.download.BaseDownloadService;
import com.zyplayer.doc.db.service.download.DownloadService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库服务工厂类
 *
 * @author 暮光：城中城
 * @since 2021-02-01
 */
@Service
public class DatabaseServiceFactory {
	
	@Resource
	DatabaseRegistrationBean databaseRegistrationBean;
	@Resource
	private List<DbBaseService> dbBaseServiceList;
	private final Map<DatabaseProductEnum, DbBaseService> dbBaseServiceMap = new HashMap<>();
	// 下载服务
	@Resource
	private BaseDownloadService baseDownloadService;
	@Resource
	private List<DownloadService> downloadServiceList;
	private final Map<DatabaseProductEnum, DownloadService> downloadServiceMap = new HashMap<>();
	
	@PostConstruct
	private void init() {
		dbBaseServiceList.forEach(item -> dbBaseServiceMap.put(item.getDatabaseProduct(), item));
		downloadServiceList.forEach(item -> downloadServiceMap.put(item.getDatabaseProductEnum(), item));
	}
	
	public DbBaseService getDbBaseService(Long sourceId) {
		DatabaseFactoryBean databaseFactoryBean = databaseRegistrationBean.getOrCreateFactoryById(sourceId);
		if (databaseFactoryBean == null) {
			throw new ConfirmException("未找到对应的数据库连接");
		}
		return dbBaseServiceMap.get(databaseFactoryBean.getDatabaseProduct());
	}
	
	/**
	 * 获取下载服务
	 *
	 * @param databaseProductEnum 数据库类型
	 * @return 下载服务
	 */
	public DownloadService getDownloadService(DatabaseProductEnum databaseProductEnum) {
		return downloadServiceMap.getOrDefault(databaseProductEnum, baseDownloadService);
	}
	
}
