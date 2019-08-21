package com.zyplayer.doc.db.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zyplayer.doc.core.annotation.AuthMan;
import com.zyplayer.doc.core.json.ResponseJson;
import com.zyplayer.doc.core.util.StringUtil;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteResult;
import com.zyplayer.doc.db.framework.db.mapper.base.SqlExecutor;
import com.zyplayer.doc.db.framework.json.DocDbResponseJson;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

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
	
	@Resource
	SqlExecutor sqlExecutor;
	
	@PostMapping(value = "/execute")
	public ResponseJson execute(Long sourceId, String executeId, String sql, String params) {
		Map<String, Object> paramMap = JSON.parseObject(params);
		try {
			ExecuteResult executeResult = sqlExecutor.execute(sourceId, executeId, sql, paramMap);
			String resultJsonStr = JSON.toJSONString(executeResult, SerializerFeature.WriteMapNullValue);
			return DocDbResponseJson.ok(resultJsonStr);
		} catch (Exception e) {
			return DocDbResponseJson.warn(StringUtil.getException(e));
		}
	}
	
	@PostMapping(value = "/cancel")
	public ResponseJson cancel(String executeId) {
		sqlExecutor.cancel(executeId);
		return DocDbResponseJson.ok();
	}
}

