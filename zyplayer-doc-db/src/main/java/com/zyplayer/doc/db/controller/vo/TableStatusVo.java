package com.zyplayer.doc.db.controller.vo;

import lombok.Data;

import java.util.Date;

/**
 * 表基本信息
 *
 * @author 暮光：城中城
 * @since 2019-09-04
 */
@Data
public class TableStatusVo {
	// 表名
	private String name;
	private String engine;
	private Long version;
	private String rowFormat;
	private Long rows;
	private Long avgRowLength;
	private Long dataLength;
	private Long maxDataLength;
	private Long indexLength;
	private Long dataFree;
	private Long autoIncrement;
	private Date createTime;
	private Date updateTime;
	private Date checkTime;
	private String collation;
	private String checksum;
	private String createOptions;
	private String comment;
	private String dbType;
}
