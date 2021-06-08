package com.zyplayer.doc.db.service;

import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.db.controller.vo.TableDdlVo;
import com.zyplayer.doc.db.controller.vo.TableStatusVo;
import com.zyplayer.doc.db.framework.db.dto.QueryTableColumnDescDto;
import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;
import com.zyplayer.doc.db.framework.db.dto.TableDescDto;
import com.zyplayer.doc.db.framework.db.dto.TableInfoDto;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import com.zyplayer.doc.db.framework.db.mapper.base.BaseMapper;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HiveServiceImpl extends DbBaseService {
	
	@Override
	public DatabaseProductEnum getDatabaseProduct() {
		return DatabaseProductEnum.HIVE;
	}
	
	@Override
	public List<TableInfoDto> getTableList(Long sourceId, String dbName) {
		BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
		List<TableInfoDto> tableList = baseMapper.getTableList(dbName);
		for (TableInfoDto tableInfoDto : tableList) {
			tableInfoDto.setDbName(dbName);
		}
		return tableList;
	}
	
	@Override
	public TableDdlVo getTableDdl(Long sourceId, String dbName, String tableName) {
		BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
		List<Map<String, String>> tableDdlList = baseMapper.getTableDdl(dbName, tableName);
		TableDdlVo tableDdlVo = new TableDdlVo();
		tableDdlVo.setCurrent(DatabaseProductEnum.HIVE.name().toLowerCase());
		if (CollectionUtils.isNotEmpty(tableDdlList)) {
			// hive和impala结果集不一样
			if (tableDdlList.size() == 1) {
				tableDdlVo.setHive(tableDdlList.get(0).get("result"));
			} else {
				String createTabStmt = tableDdlList.stream().map(map -> map.get("createtab_stmt")).collect(Collectors.joining("\n"));
				tableDdlVo.setHive(createTabStmt);
			}
		}
		return tableDdlVo;
	}
	
	@Override
	public List<QueryTableColumnDescDto> getTableAndColumnBySearch(Long sourceId, String dbName, String searchText) {
		throw new ConfirmException("不支持的操作");
	}
	
	@Override
	public List<TableDescDto> getTableDescList(Long sourceId, String dbName, String tableName) {
		throw new ConfirmException("不支持的操作");
	}
	
	@Override
	public void updateTableDesc(Long sourceId, String dbName, String tableName, String newDesc) {
		throw new ConfirmException("不支持的操作");
	}
	
	@Override
	public void updateTableColumnDesc(Long sourceId, String dbName, String tableName, String columnName, String newDesc) {
		throw new ConfirmException("不支持的操作");
	}
}
