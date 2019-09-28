package com.zyplayer.doc.db.framework.db.mapper.base;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 执行结果
 *
 * @author 暮光：城中城
 * @since 2019年8月18日
 */
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
	
	public int getUpdateCount() {
		return updateCount;
	}
	
	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}
	
	public List<Map<String, Object>> getResult() {
		return result;
	}
	
	public void setResult(List<Map<String, Object>> result) {
		this.result = result;
	}
	
	public long getUseTime() {
		return useTime;
	}
	
	public void setUseTime(long useTime) {
		this.useTime = useTime;
	}
	
	public String getErrMsg() {
		return errMsg;
	}
	
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	public static ExecuteResult error(String errMsg, String sql) {
		ExecuteResult executeResult = new ExecuteResult(sql);
		executeResult.setErrMsg(errMsg);
		return executeResult;
	}
	
	public String getSql() {
		return sql;
	}
	
	public void setSql(String sql) {
		this.sql = sql;
	}
}
