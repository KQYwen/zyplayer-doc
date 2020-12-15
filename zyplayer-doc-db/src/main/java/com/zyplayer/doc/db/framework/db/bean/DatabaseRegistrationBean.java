package com.zyplayer.doc.db.framework.db.bean;

import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.data.repository.manage.entity.DbDatasource;
import com.zyplayer.doc.data.service.manage.DbDatasourceService;
import com.zyplayer.doc.db.framework.configuration.DatasourceUtil;
import com.zyplayer.doc.db.framework.db.mapper.base.BaseMapper;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 所有的数据源管理类
 *
 * @author 暮光：城中城
 * @since 2018年8月8日
 */
@Repository
public class DatabaseRegistrationBean {
	
	@Resource
	DbDatasourceService dbDatasourceService;
	
	// 描述连接信息的对象列表
	private Map<Long, DatabaseFactoryBean> databaseFactoryBeanMap = new ConcurrentHashMap<>();
	
	/**
	 * 获取BaseMapper
	 *
	 * @param sourceId 数据源ID
	 * @param cls      指定类
	 * @return BaseMapper
	 */
	public <T> T getBaseMapper(Long sourceId, Class<T> cls) {
		DatabaseFactoryBean factoryBean = getOrCreateFactoryById(sourceId);
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
	
	/**
	 * 获取BaseMapper
	 *
	 * @param sourceId 数据源ID
	 * @return BaseMapper
	 */
	public BaseMapper getBaseMapperById(Long sourceId) {
		DatabaseFactoryBean databaseFactoryBean = this.getOrCreateFactoryById(sourceId);
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
	
	/**
	 * 关闭数据源
	 *
	 * @param sourceId 数据源ID
	 */
	public void closeDatasource(Long sourceId) {
		DatabaseFactoryBean factoryBean = databaseFactoryBeanMap.remove(sourceId);
		if (factoryBean != null) {
			try {
				// 关闭数据源
				factoryBean.getDataSource().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 通过数据源ID获取或创建新的数据源
	 *
	 * @param sourceId 数据源ID
	 * @return 数据源对象
	 */
	public DatabaseFactoryBean getOrCreateFactoryById(Long sourceId) {
		DatabaseFactoryBean factoryBean = databaseFactoryBeanMap.get(sourceId);
		if (factoryBean != null) return factoryBean;
		return this.createFactoryById(sourceId);
	}
	
	/**
	 * 创建数据源的同步方法
	 *
	 * @param sourceId 数据源ID
	 * @return 新数据源对象
	 */
	private synchronized DatabaseFactoryBean createFactoryById(Long sourceId) {
		DatabaseFactoryBean factoryBean = databaseFactoryBeanMap.get(sourceId);
		if (factoryBean != null) {
			return factoryBean;
		}
		DbDatasource dbDatasource = dbDatasourceService.getById(sourceId);
		if (dbDatasource == null) {
			throw new ConfirmException("未找到指定数据源配置信息");
		}
		try {
			DatabaseFactoryBean databaseFactoryBean = DatasourceUtil.createDatabaseFactoryBean(dbDatasource, false);
			if (databaseFactoryBean == null) {
				throw new ConfirmException("获取数据源失败");
			}
			databaseFactoryBeanMap.put(sourceId, databaseFactoryBean);
			return databaseFactoryBean;
		} catch (Exception e) {
			throw new ConfirmException("创建数据源失败", e);
		}
	}
}
