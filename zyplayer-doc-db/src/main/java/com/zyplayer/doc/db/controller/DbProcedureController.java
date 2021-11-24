package com.zyplayer.doc.db.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.exception.ConfirmException;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserDetails;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.manage.entity.DbProcLog;
import com.zyplayer.doc.data.repository.support.consts.DocAuthConst;
import com.zyplayer.doc.data.service.manage.DbProcLogService;
import com.zyplayer.doc.db.controller.param.ProcedureListParam;
import com.zyplayer.doc.db.framework.consts.DbAuthType;
import com.zyplayer.doc.db.framework.db.dto.ProcedureDto;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteResult;
import com.zyplayer.doc.db.framework.json.DocDbResponseJson;
import com.zyplayer.doc.db.service.database.DatabaseServiceFactory;
import com.zyplayer.doc.db.service.database.DbBaseService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 存储过程管理控制器
 *
 * @author 暮光：城中城
 * @since 2021年4月24日
 */
@AuthMan
@RestController
@RequestMapping("/zyplayer-doc-db/procedure")
public class DbProcedureController {
	private static Logger logger = LoggerFactory.getLogger(DbProcedureController.class);
	
	@Resource
	DatabaseServiceFactory databaseServiceFactory;
	@Resource
	DbProcLogService dbProcLogService;
	
	/**
	 * 存储过程列表
	 *
	 * @param procedureParam 参数
	 * @return 列表
	 */
	@PostMapping(value = "/list")
	public ResponseJson list(ProcedureListParam procedureParam) {
		try {
			DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(procedureParam.getSourceId());
			procedureParam.setOffset((procedureParam.getPageNum() - 1) * procedureParam.getPageSize());
			List<ProcedureDto> procedureDtoList = dbBaseService.getProcedureList(procedureParam);
			DocDbResponseJson responseJson = DocDbResponseJson.ok(procedureDtoList);
			if (procedureParam.getPageNum() == 1) {
				responseJson.setTotal(dbBaseService.getProcedureCount(procedureParam));
			}
			return responseJson;
		} catch (Exception e) {
			// 一般是数据库的帐号没权限查存储过程
			return DocDbResponseJson.error(e.getMessage());
		}
	}
	
	/**
	 * 获取函数详情
	 *
	 * @param sourceId 数据源ID
	 * @param dbName   数据库名
	 * @param typeName 类型名
	 * @param procName 函数名
	 * @return 详情
	 */
	@PostMapping(value = "/detail")
	public ResponseJson detail(Long sourceId, String dbName, String typeName, String procName) {
		DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
		try {
			ProcedureDto procedureDto = dbBaseService.getProcedureDetail(sourceId, dbName, typeName, procName);
			return DocDbResponseJson.ok(procedureDto);
		} catch (Exception e) {
			// 一般是数据库的帐号没权限查存储过程
			return DocDbResponseJson.error(e.getMessage());
		}
	}
	
	/**
	 * 删除函数
	 *
	 * @param sourceId 数据源ID
	 * @param dbName   数据库名
	 * @param typeName 类型名
	 * @param procName 函数名
	 * @return 结果
	 */
	@PostMapping(value = "/delete")
	public ResponseJson delete(Long sourceId, String dbName, String typeName, String procName) {
		this.judgeAuth(sourceId, DbAuthType.PROC_EDIT.getName(), "没有修改该库函数的权限");
		DbProcLog dbProcLog = this.createDbProcLog(sourceId, dbName, typeName, procName, "删除函数操作");
		try {
			DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
			dbBaseService.deleteProcedure(sourceId, dbName, typeName, procName);
			return DocDbResponseJson.ok();
		} catch (Exception e) {
			dbProcLog.setStatus(2);
			// 一般是数据库的帐号没权限查存储过程
			return DocDbResponseJson.error(e.getMessage());
		} finally {
			dbProcLogService.save(dbProcLog);
		}
	}
	
