package com.zyplayer.doc.data.service.elasticsearch.support;

import java.util.List;

/**
 * es查询字段封装
 * @author 暮光：城中城
 * @since 2019-07-07
 */
public class EsQueryColumn {
	private String key;
	private String value;
	private List<Object> values;
	// 类型，0=分词搜索 1=不分词
	private int type;
	
	public EsQueryColumn(String key, String value) {
		this(key, value, 0);
	}
	
	public EsQueryColumn(String key, String value, int type) {
		this.key = key;
		this.value = value;
		this.type = type;
	}
	
	public EsQueryColumn(String key, List<Object> values, int type) {
		this.key = key;
		this.values = values;
		this.type = type;
	}
	
	public static EsQueryColumn like(String key, String value){
		return new EsQueryColumn(key, value, 0);
	}
	
	public static EsQueryColumn must(String key, String value){
		return new EsQueryColumn(key, value, 1);
	}
	
	public static EsQueryColumn in(String key, List<Object> values){
		return new EsQueryColumn(key, values, 1);
	}
	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public List<Object> getValues() {
		return values;
	}
	
	public void setValues(List<Object> values) {
		this.values = values;
	}
}
