package com.zyplayer.doc.db.framework.db.dto;

/**
 * 存储过程信息
 *
 * @author 暮光：城中城
 * @since 2021-04-25
 */
public class ProcedureDto {
	private String db;
	private String name;
	private String type;
	private String definer;
	private String body;
	private String paramList;
	private String returns;
	private String created;
	
	public String getDb() {
		return db;
	}
	
	public void setDb(String db) {
		this.db = db;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDefiner() {
		return definer;
	}
	
	public void setDefiner(String definer) {
		this.definer = definer;
	}
	
	public String getCreated() {
		return created;
	}
	
	public void setCreated(String created) {
		this.created = created;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getParamList() {
		return paramList;
	}
	
	public void setParamList(String paramList) {
		this.paramList = paramList;
	}
	
	public String getReturns() {
		return returns;
	}
	
	public void setReturns(String returns) {
		this.returns = returns;
	}
}
