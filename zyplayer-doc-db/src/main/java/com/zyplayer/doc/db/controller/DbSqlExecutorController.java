package com.zyplayer.doc.db.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zyplayer.doc.core.annotation.AuthMan;
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
import com.zyplayer.doc.db.framework.db.mapper.base.*;
import com.zyplayer.doc.db.framework.db.transfer.SqlParseUtil;
import com.zyplayer.doc.db.framework.json.DocDbResponseJson;
import com.zyplayer.doc.db.framework.utils.SQLTransformUtils;
import com.zyplayer.doc.db.service.database.DatabaseServiceFactory;
import com.zyplayer.doc.db.service.database.DbBaseService;
import org.apache.commons.lang3.StringUtils;
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
	private static final Logger logger = LoggerFactory.getLogger(DbSqlExecutorController.class);

	@Resource
	ColumnSqlExecutor columnSqlExecutor;
	@Resource
	DbHistoryService dbHistoryService;
	@Resource
	DbFavoriteService dbFavoriteService;
	@Resource
	DatabaseServiceFactory databaseServiceFactory;

	/**
	 * sql执行器
	 * @param sourceId
	 * @param executeId
	 * @param dbName
	 * @param sql
	 * @param params
	 * @param pageSize
	 * @param pageNum
	 * @param type  noPage:无分页   其他:有分页
	 * @return
	 */
	@PostMapping(value = "/execute")
	public DocDbResponseJson execute(Long sourceId, String executeId, String dbName, String sql, String params,Integer pageSize,Integer pageNum,String type) {
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
		// 解析出多个执行的SQL
		List<Map<String,Object>> analysisQuerySqlList = new LinkedList<>();
		try {
			String driverClassName = dbBaseService.getDatabaseProduct().getDriverClassName();
			List<SQLStatement> sqlStatements;
			//根据驱动程序类名获取数据库类型
			DbType dbType = SQLTransformUtils.getDbTypeByDriverClassName(driverClassName);
			sqlStatements = new SQLStatementParser(sql,dbType).parseStatementList();
			for (SQLStatement sqlStatement : sqlStatements) {
				StringBuffer sb = new StringBuffer(sqlStatement.toString());
				if(sb.length()>0&&';' == (sb.charAt(sb.length()-1))){
					sb.deleteCharAt(sb.length()-1);
				}
				Map<String,Object> map = new HashMap<>();
				//原始sql
				map.put("originalSql",sb);
				//sql解析类型
				map.put("sqlType","");
				//获取数据总量sql
				map.put("getAllCountSql","");
				//判断sql解析类型
				if(sqlStatement instanceof SQLSelectStatement){
					map.put("getAllCountSql","select count(1) from ("+sb+") r");
					map.put("sqlType","select");
				}
				analysisQuerySqlList.add(map);
			}
		} catch (Exception e) {
			return DocDbResponseJson.warn("SQL解析失败：" + e.getMessage());
		}
		// 执行条数太多，反应慢，展示结果栏太多，也不应该在这一次执行很多条语句，应该使用导入
		if (analysisQuerySqlList.size() > 20) {
			return DocDbResponseJson.warn("单次执行最多支持20条语句同时执行，当前语句条数：" + analysisQuerySqlList.size());
		}
		List<ColumnExecuteResult> resultList = new LinkedList<>();
		for (int i = 0; i < analysisQuerySqlList.size(); i++) {
			Map<String, Object> map = analysisQuerySqlList.get(i);
			ColumnExecuteResult executeResult;
			ColumnExecuteResult executeCountResult;
			//原始sql
			String originalSql = map.get("originalSql").toString();
			try {
				ExecuteType executeType = (manageAuth || update) ? ExecuteType.ALL : ExecuteType.SELECT;
				ExecuteParam executeParam = new ExecuteParam();
				executeParam.setDatasourceId(sourceId);
				executeParam.setExecuteId(executeId);
				executeParam.setExecuteType(executeType);
				executeParam.setPrefixSql(useDbSql);
				if(!StrUtil.equals(type,"noPage")){
					executeParam.setMaxRows(1000);
				}
				//sql解析类型为select
				if(map.get("sqlType").equals("select")){
					if(StrUtil.equals(type,"noPage")){
						executeParam = SqlParseUtil.getSingleExecuteParam(executeParam,originalSql, paramMap);
						//设置最后一次标志
						if(i==analysisQuerySqlList.size()-1){
							executeParam.setIsLastTime(true);
						}
						executeResult = columnSqlExecutor.execute(executeParam);
						resultList.add(executeResult);
						continue;
					}
					//获取总数据量sql
					String getAllCountSql = map.get("getAllCountSql").toString();
					executeParam = SqlParseUtil.getSingleExecuteParam(executeParam,getAllCountSql, paramMap);
					executeCountResult = columnSqlExecutor.execute(executeParam);
					List<List<Object>> data = executeCountResult.getData();
					long count = 0;
					if(data!=null){
						count = Long.parseLong(data.get(0).get(0)+"");
					}
					//设置最后一次标志
					if(i==analysisQuerySqlList.size()-1){
						executeParam.setIsLastTime(true);
					}
					//总数据量大于1000进行分页
					if(count>1000){
						String pageSql = dbBaseService.getQueryPageSqlBySql(originalSql,pageSize,pageNum);
						executeParam = SqlParseUtil.getSingleExecuteParam(executeParam,pageSql, paramMap);
						executeResult = columnSqlExecutor.execute(executeParam);
						executeResult.setSelectCount(count);
						resultList.add(executeResult);
						continue;
					}
				}
				executeParam = SqlParseUtil.getSingleExecuteParam(executeParam,originalSql, paramMap);
				executeResult = columnSqlExecutor.execute(executeParam);
			} catch (Exception e) {
				logger.error("执行出错", e);
				executeResult = ColumnExecuteResult.error(originalSql, e.getMessage(), e);
			}
			resultList.add(executeResult);
		}
		//预处理返回数据(解决大数据量下的性能问题)
		JSONArray array = JSONUtil.parseArray(resultList);
		return DocDbResponseJson.ok(array);
	}

	@PostMapping(value = "/cancel")
	public DocDbResponseJson cancel(String executeId) {
		columnSqlExecutor.cancel(executeId);
		return DocDbResponseJson.ok();
	}

	@PostMapping(value = "/history/list")
	public DocDbResponseJson historyList(Long sourceId) {
		UpdateWrapper<DbHistory> wrapper = new UpdateWrapper<>();
		wrapper.eq(sourceId != null, "datasource_id", sourceId);
		wrapper.orderByDesc("id");
		List<DbHistory> favoriteList = dbHistoryService.list(wrapper);
		return DocDbResponseJson.ok(favoriteList);
	}

	@PostMapping(value = "/favorite/list")
	public DocDbResponseJson favoriteList(Long sourceId) {
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
	public DocDbResponseJson addFavorite(DbFavorite dbFavorite) {
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
