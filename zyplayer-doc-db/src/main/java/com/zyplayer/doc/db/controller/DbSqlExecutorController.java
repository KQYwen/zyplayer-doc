package com.zyplayer.doc.db.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.DbFavorite;
import com.zyplayer.doc.data.repository.manage.entity.DbHistory;
import com.zyplayer.doc.data.repository.support.consts.DocAuthConst;
import com.zyplayer.doc.data.repository.support.consts.DocSysModuleType;
import com.zyplayer.doc.data.repository.support.consts.DocSysType;
import com.zyplayer.doc.data.service.manage.DbFavoriteService;
import com.zyplayer.doc.data.service.manage.DbHistoryService;
import com.zyplayer.doc.db.framework.consts.DbAuthType;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteParam;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteResult;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteType;
import com.zyplayer.doc.db.framework.db.mapper.base.SqlExecutor;
import com.zyplayer.doc.db.framework.db.transfer.SqlParseUtil;
import com.zyplayer.doc.db.framework.json.DocDbResponseJson;
import com.zyplayer.doc.db.framework.utils.JSONUtil;
import com.zyplayer.doc.db.framework.utils.SqlLogUtil;
import com.zyplayer.doc.db.service.database.DatabaseServiceFactory;
import com.zyplayer.doc.db.service.database.DbBaseService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * sql执行器
 *
 * @author 暮光：城中城
 * @since 2019年8月18日
 */
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-db/executor")
public class DbSqlExecutorController {
	private static Logger logger = LoggerFactory.getLogger(DbSqlExecutorController.class);
	
	@Resource
	SqlExecutor sqlExecutor;
	@Resource
	DbHistoryService dbHistoryService;
	@Resource
	DbFavoriteService dbFavoriteService;
	@Resource
	DatabaseServiceFactory databaseServiceFactory;
	
	@PostMapping(value = "/execute")
	public ResponseJson execute(Long sourceId, String executeId, String dbName, String sql, String params) {
		if (StringUtils.isBlank(sql)) {
			return DocDbResponseJson.warn("执行的SQL不能为空");
		}
		boolean manageAuth = DocUserUtil.haveAuth(DocAuthConst.DB_DATASOURCE_MANAGE);
		boolean select = DocUserUtil.haveCustomAuth(DbAuthType.SELECT.getName(), DocSysType.DB.getType(), DocSysModuleType.Db.DATASOURCE.getType(), sourceId);
		boolean update = DocUserUtil.haveCustomAuth(DbAuthType.UPDATE.getName(), DocSysType.DB.getType(), DocSysModuleType.Db.DATASOURCE.getType(), sourceId);
		if (!manageAuth && !select && !update) {
			return DocDbResponseJson.warn("没有该数据源的执行权限");
		}
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
		String useDbSql = dbBaseService.getUseDbSql(dbName);
		// 保留历史记录
		dbHistoryService.saveHistory(sql.trim(), params, sourceId);
		// 参数处理
		Map<String, Object> paramMap = JSON.parseObject(params);
		List<String> resultList = new LinkedList<>();
		// 支持;分割的多个sql执行
		String[] sqlArr = sql.split(";");
		// 执行条数太多，反应慢，展示结果栏太多，也不应该在这一次执行很多条语句，应该使用导入
		if (sqlArr.length > 20) {
			return DocDbResponseJson.warn("单次执行最多支持20条语句同时执行，当前语句条数：" + sqlArr.length);
		}
		for (String sqlItem : sqlArr) {
			if (StringUtils.isBlank(sqlItem)) {
				continue;
			}
			sqlItem = sqlItem.trim();
			ExecuteResult executeResult;
			ExecuteParam executeParam = new ExecuteParam();
			try {
				ExecuteType executeType = (manageAuth || update) ? ExecuteType.ALL : ExecuteType.SELECT;
				executeParam = SqlParseUtil.getSingleExecuteParam(sqlItem, paramMap);
				executeParam.setDatasourceId(sourceId);
				executeParam.setExecuteId(executeId);
				executeParam.setExecuteType(executeType);
				executeParam.setPrefixSql(useDbSql);
				executeParam.setMaxRows(1000);
				executeResult = sqlExecutor.execute(executeParam);
			} catch (Exception e) {
				logger.error("执行出错", e);
				executeResult = ExecuteResult.error(e.getMessage(), sqlItem);
			}
			// 执行的sql处理
			String executeSqlLog = SqlLogUtil.parseLogSql(executeParam.getSql(), executeParam.getParameterMappings(), executeParam.getParamList());
			executeResult.setSql(executeSqlLog);
			resultList.add(JSON.toJSONString(executeResult, JSONUtil.serializeConfig, SerializerFeature.WriteMapNullValue));
		}
		return DocDbResponseJson.ok(resultList);
	}
	
	@PostMapping(value = "/cancel")
	public ResponseJson cancel(String executeId) {
		sqlExecutor.cancel(executeId);
		return DocDbResponseJson.ok();
	}
	
	@PostMapping(value = "/history/list")
	public ResponseJson historyList(Long sourceId) {
		UpdateWrapper<DbHistory> wrapper = new UpdateWrapper<>();
		wrapper.eq(sourceId != null, "datasource_id", sourceId);
		wrapper.orderByDesc("id");
		List<DbHistory> favoriteList = dbHistoryService.list(wrapper);
		return DocDbResponseJson.ok(favoriteList);
	}
	
	@PostMapping(value = "/favorite/list")
	public ResponseJson favoriteList(Long sourceId) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		UpdateWrapper<DbFavorite> wrapper = new UpdateWrapper<>();
		wrapper.eq(sourceId != null, "datasource_id", sourceId);
		wrapper.eq("create_user_id", currentUser.getUserId());
		wrapper.eq("yn", 1);
		wrapper.orderByDesc("id");
		List<DbFavorite> favoriteList = dbFavoriteService.list(wrapper);
		return DocDbResponseJson.ok(favoriteList);
	}
	
	@PostMapping(value = "/favorite/add")
	public ResponseJson addFavorite(DbFavorite dbFavorite) {
		Integer yn = Optional.ofNullable(dbFavorite.getYn()).orElse(1);
		if (yn == 1) {
			if (StringUtils.isBlank(dbFavorite.getContent())) {
				return DocDbResponseJson.warn("收藏的SQL不能为空");
			}
			dbFavorite.setContent(dbFavorite.getContent().trim());
		}
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		if (dbFavorite.getId() != null && dbFavorite.getId() > 0) {
			dbFavoriteService.updateById(dbFavorite);
		} else {
			dbFavorite.setCreateTime(new Date());
			dbFavorite.setCreateUserId(currentUser.getUserId());
			dbFavorite.setCreateUserName(currentUser.getUsername());
			dbFavorite.setYn(1);
			dbFavoriteService.save(dbFavorite);
		}
		return DocDbResponseJson.ok();
	}
	
}

