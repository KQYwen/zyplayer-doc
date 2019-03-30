package com.zyplayer.doc.grpc.controller.po;

import java.util.List;
import java.util.Map;

/**
 * 请求参数对象
 *
 * @author 暮光：城中城
 * @since 2019年2月10日
 */
public class GrpcServiceAndColumn {
	private List<GrpcDocInfo> serviceList;
	private Map<String, ColumnInfo> columnMap;
	
	public List<GrpcDocInfo> getServiceList() {
		return serviceList;
	}
	
	public void setServiceList(List<GrpcDocInfo> serviceList) {
		this.serviceList = serviceList;
	}
	
	public Map<String, ColumnInfo> getColumnMap() {
		return columnMap;
	}
	
	public void setColumnMap(Map<String, ColumnInfo> columnMap) {
		this.columnMap = columnMap;
	}
}
