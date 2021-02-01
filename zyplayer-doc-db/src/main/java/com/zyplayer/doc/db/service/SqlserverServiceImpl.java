package com.zyplayer.doc.db.service;

import com.zyplayer.doc.db.controller.vo.TableColumnVo;
import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;
import com.zyplayer.doc.db.framework.db.mapper.base.BaseMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SqlserverServiceImpl extends DbBaseService {
	
	@Override
	DatabaseFactoryBean.DatabaseProduct getDatabaseProduct() {
		return DatabaseFactoryBean.DatabaseProduct.SQLSERVER;
	}
	
	@Override
	public TableColumnVo getTableColumnList(Long sourceId, String dbName, String tableName) {
		TableColumnVo tableColumnVo = super.getTableColumnList(sourceId, dbName, tableName);
		// SQLSERVER 要单独查字段注释
		BaseMapper baseMapper = this.getViewAuthBaseMapper(sourceId);
		List<TableColumnDescDto> columnDescList = baseMapper.getTableColumnDescList(tableName);
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