	/**
	 * 保存函数
	 *
	 * @param sourceId 数据源ID
	 * @param dbName   数据库名
	 * @param typeName 类型名
	 * @param procName 函数名
	 * @param procSql  存储过程创建SQL
	 * @return 结果
	 */
	@PostMapping(value = "/save")
	public ResponseJson save(Long sourceId, String dbName, String typeName, String procName, String procSql) {
		this.judgeAuth(sourceId, DbAuthType.PROC_EDIT.getName(), "没有修改该库函数的权限");
		DbProcLog dbProcLog = this.createDbProcLog(sourceId, dbName, typeName, procName, procSql);
		try {
			DbBaseService dbBaseService = databaseServiceFactory.getDbBaseService(sourceId);
			ExecuteResult executeResult = dbBaseService.saveProcedure(sourceId, dbName, typeName, procName, procSql);
			if (StringUtils.isNotBlank(executeResult.getErrMsg())) {
				dbProcLog.setStatus(2);
			}
			return DocDbResponseJson.ok(executeResult);
		} catch (Exception e) {
			dbProcLog.setStatus(2);
			// 一般是数据库的帐号没权限查存储过程
			return DocDbResponseJson.error(e.getMessage());
		} finally {
			dbProcLogService.save(dbProcLog);
		}
	}
	
	/**
	 * 存储过程修改日志列表
	 *
	 * @param sourceId 数据源ID
	 * @param dbName   数据库名
	 * @param typeName 类型名
	 * @param procName 函数名
	 * @return 列表
	 */
	@PostMapping(value = "/log/list")
	public ResponseJson logList(Integer pageNum, Integer pageSize, Long sourceId, String dbName, String typeName, String procName) {
		QueryWrapper<DbProcLog> wrapper = new QueryWrapper<>();
		wrapper.eq("datasource_id", sourceId);
		wrapper.eq("proc_db", dbName);
		wrapper.eq("proc_name", procName);
		wrapper.eq("proc_type", typeName);
		wrapper.orderByDesc("id");
		wrapper.select("id", "proc_body", "create_user_name", "create_time", "status");
		IPage<DbProcLog> page = new Page<>(pageNum, pageSize, pageNum == 1);
		dbProcLogService.page(page, wrapper);
		return DocDbResponseJson.ok(page);
	}
	
	/**
	 * 存储过程修改日志详情
	 *
	 * @param logId 日志ID
	 * @return 详情
	 */
	@PostMapping(value = "/log/detail")
	public ResponseJson logDetail(Long logId) {
		DbProcLog dbProcLog = dbProcLogService.getById(logId);
		return DocDbResponseJson.ok(dbProcLog);
	}
	
	/**
	 * 权限判断
	 *
	 * @author 暮光：城中城
	 */
	private void judgeAuth(Long sourceId, String authName, String noAuthInfo) {
		if (!DocUserUtil.haveAuth(DocAuthConst.DB_DATASOURCE_MANAGE)
				&& !DocUserUtil.haveCustomAuth(authName, DocAuthConst.DB + sourceId)) {
			throw new ConfirmException(noAuthInfo);
		}
	}
	
	/**
	 * 创建日志对象
	 *
	 * @param sourceId
	 * @param dbName
	 * @param typeName
	 * @param procName
	 * @param procSql
	 * @return
	 */
	public DbProcLog createDbProcLog(Long sourceId, String dbName, String typeName, String procName, String procSql) {
		DocUserDetails currentUser = DocUserUtil.getCurrentUser();
		DbProcLog dbProcLog = new DbProcLog();
		dbProcLog.setDatasourceId(sourceId);
		dbProcLog.setCreateTime(new Date());
		dbProcLog.setCreateUserId(currentUser.getUserId());
		dbProcLog.setCreateUserName(currentUser.getUsername());
		dbProcLog.setProcDb(dbName);
		dbProcLog.setProcName(procName);
		dbProcLog.setProcType(typeName);
		dbProcLog.setProcBody(procSql);
		dbProcLog.setStatus(1);
		return dbProcLog;
	}
}

