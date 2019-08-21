package com.zyplayer.doc.db.framework.db.bean;

import com.zyplayer.doc.db.framework.db.mapper.base.BaseMapper;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 需要声明注入的对象，只需要设置dbConfigList即可
 * databaseFactoryBeanList是后面生成的
 *
 * @author 暮光：城中城
 * @since 2018年8月8日
 */
public class DatabaseRegistrationBean {
	
	// 注入此对象必须配置的参数
	// 配置的数据源连接、账号密码等信息，通过注入得到
	private List<DbConfigBean> dbConfigList = new LinkedList<>();
	// 描述连接信息的对象列表
	private List<DatabaseFactoryBean> databaseFactoryBeanList = new LinkedList<>();
	
	public List<DatabaseFactoryBean> getDatabaseFactoryBeanList() {
		return databaseFactoryBeanList;
	}
	
	public void setDatabaseFactoryBeanList(List<DatabaseFactoryBean> databaseFactoryBeanList) {
		this.databaseFactoryBeanList = databaseFactoryBeanList;
	}
	
	public BaseMapper getBaseMapper(Long sourceId) {
		return getBaseMapper(sourceId, BaseMapper.class);
	}
	
	public <T> T getBaseMapper(Long sourceId, Class<T> cls) {
		DatabaseFactoryBean factoryBean = getFactoryById(sourceId);
		if (factoryBean != null) {
			SqlSessionTemplate sessionTemplate = factoryBean.getSqlSessionTemplate();
			try {
				return sessionTemplate.getMapper(cls);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public DatabaseFactoryBean getFactoryById(Long sourceId) {
		for (DatabaseFactoryBean databaseFactoryBean : databaseFactoryBeanList) {
			if (Objects.equals(databaseFactoryBean.getId(), sourceId)) {
				return databaseFactoryBean;
			}
		}
		return null;
	}
	
	public BaseMapper getBaseMapperById(Long sourceId) {
		DatabaseFactoryBean databaseFactoryBean = this.getFactoryById(sourceId);
		if (databaseFactoryBean == null) {
			return null;
		}
		try {
			SqlSessionTemplate sessionTemplate = databaseFactoryBean.getSqlSessionTemplate();
			return sessionTemplate.getMapper(BaseMapper.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<DbConfigBean> getDbConfigList() {
		return dbConfigList;
	}
	
	public void setDbConfigList(List<DbConfigBean> dbConfigList) {
		this.dbConfigList = dbConfigList;
	}
}
