package com.zyplayer.doc.db.service;

import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.db.controller.vo.TableStatusVo;
import com.zyplayer.doc.db.framework.db.dto.QueryTableColumnDescDto;
import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;
import com.zyplayer.doc.db.framework.db.dto.TableDescDto;
import com.zyplayer.doc.db.framework.db.dto.TableInfoDto;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import com.zyplayer.doc.db.framework.db.mapper.base.BaseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public List<TableColumnDescDto> getTableColumnDescList(Long sourceId, String tableName) {
		throw new ConfirmException("不支持的操作");
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
