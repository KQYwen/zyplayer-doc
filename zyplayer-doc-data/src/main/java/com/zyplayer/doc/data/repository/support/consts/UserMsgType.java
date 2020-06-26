package com.zyplayer.doc.data.repository.support.consts;

public enum UserMsgType {
	// 消息类型 1=普通文本消息 2=wiki文档创建 3=wiki文档删除 4=wiki文档编辑 5=wiki文档权限修改
	// 6=wiki文档评论 7=wiki文档删除评论 8=wiki文档上传附件 9=wiki文档修改了父级 10=wiki文档点赞 11=wiki文档附件删除
	SIMPLE(1), WIKI_PAGE_CREATE(2), WIKI_PAGE_DELETE(3), WIKI_PAGE_UPDATE(4), WIKI_PAGE_AUTH(5),
	WIKI_PAGE_COMMENT(6), WIKI_PAGE_COMMENT_DEL(7), WIKI_PAGE_UPLOAD(8), WIKI_PAGE_PARENT(9), WIKI_PAGE_ZAN(10), WIKI_PAGE_FILE_DEL(11),
	;
	
	UserMsgType(int type) {
		this.type = type;
	}
	
	private int type;
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
}
