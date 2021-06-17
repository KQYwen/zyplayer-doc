package com.zyplayer.doc.db.service;

import com.zyplayer.doc.db.controller.param.DataViewParam;
import com.zyplayer.doc.db.controller.vo.TableColumnVo;
import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteParam;
import com.zyplayer.doc.db.framework.db.mapper.sqlserver.SqlServerMapper;
import com.zyplayer.doc.db.service.download.BaseDownloadService;
import com.zyplayer.doc.db.service.download.SqlserverDownloadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SqlserverServiceImpl extends DbBaseService {
	
	@Resource
	SqlserverDownloadService sqlserverDownloadService;
	
	@Override
	public DatabaseProductEnum getDatabaseProduct() {
		return DatabaseProductEnum.SQLSERVER;
	}
	
	@Override
	public TableColumnVo getTableColumnList(Long sourceId, String dbName, String tableName) {
		TableColumnVo tableColumnVo = super.getTableColumnList(sourceId, dbName, tableName);
		// SQLSERVER 要单独查字段注释
		SqlServerMapper sqlServerMapper = databaseRegistrationBean.getBaseMapper(sourceId, SqlServerMapper.class);
		List<TableColumnDescDto> columnDescList = sqlServerMapper.getTableColumnDescList(tableName);
		Map<String, TableColumnDescDto> columnMap = tableColumnVo.getColumnList().stream().collect(Collectors.toMap(TableColumnDescDto::getName, val -> val));
		// 字段注释
		for (TableColumnDescDto descDto : columnDescList) {
			TableColumnDescDto tempDesc = columnMap.get(descDto.getName());
			if (tempDesc != null) {
				tempDesc.setDescription(descDto.getDescription());
			}
		}
		return tableColumnVo;
	}
	
	/**
	 * 获取分页查询的SQL
	 *
	 * @return 分页查询的SQL
	 * @author 暮光：城中城
	 * @since 2021年6月13日
	 */
	@Override
	public String getQueryPageSql(DataViewParam dataViewParam) {
		String queryColumns = StringUtils.defaultIfBlank(dataViewParam.getRetainColumn(), "*");
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append(String.format("select %s from %s..%s", queryColumns, dataViewParam.getDbName(), dataViewParam.getTableName()));
		if (StringUtils.isNotBlank(dataViewParam.getCondition())) {
			sqlSb.append(String.format(" where %s", dataViewParam.getCondition()));
		}
		if (StringUtils.isNotBlank(dataViewParam.getOrderColumn()) && StringUtils.isNotBlank(dataViewParam.getOrderType())) {
			sqlSb.append(String.format(" order by %s %s", dataViewParam.getOrderColumn(), dataViewParam.getOrderType()));
		}
		sqlSb.append(String.format(" offset %s row fetch next %s rows only", dataViewParam.getOffset(), dataViewParam.getPageSize()));
		return sqlSb.toString();
	}
	
	/**
	 * 获取查询总条数的SQL
	 *
	 * @return 查询总条数的SQL
	 * @author 暮光：城中城
	 * @since 2020年4月24日
	 */
	public String getQueryCountSql(DataViewParam dataViewParam) {
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append(String.format("select count(1) as counts from %s..%s", dataViewParam.getDbName(), dataViewParam.getTableName()));
		if (StringUtils.isNotBlank(dataViewParam.getCondition())) {
			sqlSb.append(String.format(" where %s", dataViewParam.getCondition()));
		}
		return sqlSb.toString();
	}
	
	/**
	 * 获取全量数据查询的SQL
	 *
	 * @return 分页查询的SQL
	 * @author 暮光：城中城
	 * @since 2020年4月24日
	 */
	public String getQueryAllSql(DataViewParam dataViewParam) {
		String queryColumns = StringUtils.defaultIfBlank(dataViewParam.getRetainColumn(), "*");
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append(String.format("select %s from %s..%s", queryColumns, dataViewParam.getDbName(), dataViewParam.getTableName()));
		if (StringUtils.isNotBlank(dataViewParam.getCondition())) {
			sqlSb.append(String.format(" where %s", dataViewParam.getCondition()));
		}
		return sqlSb.toString();
	}
}
