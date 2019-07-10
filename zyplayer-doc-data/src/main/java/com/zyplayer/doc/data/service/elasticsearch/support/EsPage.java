package com.zyplayer.doc.data.service.elasticsearch.support;

import java.util.List;

/**
 * es分页结果
 * @author 暮光：城中城
 * @since 2019-07-07
 */
public class EsPage<T> {
	private Long total;
	private List<T> data;
	
	public Long getTotal() {
		return total;
	}
	
	public void setTotal(Long total) {
		this.total = total;
	}
	
	public List<T> getData() {
		return data;
	}
	
	public void setData(List<T> data) {
		this.data = data;
	}
}
