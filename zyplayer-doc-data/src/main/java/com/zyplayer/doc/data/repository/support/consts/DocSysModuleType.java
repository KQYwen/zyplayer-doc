package com.zyplayer.doc.data.repository.support.consts;

/**
 * 系统模块类型枚举
 *
 * @author 暮光：城中城
 * @since 2020-06-26
 */
public class DocSysModuleType {
	public static enum Manage {
		USER_MANAGE(1, "用户管理权限"),
		;
		
		Manage(int type, String desc) {
			this.type = type;
			this.desc = desc;
		}
		
		private final int type;
		private final String desc;
		
		public int getType() {
			return type;
		}
		
		public String getDesc() {
			return desc;
		}
	}
	
	public static enum Wiki {
		PAGE(1, "空间"),
		;
		
		Wiki(int type, String desc) {
			this.type = type;
			this.desc = desc;
		}
		
		private final int type;
		private final String desc;
		
		public int getType() {
			return type;
		}
		
		public String getDesc() {
			return desc;
		}
	}
	
	public static enum Db {
		DATASOURCE(1, "数据源管理"),
		;
		
		Db(int type, String desc) {
			this.type = type;
			this.desc = desc;
		}
		
		private final int type;
		private final String desc;
		
		public int getType() {
			return type;
		}
		
		public String getDesc() {
			return desc;
		}
	}
	
	public static enum Api {
		DOC(1, "api文档管理"),
		;
		
		Api(int type, String desc) {
			this.type = type;
			this.desc = desc;
		}
		
		private final int type;
		private final String desc;
		
		public int getType() {
			return type;
		}
		
		public String getDesc() {
			return desc;
		}
	}
}
