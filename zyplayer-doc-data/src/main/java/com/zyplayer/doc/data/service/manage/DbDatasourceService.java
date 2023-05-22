package com.zyplayer.doc.data.service.manage;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zyplayer.doc.data.repository.manage.entity.DbDatasource;

import java.util.List;

/**
 * 数据源业务接口
 *
 * @author 暮光：城中城
 * @since 2019-07-04
 */
public interface DbDatasourceService extends IService<DbDatasource> {

	/**
	 * 获取数据源列表(管理员返回所有数据源,用户返回有权限的数据源)
	 * @return List<DbDatasource>
	 */
	List<DbDatasource> getDataSourceList();

	/**
	 * 获取数据源
	 */
	DbDatasource getDataSource(Long sourceId);
}
