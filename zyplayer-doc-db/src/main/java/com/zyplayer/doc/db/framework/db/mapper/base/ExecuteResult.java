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
	private List<Map<String, Object>> result;
	
	public ExecuteResult() {
		this.updateCount = 0;
		this.result = Collections.emptyList();
	}
	
	public ExecuteResult(int updateCount, List<Map<String, Object>> result) {
		this.updateCount = updateCount;
		this.result = result;
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
}
