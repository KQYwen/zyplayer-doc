package com.zyplayer.doc.db.framework.configuration;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.data.repository.manage.entity.DbDatasource;
import com.zyplayer.doc.data.service.manage.DbDatasourceService;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.bean.DatabaseRegistrationBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ApplicationListenerBean implements ApplicationListener<ContextRefreshedEvent> {
	
	@javax.annotation.Resource
	DatabaseRegistrationBean databaseRegistrationBean;
	@javax.annotation.Resource
	DbDatasourceService dbDatasourceService;
	
	private volatile static boolean IS_INIT = false;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (databaseRegistrationBean == null || IS_INIT) {
			return;
		}
		// 会被调用两次
		IS_INIT = true;
		List<DatabaseFactoryBean> databaseFactoryBeanList = new LinkedList<>();
		
		QueryWrapper<DbDatasource> wrapper = new QueryWrapper<>();
		wrapper.eq("yn", 1);
		List<DbDatasource> datasourceList = dbDatasourceService.list(wrapper);
		for (DbDatasource dbDatasource : datasourceList) {
			DatabaseFactoryBean databaseFactoryBean = DatasourceUtil.createDatabaseFactoryBean(dbDatasource);
			if (databaseFactoryBean != null) {
				databaseFactoryBeanList.add(databaseFactoryBean);
			}
		}
		databaseRegistrationBean.setDatabaseFactoryBeanList(databaseFactoryBeanList);
	}
	
}
