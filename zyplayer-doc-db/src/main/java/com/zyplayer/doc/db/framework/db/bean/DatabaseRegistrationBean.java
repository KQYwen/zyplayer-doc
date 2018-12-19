package com.zyplayer.doc.db.framework.db.bean;

import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean.DatabaseProduct;
import com.zyplayer.doc.db.framework.db.mapper.base.BaseMapper;
import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.LinkedList;
import java.util.List;

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
	
	public DatabaseFactoryBean getDatabaseFactoryBean(String host, String dbName) {
		if (StringUtils.isBlank(dbName)) {
			return null;
		}
		DatabaseFactoryBean resultBean = null;
		for (DatabaseFactoryBean databaseFactoryBean : databaseFactoryBeanList) {
			if (host.equalsIgnoreCase(databaseFactoryBean.getHost())) {
				if (dbName.equalsIgnoreCase(databaseFactoryBean.getDbName())) {
					return databaseFactoryBean;
				}
				if (databaseFactoryBean.getDatabaseProduct() == DatabaseProduct.MYSQL) {
					resultBean = databaseFactoryBean;
				}
			}
		}
		return resultBean;
	}
	
	public BaseMapper getBaseMapper(String host, String dbName) {
		return getBaseMapper(host, dbName, BaseMapper.class);
	}

	public <T> T getBaseMapper(String host, String dbName, Class<T> cls) {
		DatabaseFactoryBean factoryBean = getDatabaseFactoryBean(host, dbName);
		if (factoryBean != null) {
			SqlSessionTemplate sessionTemplate = factoryBean.getSqlSessionTemplate();
			try {
				return sessionTemplate.getMapper(cls);
			} catch (Exception e) {}
		}
		return null;
	}

	public BaseMapper getBaseMapperByHost(String host) {
		if (StringUtils.isBlank(host)) {
			return null;
		}
		for (DatabaseFactoryBean databaseFactoryBean : databaseFactoryBeanList) {
			if (host.equalsIgnoreCase(databaseFactoryBean.getHost())) {
				try {
					SqlSessionTemplate sessionTemplate = databaseFactoryBean.getSqlSessionTemplate();
					return sessionTemplate.getMapper(BaseMapper.class);
				} catch (Exception e) {}
			}
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