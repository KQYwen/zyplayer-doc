package com.zyplayer.doc.db.framework.db.mapper.base;

import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 执行结果
 *
 * @author 暮光：城中城
 * @since 2019年8月18日
 */
@Data
public class ExecuteResult {
	private int updateCount;
	private long useTime;
	private String errMsg;
	private String sql;
	private List<Map<String, Object>> result;

	public ExecuteResult(String sql) {
		this.updateCount = -1;
		this.useTime = 0;
		this.result = Collections.emptyList();
		this.sql = sql;
	}

	public ExecuteResult(int updateCount, List<Map<String, Object>> result, long useTime, String sql) {
		this.updateCount = updateCount;
		this.result = result;
		this.useTime = useTime;
		this.sql = sql;
	}

	public static ExecuteResult error(String errMsg, String sql) {
		ExecuteResult executeResult = new ExecuteResult(sql);
		executeResult.setErrMsg(errMsg);
		return executeResult;
	}

}
