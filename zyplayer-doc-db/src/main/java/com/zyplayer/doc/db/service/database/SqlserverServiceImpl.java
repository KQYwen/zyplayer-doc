package com.zyplayer.doc.db.service.database;

import com.zyplayer.doc.db.controller.param.DataViewParam;
import com.zyplayer.doc.db.controller.vo.TableColumnVo;
import com.zyplayer.doc.db.controller.vo.TableDdlVo;
import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import com.zyplayer.doc.db.framework.db.mapper.base.BaseMapper;
import com.zyplayer.doc.db.framework.db.mapper.sqlserver.SqlServerMapper;
import com.zyplayer.doc.db.framework.utils.SQLTransformUtils;
import com.zyplayer.doc.db.service.download.SqlserverDownloadService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Sqlserver数据查询服务实现类
 *
 * @author 暮光：城中城
 * @since 2021-02-01
 */
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

//	/**
//	 * 获取分页查询的SQL(fetch next只适用SQL Server 2012及以上版本)
//	 *
//	 * @return 分页查询的SQL
//	 * @author 暮光：城中城
//	 * @since 2021年6月13日
//	 */
//	@Override
//	public String getQueryPageSql(DataViewParam dataViewParam) {
//		String queryColumns = StringUtils.defaultIfBlank(dataViewParam.getRetainColumn(), "*");
//		StringBuilder sqlSb = new StringBuilder();
//		sqlSb.append(String.format("select %s from %s..%s", queryColumns, dataViewParam.getDbName(), dataViewParam.getTableName()));
//		if (StringUtils.isNotBlank(dataViewParam.getCondition())) {
//			sqlSb.append(String.format(" where %s", dataViewParam.getCondition()));
//		}
//		if (StringUtils.isNotBlank(dataViewParam.getOrderColumn()) && StringUtils.isNotBlank(dataViewParam.getOrderType())) {
//			sqlSb.append(String.format(" order by %s %s", dataViewParam.getOrderColumn(), dataViewParam.getOrderType()));
//		}
//		sqlSb.append(String.format(" offset %s row fetch next %s rows only", dataViewParam.getOffset(), dataViewParam.getPageSize()));
//		return sqlSb.toString();
//	}

	/**
	 * 获取分页查询的SQL(兼容写法,支持SQL Server 2005及以上版本)
	 *
	 * @return 分页查询的SQL
	 * @author diantu
	 * @since 2023年1月18日
	 */
	@Override
	public String getQueryPageSql(DataViewParam dataViewParam) {
		String queryColumns = StringUtils.defaultIfBlank(dataViewParam.getRetainColumn(), "*");
		if(!Objects.equals(queryColumns, "*")){
			//给字段加上双引号，解决关键字问题
			queryColumns = Arrays.stream(queryColumns.split(",")).map(word -> "\"" + word + "\"").collect(Collectors.joining(","));
		}
		Integer pageNum = dataViewParam.getPageNum();
		Integer pageSize = dataViewParam.getPageSize();
		Integer rownumber = (pageNum-1)*pageSize;
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append(String.format("select top %s %s from (select row_number()  OVER(order by %s %s) as rowid, %s from %s..%s )A where rowid> %s",pageSize,queryColumns,dataViewParam.getOrderColumn(), dataViewParam.getOrderType(), queryColumns, dataViewParam.getDbName(), dataViewParam.getTableName(),rownumber));
		if (StringUtils.isNotBlank(dataViewParam.getCondition())) {
			sqlSb.append(String.format(" and %s", dataViewParam.getCondition()));
		}
		return sqlSb.toString();
	}

	/**
	 * 获取分页查询的SQL(兼容写法,支持SQL Server 2005及以上版本)
	 *
	 * @return 分页查询的SQL
	 * @author diantu
	 * @since 2023年2月22日
	 */
	@Override
	public String getQueryPageSqlBySql(String sql,Integer pageSize,Integer pageNum) {
		StringBuilder sqlSb = new StringBuilder();
		Integer rownumber = (pageNum-1)*pageSize;
		Integer top = pageSize*pageNum;
		sqlSb.append(String.format("select * from ( select row_number()over(order by tempColumn)rownumber,* from (select top %s tempColumn=0,* from (%s) r where 1=1 )a)b where rownumber > %s",top,sql,rownumber));
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

	/**
	 * 获取建表语句
	 *
	 * @author diantu
	 * @since 2023年2月2日
	 */
	@Override
	public TableDdlVo getTableDdl(Long sourceId, String dbName, String tableName) {
		BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
		List<Map<String, Object>> tableDdlList = baseMapper.getTableDdl(dbName, tableName);
		TableDdlVo tableDdlVo = new TableDdlVo();
		tableDdlVo.setCurrent(DatabaseProductEnum.SQLSERVER.name().toLowerCase());
		if (CollectionUtils.isNotEmpty(tableDdlList)) {
			String sqlserverSql = "";
			try {
				sqlserverSql = SQLTransformUtils.ClobToString((Clob)tableDdlList.get(0).get(""));
			} catch (SQLException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			//String sqlserverSql = (String) tableDdlList.get(0).get("");
			tableDdlVo.setSqlserver(sqlserverSql);
//			tableDdlVo.setOracle(oracleSql);
			//oracle建表语句转换为mysql建表语句
			String mysqlSql = SQLTransformUtils.translateSqlServerToMySql(sqlserverSql);
			tableDdlVo.setMysql(mysqlSql);
			// TODO 其他数据库同理
		}
		return tableDdlVo;
	}
}
