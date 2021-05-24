package com.zyplayer.doc.db.service;

import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.bean.DatabaseRegistrationBean;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DbBaseFactory {
	
	@Resource
	DatabaseRegistrationBean databaseRegistrationBean;
	@Resource
	private List<DbBaseService> dbBaseServiceList;
	private final Map<DatabaseProductEnum, DbBaseService> dbBaseServiceMap = new HashMap<>();
	
	@PostConstruct
	private void init() {
		dbBaseServiceList.forEach(item -> dbBaseServiceMap.put(item.getDatabaseProduct(), item));
	}
	
	public DbBaseService getDbBaseService(Long sourceId) {
		DatabaseFactoryBean databaseFactoryBean = databaseRegistrationBean.getOrCreateFactoryById(sourceId);
		if (databaseFactoryBean == null) {
			throw new ConfirmException("未找到对应的数据库连接");
		}
		return dbBaseServiceMap.get(databaseFactoryBean.getDatabaseProduct());
	}
	
}
