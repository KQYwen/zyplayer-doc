package com.zyplayer.doc.db.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.core.util.StringUtil;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.DbFavorite;
import com.zyplayer.doc.data.repository.manage.entity.DbHistory;
import com.zyplayer.doc.data.repository.support.consts.DocAuthConst;
import com.zyplayer.doc.data.service.manage.DbFavoriteService;
import com.zyplayer.doc.data.service.manage.DbHistoryService;
import com.zyplayer.doc.db.framework.consts.DbAuthType;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteParam;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteResult;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteType;
import com.zyplayer.doc.db.framework.db.mapper.base.SqlExecutor;
import com.zyplayer.doc.db.framework.json.DocDbResponseJson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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
	
	@PostMapping(value = "/execute")
	public ResponseJson execute(Long sourceId, String executeId, String sql, String params) {
		if (StringUtils.isBlank(sql)) {
			return DocDbResponseJson.warn("执行的SQL不能为空");
		}
		boolean manageAuth = DocUserUtil.haveAuth(DocAuthConst.DB_DATASOURCE_MANAGE);
		boolean select = DocUserUtil.haveCustomAuth(DbAuthType.SELECT.getName(), DocAuthConst.DB + sourceId);
		boolean update = DocUserUtil.haveCustomAuth(DbAuthType.UPDATE.getName(), DocAuthConst.DB + sourceId);
		if (!manageAuth && !select && !update) {
			return DocDbResponseJson.warn("没有该数据源的执行权限");
		}
		// 保留历史记录
		dbHistoryService.saveHistory(sql.trim(), sourceId);
		List<String> resultList = new LinkedList<>();
		// 支持;分割的多个sql执行
		String[] sqlArr = sql.split(";");
		for (String sqlItem : sqlArr) {
			if (StringUtils.isBlank(sqlItem)) {
				continue;
			}
			sqlItem = sqlItem.trim();
			try {
				ExecuteType executeType = (!manageAuth && select) ? ExecuteType.SELECT : ExecuteType.ALL;
				ExecuteParam executeParam = new ExecuteParam();
				executeParam.setDatasourceId(sourceId);
				executeParam.setExecuteId(executeId);
				executeParam.setExecuteType(executeType);
				executeParam.setSql(sqlItem);
				executeParam.setMaxRows(1000);
				ExecuteResult executeResult = sqlExecutor.execute(executeParam);
				SerializeConfig mapping = new SerializeConfig();
				mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
				mapping.put(Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
				String resultJsonStr = JSON.toJSONString(executeResult, mapping, SerializerFeature.WriteMapNullValue);
				resultList.add(resultJsonStr);
			} catch (Exception e) {
				logger.error("执行出错", e);
				ExecuteResult executeResult = ExecuteResult.error(StringUtil.getException(e), sqlItem);
				resultList.add(JSON.toJSONString(executeResult));
			}
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

