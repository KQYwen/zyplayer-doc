package com.zyplayer.doc.db.service;

import com.zyplayer.doc.db.framework.db.bean.DatabaseFactoryBean;
import com.zyplayer.doc.db.framework.db.dto.TableInfoDto;
import com.zyplayer.doc.db.framework.db.mapper.base.BaseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HiveServiceImpl extends DbBaseService {
	
	@Override
	public DatabaseFactoryBean.DatabaseProduct getDatabaseProduct() {
		return DatabaseFactoryBean.DatabaseProduct.HIVE;
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
}
