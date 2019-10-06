package com.zyplayer.doc.db.framework.db.transfer;

public enum TransferTaskStatus {
	//	最后执行状态 0=未执行 1=执行中 2=执行成功 3=执行失败 4=取消执行
	NOT_START(0), EXECUTING(1), SUCCESS(2), ERROR(3), CANCEL(4);
	private Integer code;
	
	TransferTaskStatus(Integer code) {
		this.code = code;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}
}
