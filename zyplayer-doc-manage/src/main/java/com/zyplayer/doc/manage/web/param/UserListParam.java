package com.zyplayer.doc.manage.web.param;

/**
 * 域账号用户列表查询参数
 *
 * @author 暮光：城中城
 * @since 2021年8月2日
 */
public class UserListParam {
	private Integer type;
	private String keyword;
	private Integer pageNum;
	private Integer pageSize;
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public Integer getPageNum() {
		return pageNum;
	}
	
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
