package com.zyplayer.doc.db.service.database;

import com.zyplayer.doc.db.controller.param.DataViewParam;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * Postgresql数据查询服务实现类
 *
 * @author 暮光：城中城
 * @since 2021-02-01
 */
@Service
public class PostgresqlServiceImpl extends DbBaseService {
	
	@Override
	public DatabaseProductEnum getDatabaseProduct() {
		return DatabaseProductEnum.POSTGRESQL;
	}

	/**
	 * Postgresql中没有use语句,指定数据库名的情况下直接返回空即可
	 * @author diantu
	 * @since 2023-06-20
	 */
	@Override
	public String getUseDbSql(String dbName) {
		return null;
	}

	/**
	 * 获取分页查询的SQL
	 *
	 * @return 分页查询的SQL
	 * @author diantu
	 * @since 2023年6月20日
	 */
	@Override
	public String getQueryPageSql(DataViewParam dataViewParam) {
		String queryColumns = StringUtils.defaultIfBlank(dataViewParam.getRetainColumn(), "*");
		StringBuilder sqlSb = new StringBuilder();
		//postgresql记录的ctid会随着update变化，与oracle中的伪列有本质区别（oracle，达梦里的rowid可以理解成持久化数据）
		//TODO 在高并发下依赖ctid修改数据可能会有业务逻辑问题
		sqlSb.append(String.format("select cast(ctid as varchar) as ZYPLAYDBCTID,%s from %s.%s", queryColumns, dataViewParam.getDbName(), dataViewParam.getTableName()));
		if (StringUtils.isNotBlank(dataViewParam.getCondition())) {
			sqlSb.append(String.format(" where %s", dataViewParam.getCondition()));
		}
		if (StringUtils.isNotBlank(dataViewParam.getOrderColumn()) && StringUtils.isNotBlank(dataViewParam.getOrderType())) {
			sqlSb.append(String.format(" order by %s %s", dataViewParam.getOrderColumn(), dataViewParam.getOrderType()));
		}
		sqlSb.append(String.format(" limit %s offset %s", dataViewParam.getPageSize(), dataViewParam.getOffset()));
		return sqlSb.toString();
	}
}
