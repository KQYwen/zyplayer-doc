package com.zyplayer.doc.db.framework.db.bean;

import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;

import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean.DatabaseProduct;
import com.zyplayer.doc.db.framework.db.mapper.base.BaseMapper;

/**
 * 需要声明注入的对象，只需要设置dataSourceList即可
 * databaseFactoryBeanList是后面生成的
 * 
 * @author 暮光：城中城
 * @since 2018年8月8日
 */
public class DatabaseRegistrationBean {

	private List<DataSource> dataSourceList = new LinkedList<>();

	private List<DatabaseFactoryBean> databaseFactoryBeanList = new LinkedList<>();

	public List<DataSource> getDataSourceList() {
		return dataSourceList;
	}

	public void setDataSourceList(List<DataSource> dataSourceList) {
		this.dataSourceList = dataSourceList;
	}

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

}