package com.zyplayer.doc.db.framework.db.mapper.base;

import org.apache.ibatis.mapping.ParameterMapping;

import java.util.List;

/**
 * 通过SQL和参数列表处理后的执行参数对象
 *
 * @author 暮光：城中城
 * @since 2019-09-28
 */
public class ExecuteParam {
	private String sql;
	private List<Object> paramList;
	private List<ParameterMapping> parameterMappings;
	private Long datasourceId;
	private String executeId;
	private ExecuteType executeType;
	
	public Long getDatasourceId() {
		return datasourceId;
	}
	
	public void setDatasourceId(Long datasourceId) {
		this.datasourceId = datasourceId;
	}
	
	public String getExecuteId() {
		return executeId;
	}
	
	public void setExecuteId(String executeId) {
		this.executeId = executeId;
	}
	
	public ExecuteType getExecuteType() {
		return executeType;
	}
	
	public void setExecuteType(ExecuteType executeType) {
		this.executeType = executeType;
	}
	
	public List<ParameterMapping> getParameterMappings() {
		return parameterMappings;
	}
	
	public void setParameterMappings(List<ParameterMapping> parameterMappings) {
		this.parameterMappings = parameterMappings;
	}
	
	public String getSql() {
		return sql;
	}
	
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public List<Object> getParamList() {
		return paramList;
	}
	
	public void setParamList(List<Object> paramList) {
		this.paramList = paramList;
	}
}
