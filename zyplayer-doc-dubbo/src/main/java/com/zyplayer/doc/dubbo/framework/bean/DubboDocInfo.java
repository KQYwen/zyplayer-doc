package com.zyplayer.doc.dubbo.framework.bean;

import java.util.List;

/**
 * 请求参数对象
 *
 * @author 暮光：城中城
 * @since 2019年2月10日
 */
public class DubboDocInfo {
	private String service;
	private String method;
	private String function;
	private String explain;
	private String result;
	private String resultType;
	private Integer version;
	private List<DubboDocParam> params;
	
	public static class DubboDocParam {
		private String paramName;
		private String paramType;
		private String paramDesc;
		private Object paramValue;
		private Integer required;
		private List<DubboDocParam> params;
		
		public String getParamName() {
			return paramName;
		}
		
		public void setParamName(String paramName) {
			this.paramName = paramName;
		}
		
		public String getParamType() {
			return paramType;
		}
		
		public void setParamType(String paramType) {
			this.paramType = paramType;
		}
		
		public String getParamDesc() {
			return paramDesc;
		}
		
		public void setParamDesc(String paramDesc) {
			this.paramDesc = paramDesc;
		}
		
		public Object getParamValue() {
			return paramValue;
		}
		
		public void setParamValue(Object paramValue) {
			this.paramValue = paramValue;
		}
		
		public Integer getRequired() {
			return required;
		}
		
		public void setRequired(Integer required) {
			this.required = required;
		}
		
		public List<DubboDocParam> getParams() {
			return params;
		}
		
		public void setParams(List<DubboDocParam> params) {
			this.params = params;
		}
	}
	
	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getService() {
		return service;
	}
	
	public void setService(String service) {
		this.service = service;
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getExplain() {
		return explain;
	}
	
	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	public List<DubboDocParam> getParams() {
		return params;
	}
	
	public void setParams(List<DubboDocParam> params) {
		this.params = params;
	}
	
	public String getFunction() {
		return function;
	}
	
	public void setFunction(String function) {
		this.function = function;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getResultType() {
		return resultType;
	}
	
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
}
