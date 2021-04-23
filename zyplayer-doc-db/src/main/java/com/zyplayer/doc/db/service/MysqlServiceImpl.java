package com.zyplayer.doc.db.service;

import com.zyplayer.doc.db.controller.vo.TableDdlVo;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.dto.ColumnInfoDto;
import com.zyplayer.doc.db.framework.db.mapper.base.BaseMapper;
import com.zyplayer.doc.db.framework.db.mapper.mysql.MysqlMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class MysqlServiceImpl extends DbBaseService {
	
	@Override
	public DatabaseFactoryBean.DatabaseProduct getDatabaseProduct() {
		return DatabaseFactoryBean.DatabaseProduct.MYSQL;
	}
	
	@Override
	public TableDdlVo getTableDdl(Long sourceId, String dbName, String tableName) {
		BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
		Map<String, String> tableDdl = baseMapper.getTableDdl(dbName, tableName);
		TableDdlVo tableDdlVo = new TableDdlVo();
		tableDdlVo.setCurrent(DatabaseFactoryBean.DatabaseProduct.MYSQL.name().toLowerCase());
		tableDdlVo.setMysql(tableDdl.get("Create Table"));
		tableDdlVo.setOracle("// TODO 等待大佬来实现转换");
		// TODO 将建表语句转换为其他数据库的，还不知道怎么做，先这样留着，看有没大佬来实现
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
}
