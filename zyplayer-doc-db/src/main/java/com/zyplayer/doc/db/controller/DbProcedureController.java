package com.zyplayer.doc.db.controller;

import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.data.config.security.DocUserUtil;
import com.zyplayer.doc.data.repository.support.consts.DocAuthConst;
import com.zyplayer.doc.db.controller.param.ProcedureListParam;
import com.zyplayer.doc.db.framework.db.dto.ProcedureDto;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteResult;
import com.zyplayer.doc.db.framework.json.DocDbResponseJson;
import com.zyplayer.doc.db.service.DbBaseFactory;
import com.zyplayer.doc.db.service.DbBaseService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
	DbBaseFactory dbBaseFactory;
	
	/**
	 * 存储过程列表
	 *
	 * @param procedureParam 参数
	 * @return 列表
	 */
	@PostMapping(value = "/list")
	public ResponseJson list(ProcedureListParam procedureParam) {
		boolean manageAuth = DocUserUtil.haveAuth(DocAuthConst.DB_DATASOURCE_MANAGE);
		if (!manageAuth) {
			return DocDbResponseJson.warn("没有该数据源的管理权限");
		}
		DbBaseService dbBaseService = dbBaseFactory.getDbBaseService(procedureParam.getSourceId());
		procedureParam.setOffset((procedureParam.getPageNum() - 1) * procedureParam.getPageSize());
		List<ProcedureDto> procedureDtoList = dbBaseService.getProcedureList(procedureParam);
		DocDbResponseJson responseJson = DocDbResponseJson.ok(procedureDtoList);
		if (procedureParam.getPageNum() == 1) {
			responseJson.setTotal(dbBaseService.getProcedureCount(procedureParam));
		}
		return responseJson;
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
		boolean manageAuth = DocUserUtil.haveAuth(DocAuthConst.DB_DATASOURCE_MANAGE);
		if (!manageAuth) {
			return DocDbResponseJson.warn("没有该数据源的管理权限");
		}
		DbBaseService dbBaseService = dbBaseFactory.getDbBaseService(sourceId);
		ProcedureDto procedureDto = dbBaseService.getProcedureDetail(sourceId, dbName, typeName, procName);
		return DocDbResponseJson.ok(procedureDto);
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
		boolean manageAuth = DocUserUtil.haveAuth(DocAuthConst.DB_DATASOURCE_MANAGE);
		if (!manageAuth) {
			return DocDbResponseJson.warn("没有该数据源的管理权限");
		}
		DbBaseService dbBaseService = dbBaseFactory.getDbBaseService(sourceId);
		dbBaseService.deleteProcedure(sourceId, dbName, typeName, procName);
		return DocDbResponseJson.ok();
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
		boolean manageAuth = DocUserUtil.haveAuth(DocAuthConst.DB_DATASOURCE_MANAGE);
		if (!manageAuth) {
			return DocDbResponseJson.warn("没有该数据源的管理权限");
		}
		DbBaseService dbBaseService = dbBaseFactory.getDbBaseService(sourceId);
		ExecuteResult executeResult = dbBaseService.saveProcedure(sourceId, dbName, typeName, procName, procSql);
		return DocDbResponseJson.ok(executeResult);
	}
	
}

