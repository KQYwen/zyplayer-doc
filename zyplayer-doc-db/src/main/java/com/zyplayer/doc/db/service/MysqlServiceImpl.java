package com.zyplayer.doc.db.service;

import cn.hutool.core.util.RandomUtil;
import com.zyplayer.doc.db.controller.param.DataViewParam;
import com.zyplayer.doc.db.controller.vo.TableDdlVo;
import com.zyplayer.doc.db.framework.db.dto.ColumnInfoDto;
import com.zyplayer.doc.db.framework.db.dto.ProcedureDto;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import com.zyplayer.doc.db.framework.db.mapper.base.BaseMapper;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteParam;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteResult;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteType;
import com.zyplayer.doc.db.framework.db.mapper.mysql.MysqlMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class MysqlServiceImpl extends DbBaseService {
	
	@Override
	public DatabaseProductEnum getDatabaseProduct() {
		return DatabaseProductEnum.MYSQL;
	}
	
	@Override
	public TableDdlVo getTableDdl(Long sourceId, String dbName, String tableName) {
		BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
		List<Map<String, String>> tableDdlList = baseMapper.getTableDdl(dbName, tableName);
		TableDdlVo tableDdlVo = new TableDdlVo();
		tableDdlVo.setCurrent(DatabaseProductEnum.MYSQL.name().toLowerCase());
		tableDdlVo.setMysql("// 生成失败");
		tableDdlVo.setOracle("// TODO 等待大佬来实现转换");
		// TODO 将建表语句转换为其他数据库的，还不知道怎么做，先这样留着，看有没大佬来实现
		if (CollectionUtils.isNotEmpty(tableDdlList)) {
			tableDdlVo.setMysql(tableDdlList.get(0).get("Create Table"));
		}
		return tableDdlVo;
	}
	
	@Override
	public void updateTableColumnDesc(Long sourceId, String dbName, String tableName, String columnName, String newDesc) {
		BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
		// mysql要同时修改类型默认值等，所以先查出来
		MysqlMapper mysqlMapper = databaseRegistrationBean.getBaseMapper(sourceId, MysqlMapper.class);
		ColumnInfoDto columnInfo = mysqlMapper.getColumnInfo(dbName, tableName, columnName);
		String isNullable = Optional.ofNullable(columnInfo.getIsNullable()).orElse("");
		columnInfo.setIsNullable("yes".equalsIgnoreCase(isNullable) ? "null" : "not null");
		String columnDefault = columnInfo.getColumnDefault();
		if (StringUtils.isNotBlank(columnDefault)) {
			columnInfo.setColumnDefault("DEFAULT " + columnDefault);
		} else {
			columnInfo.setColumnDefault("");
		}
		String extra = columnInfo.getExtra();
		columnInfo.setExtra(StringUtils.isBlank(extra) ? "" : extra);
		baseMapper.updateTableColumnDesc(dbName, tableName, columnName, newDesc, columnInfo);
	}
	
	@Override
	public ProcedureDto getProcedureDetail(Long sourceId, String dbName, String typeName, String procName) {
		BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
		ProcedureDto procedureDetail = baseMapper.getProcedureDetail(dbName, typeName, procName);
		if (procedureDetail == null) {
			// 新建的时候
			ProcedureDto procedureDetailNew = new ProcedureDto();
			if (Objects.equals(typeName, "FUNCTION")) {
				procedureDetailNew.setBody("CREATE DEFINER = CURRENT_USER " + typeName + " `" + dbName + "`.`" + procName + "`() RETURNS integer\n" +
						"BEGIN\n" +
						"\t#Routine body goes here...\n" +
						"\tRETURN 0;\n" +
						"END;");
			} else {
				procedureDetailNew.setBody("CREATE DEFINER = CURRENT_USER " + typeName + " `" + dbName + "`.`" + procName + "`()\n" +
						"BEGIN\n" +
						"\t#Routine body goes here...\n" +
						"END;");
			}
			procedureDetailNew.setDb(dbName);
			procedureDetailNew.setDefiner("CURRENT_USER");
			procedureDetailNew.setType(typeName);
			return procedureDetailNew;
		}
		// 组装好SQL
		String type = procedureDetail.getType();
		String name = procedureDetail.getName();
		String db = procedureDetail.getDb();
		String paramList = StringUtils.defaultIfBlank(procedureDetail.getParamList(), "");
		String[] definerArr = procedureDetail.getDefiner().split("@");
		String createStr = String.format("CREATE DEFINER=`%s`@`%s` %s `%s`.`%s`(%s)", definerArr[0], definerArr[1], type, db, name, paramList);
		if (Objects.equals(procedureDetail.getType(), "FUNCTION")) {
			createStr += " RETURNS " + procedureDetail.getReturns();
		}
		procedureDetail.setBody(createStr + "\r\n" + procedureDetail.getBody());
		return procedureDetail;
	}
	
	@Override
	public ExecuteResult saveProcedure(Long sourceId, String dbName, String typeName, String procName, String procSql) {
		String firstLine = procSql.split("\n")[0];
		// 看函数名是否被修改了，修改会导致函数名的不确定，有认知上的成本，明确的先删再建吧
		if (!firstLine.contains("`" + procName + "`(") && !firstLine.contains(" " + procName + "(") && !firstLine.contains("." + procName + "(")) {
			return ExecuteResult.error("在编辑页面不允许修改函数名，如需新建或修改，请到列表页删除后再新建函数", procSql);
		}
		ProcedureDto procedureDetail = this.getProcedureDetail(sourceId, dbName, typeName, procName);
		// 按MySQL的来是先删除再创建，如果其他数据库不是这个逻辑，需要重写本方法实现自己的逻辑
		BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
		baseMapper.deleteProcedure(dbName, typeName, procName);
		// 执行创建SQL
		ExecuteParam executeParam = new ExecuteParam();
		executeParam.setDatasourceId(sourceId);
		executeParam.setExecuteId(RandomUtil.randomUUID());
		executeParam.setExecuteType(ExecuteType.ALL);
		executeParam.setSql(procSql);
		executeParam.setMaxRows(1000);
		try {
			return sqlExecutor.execute(executeParam);
		} catch (Exception e) {
			try {
				// 尝试恢复函数
				executeParam.setSql(procedureDetail.getBody());
				sqlExecutor.execute(executeParam);
			} catch (Exception e1) {
				return ExecuteResult.error("执行和恢复函数均失败，请先备份您的SQL，以防丢失\n" + e.getMessage(), procSql);
			}
			return ExecuteResult.error(e.getMessage(), procSql);
		}
	}
}
