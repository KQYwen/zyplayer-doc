package com.zyplayer.doc.db.service;

import com.zyplayer.doc.db.controller.vo.TableColumnVo;
import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import com.zyplayer.doc.db.framework.db.mapper.sqlserver.SqlServerMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SqlserverServiceImpl extends DbBaseService {
	
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
}
