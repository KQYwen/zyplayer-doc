package com.zyplayer.doc.db.framework.db.mapper.base;

import lombok.Data;

import java.util.List;

/**
 * SQL执行结果对象
 *
 * @author 暮光：城中城
 * @since 2019-08-19
 */
@Data
public class ColumnExecuteResult {
	private Long queryTime;
	private Integer errCode;
	private String errMsg;
	private Exception exception;
	private String executeSql;
	private Integer updateCount;
	private long selectCount;
	// 查询结果表头
	private List<String> header;
	// 查询结果数据
	private List<List<Object>> data;

	public ColumnExecuteResult() {
		this.updateCount = 0;
	}

	public static ColumnExecuteResult ok(String sql) {
		ColumnExecuteResult result = new ColumnExecuteResult();
		result.setExecuteSql(sql);
		result.setErrCode(ExecuteResultCode.SUCCESS);
		return result;
	}

	public static ColumnExecuteResult warn(String sql, String errMsg) {
		ColumnExecuteResult result = new ColumnExecuteResult();
		result.setExecuteSql(sql);
		result.setErrMsg(errMsg);
		result.setErrCode(ExecuteResultCode.WARN);
		return result;
	}

	public static ColumnExecuteResult error(String sql, String errMsg, Exception e) {
		ColumnExecuteResult result = new ColumnExecuteResult();
		result.setExecuteSql(sql);
		result.setErrMsg(errMsg);
		result.setException(e);
		result.setErrCode(ExecuteResultCode.ERROR);
		return result;
	}

	public static class ExecuteResultCode {
		public static final Integer SUCCESS = 0;
		public static final Integer WARN = -1;
		public static final Integer ERROR = -2;
	}

	/**
	 * 判断错误并抛出异常
	 */
	public ColumnExecuteResult judgeAndThrow() {
		if (ExecuteResultCode.SUCCESS.equals(errCode)) {
			return this;
		}
		if (ExecuteResultCode.WARN.equals(errCode)) {
			throw new RuntimeException(errMsg);
		}
		if (ExecuteResultCode.ERROR.equals(errCode)) {
			if (exception != null) {
				throw new RuntimeException(exception);
			}
			throw new RuntimeException(errMsg);
		}
		return this;
	}
}
