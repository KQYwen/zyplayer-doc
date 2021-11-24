package com.zyplayer.doc.db.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.repository.manage.entity.DbTableRelation;
import com.zyplayer.doc.data.repository.manage.param.TableRelationParam;
import com.zyplayer.doc.data.repository.manage.vo.TableRelationVo;
import com.zyplayer.doc.data.service.manage.DbTableRelationService;
import com.zyplayer.doc.db.controller.vo.TableColumnVo;
import com.zyplayer.doc.db.framework.consts.DbAuthType;
import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;
import com.zyplayer.doc.db.framework.json.DocDbResponseJson;
import com.zyplayer.doc.db.service.database.DatabaseServiceFactory;
import com.zyplayer.doc.db.service.database.DbBaseService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * 表关系控制器
 *
 * @author 暮光：城中城
 * @since 2021年6月6日
 */
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-db/table-relation")
public class DbTableRelationController {
	private static Logger logger = LoggerFactory.getLogger(DbTableRelationController.class);
	
	@Resource
	DatabaseServiceFactory databaseServiceFactory;
	@Resource
	DbTableRelationService dbTableRelationService;
	
	@PostMapping(value = "/update")
	public ResponseJson update(TableRelationParam param) {
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(param.getSourceId());
		dbBaseService.judgeAuth(param.getSourceId(), DbAuthType.UPDATE.getName(), "没有该库的执行权限");
		dbTableRelationService.update(param);
		return DocDbResponseJson.ok();
	}
	
	@PostMapping(value = "/getRelation")
	public ResponseJson getRelation(TableRelationParam param) {
		TableRelationVo relationVo = new TableRelationVo();
		relationVo.setDbName(param.getDbName());
		relationVo.setName(param.getTableName());
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(param.getSourceId());
		TableColumnVo tableColumn = dbBaseService.getTableColumnList(param.getSourceId(), param.getDbName(), param.getTableName());
		if (CollectionUtils.isNotEmpty(tableColumn.getColumnList())) {
			Set<String> drillPath = new HashSet<>();
			List<TableRelationVo> childrenRelationList = new LinkedList<>();
			for (TableColumnDescDto columnDto : tableColumn.getColumnList()) {
				drillPath.add(param.getDbName() + "." + param.getTableName() + "." + columnDto.getName());
				TableRelationVo relationVoChildren = new TableRelationVo();
				relationVoChildren.setNodeType(1);
				relationVoChildren.setDbName(param.getDbName());
				relationVoChildren.setTableName(param.getTableName());
				relationVoChildren.setName(columnDto.getName());
				relationVoChildren.setColumnName(columnDto.getName());
				relationVoChildren.setChildren(this.getRelation(param.getSourceId(), param.getDbName(), param.getTableName(), columnDto.getName(), drillPath, 1));
				childrenRelationList.add(relationVoChildren);
			}
			relationVo.setChildren(childrenRelationList);
		}
		return DocDbResponseJson.ok(relationVo);
	}
	
	public List<TableRelationVo> getRelation(Long sourceId, String dbName, String tableName, String columnName, Set<String> drillPath, int recursion) {
		// 最大支持5层关系链展示
		if (recursion >= 5) {
			return Collections.emptyList();
		}
		QueryWrapper<DbTableRelation> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("datasource_id", sourceId);
		queryWrapper.and(wrapper -> wrapper.or(or -> or.eq("start_db_name", dbName)
				.eq("start_table_name", tableName)
				.eq("start_column_name", columnName)
		).or(or -> or.eq("end_db_name", dbName)
				.eq("end_table_name", tableName)
				.eq("end_column_name", columnName)
		));
		List<TableRelationVo> resultRelationList = new LinkedList<>();
		List<DbTableRelation> relationList = dbTableRelationService.list(queryWrapper);
		if (CollectionUtils.isEmpty(relationList)) {
			return resultRelationList;
		}
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
		for (DbTableRelation tableRelation : relationList) {
			String endDbName = tableRelation.getStartDbName();
			String endTableName = tableRelation.getStartTableName();
			String endColumnName = tableRelation.getStartColumnName();
			if (Objects.equals(tableRelation.getStartDbName(), dbName)
					&& Objects.equals(tableRelation.getStartTableName(), tableName)
					&& Objects.equals(tableRelation.getStartColumnName(), columnName)) {
				endDbName = tableRelation.getEndDbName();
				endTableName = tableRelation.getEndTableName();
				endColumnName = tableRelation.getEndColumnName();
			}
			if (drillPath.contains(endDbName + "." + endTableName + "." + endColumnName)) {
				continue;
			}
			TableRelationVo relationVo = new TableRelationVo();
			relationVo.setDbName(endDbName);
			relationVo.setTableName(endTableName);
			relationVo.setName("表：" + endTableName + "\n列：" + endColumnName);
			relationVo.setColumnName(endColumnName);
			TableColumnVo tableColumn = dbBaseService.getTableColumnList(sourceId, endDbName, endTableName);
			if (CollectionUtils.isNotEmpty(tableColumn.getColumnList())) {
				List<TableRelationVo> childrenRelationList = new LinkedList<>();
				for (TableColumnDescDto columnDto : tableColumn.getColumnList()) {
					boolean contains = drillPath.contains(endDbName + "." + endTableName + "." + columnDto.getName());
					drillPath.add(endDbName + "." + endTableName + "." + columnDto.getName());
					TableRelationVo relationVoChildren = new TableRelationVo();
					relationVoChildren.setNodeType(1);
					relationVoChildren.setDbName(endDbName);
					relationVoChildren.setTableName(endTableName);
					relationVoChildren.setName(columnDto.getName());
					relationVoChildren.setColumnName(columnDto.getName());
					if (!contains) {
						relationVoChildren.setChildren(this.getRelation(sourceId, endDbName, endTableName, columnDto.getName(), drillPath, recursion + 1));
					}
					childrenRelationList.add(relationVoChildren);
				}
				relationVo.setChildren(childrenRelationList);
			}
			resultRelationList.add(relationVo);
		}
		return resultRelationList;
	}
}

