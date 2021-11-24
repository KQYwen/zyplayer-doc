package com.zyplayer.doc.db.service.download;

import com.alibaba.fastjson.JSONObject;
import com.zyplayer.doc.db.controller.param.DataViewParam;
import com.zyplayer.doc.db.controller.vo.TableDdlVo;
import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteParam;
import com.zyplayer.doc.db.framework.db.mapper.base.SqlExecutor;
import com.zyplayer.doc.db.service.database.DatabaseServiceFactory;
import com.zyplayer.doc.db.service.database.DbBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * SQLServer的数据导出服务类
 *
 * @author 暮光：城中城
 * @since 2021-06-17
 */
@Service
public class SqlserverDownloadService implements DownloadService {
	private static Logger logger = LoggerFactory.getLogger(SqlserverDownloadService.class);
	
	@Resource
	SqlExecutor sqlExecutor;
	@Resource
	DatabaseServiceFactory databaseServiceFactory;
	
	@Override
	public DatabaseProductEnum getDatabaseProductEnum() {
		return DatabaseProductEnum.SQLSERVER;
	}
	
	/**
	 * 导出数据为insert语句格式
	 *
	 * @author 暮光：城中城
	 * @since 2020年6月5日
	 */
	@Override
	public String downloadDataByInsert(DataViewParam param, ExecuteParam executeParam, List<TableColumnDescDto> dataCols, Set<String> conditionSet) throws Exception {
		String dbTableName = String.format("%s..%s", param.getDbName(), param.getTableName());
		StringBuilder resultSb = new StringBuilder();
		if (Objects.equals(param.getCreateTableFlag(), 1)) {
			resultSb.append("-- 导出表 ").append(dbTableName).append(" 结构\n");
			if (Objects.equals(param.getDropTableFlag(), 1)) {
				resultSb.append("DROP TABLE IF EXISTS ").append(dbTableName).append(";\n");
			}
			DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(param.getSourceId());
			try {
				TableDdlVo tableDdlVo = dbBaseService.getTableDdl(param.getSourceId(), param.getDbName(), param.getTableName());
				resultSb.append(tableDdlVo.getTableDDLByType()).append("\n\n");
			} catch (Exception e) {
				logger.error("导出表结构失败：{}", e.getMessage());
				resultSb.append("-- ！！导出表 ").append(dbTableName).append(" 结构失败！！\n");
			}
		}
		resultSb.append("-- 导出表 ").append(dbTableName).append(" 数据\n");
		Pattern pattern = Pattern.compile("\t|\r\n|\r|\n|\\s+");
		String executeSql = pattern.matcher(executeParam.getSql()).replaceAll(" ");
		resultSb.append("-- 导出查询SQL：").append(executeSql).append(";\n");
		// 执行数据查询操作
		sqlExecutor.execute(executeParam, item -> {
			StringBuilder names = new StringBuilder();
			for (TableColumnDescDto dataCol : dataCols) {
				if (names.length() > 0) names.append(", ");
				names.append(dataCol.getName());
			}
			StringBuilder values = new StringBuilder();
			for (TableColumnDescDto dataCol : dataCols) {
				if (values.length() > 0) values.append(", ");
				Object val = item.get(dataCol.getName());
				if (this.isNumber(dataCol.getType())) {
					values.append(val);
				} else {
					val = (val == null) ? "" : val;
					val = val.toString().replaceAll("'", "''");
					values.append("'").append(val).append("'");
				}
			}
			String resultData = "insert into " + dbTableName + " (" + names + ") values (" + values + ");\n";
			resultSb.append(resultData);
		});
		return resultSb.toString();
	}
	
	/**
	 * 导出数据为update语句格式
	 *
	 * @author 暮光：城中城
	 * @since 2020年6月5日
	 */
	@Override
	public String downloadDataByUpdate(DataViewParam param, ExecuteParam executeParam, List<TableColumnDescDto> dataCols, Set<String> conditionSet) throws Exception {
		String dbTableName = String.format("%s..%s", param.getDbName(), param.getTableName());
		StringBuilder resultSb = new StringBuilder();
		Pattern pattern = Pattern.compile("\t|\r\n|\r|\n|\\s+");
		String executeSql = pattern.matcher(executeParam.getSql()).replaceAll(" ");
		resultSb.append("-- 导出查询SQL：").append(executeSql).append(";\n");
		// 执行数据查询操作
		sqlExecutor.execute(executeParam, item -> {
			StringBuilder values = new StringBuilder();
			StringBuilder where = new StringBuilder();
			for (TableColumnDescDto dataCol : dataCols) {
				Object val = item.get(dataCol.getName());
				if (conditionSet.contains(dataCol.getName())) {
					if (this.isNumber(dataCol.getType())) {
						if (where.length() > 0) where.append(" and ");
						where.append(dataCol.getName()).append(" = ").append(val);
					} else {
						if (where.length() > 0) where.append(" and ");
						where.append(dataCol.getName()).append(" = ").append("'").append(val).append("'");
					}
				} else {
					if (values.length() > 0) values.append(", ");
					values.append(dataCol.getName()).append("=");
					if (this.isNumber(dataCol.getType())) {
						values.append(val);
					} else {
						val = (val == null) ? "" : val;
						val = val.toString().replaceAll("'", "''");
						values.append("'").append(val).append("'");
					}
				}
			}
			if (where.length() > 0) where.insert(0, " where ");
			String resultData = "update " + dbTableName + " set " + values + where + ";\n";
			resultSb.append(resultData);
		});
		return resultSb.toString();
	}
	
	/**
	 * 导出数据为json格式
	 *
	 * @author 暮光：城中城
	 * @since 2020年6月5日
	 */
	@Override
	public String downloadDataByJson(DataViewParam param, ExecuteParam executeParam, List<TableColumnDescDto> dataCols, Set<String> conditionSet) throws Exception {
		StringBuilder resultSb = new StringBuilder();
		resultSb.append("[");
		sqlExecutor.execute(executeParam, item -> {
			JSONObject result = new JSONObject();
			for (TableColumnDescDto dataCol : dataCols) {
				result.put(dataCol.getName(), item.get(dataCol.getName()));
			}
			String resultData = result.toJSONString();
			if (resultSb.length() > 1) {
				resultSb.append(",");
			}
			resultSb.append(resultData);
		});
		resultSb.append("]\n");
		return resultSb.toString();
	}
	
	/**
	 * 是否是数值类型
	 *
	 * @param type 类型
	 * @return 结果
	 */
	@Override
	public boolean isNumber(String type) {
		return type.contains("int")
				|| type.contains("bit")
				|| type.contains("float")
				|| type.contains("double")
				|| type.contains("decimal")
				|| type.contains("real")
				;
	}
}
