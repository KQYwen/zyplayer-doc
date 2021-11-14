package com.zyplayer.doc.data.service.manage.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.DbTableRelation;
import com.zyplayer.doc.data.repository.manage.mapper.DbTableRelationMapper;
import com.zyplayer.doc.data.repository.manage.param.TableRelationParam;
import com.zyplayer.doc.data.service.manage.DbTableRelationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 表关系 服务实现类
 * </p>
 *
 * @author 暮光：城中城
 * @since 2021-06-07
 */
@Service
public class DbTableRelationServiceImpl extends ServiceImpl<DbTableRelationMapper, DbTableRelation> implements DbTableRelationService {
	
	@Override
	@Transactional
	public void update(TableRelationParam param) {
		UpdateWrapper<DbTableRelation> removeWrapper = new UpdateWrapper<>();
		removeWrapper.eq("datasource_id", param.getSourceId());
		removeWrapper.and(wrapper -> wrapper.or(or -> or.eq("start_db_name", param.getDbName())
				.eq("start_table_name", param.getTableName())
				.eq("start_column_name", param.getColumnName())
		).or(or -> or.eq("end_db_name", param.getDbName())
				.eq("end_table_name", param.getTableName())
				.eq("end_column_name", param.getColumnName())
		));
		this.remove(removeWrapper);
		if(StringUtils.isEmpty(param.getRelation())) {
			return;
		}
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		List<TableRelationParam> tableRelationList = JSON.parseArray(param.getRelation(), TableRelationParam.class);
		for (TableRelationParam relation : tableRelationList) {
			if (StringUtils.isBlank(relation.getDbName())
					|| StringUtils.isBlank(relation.getTableName())
					|| StringUtils.isBlank(relation.getColumnName())) {
				continue;
			}
			DbTableRelation tableRelation = new DbTableRelation();
			tableRelation.setDatasourceId(param.getSourceId());
			tableRelation.setStartDbName(param.getDbName());
			tableRelation.setStartTableName(param.getTableName());
			tableRelation.setStartColumnName(param.getColumnName());
			tableRelation.setEndDbName(relation.getDbName());
			tableRelation.setEndTableName(relation.getTableName());
			tableRelation.setEndColumnName(relation.getColumnName());
			tableRelation.setCreateTime(new Date());
			tableRelation.setCreateUserId(currentUser.getUserId());
			tableRelation.setCreateUserName(currentUser.getUsername());
			this.save(tableRelation);
		}
	}
}
