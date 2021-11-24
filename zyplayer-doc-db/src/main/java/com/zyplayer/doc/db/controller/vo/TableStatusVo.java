package com.zyplayer.doc.db.controller.vo;

import java.util.Date;

/**
 * 表基本信息
 *
 * @author 暮光：城中城
 * @since 2019-09-04
 */
public class TableStatusVo {
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEngine() {
		return engine;
	}
	
	public void setEngine(String engine) {
		this.engine = engine;
	}
	
	public Long getVersion() {
		return version;
	}
	
	public void setVersion(Long version) {
		this.version = version;
	}
	
	public String getRowFormat() {
		return rowFormat;
	}
	
	public void setRowFormat(String rowFormat) {
		this.rowFormat = rowFormat;
	}
	
	public Long getRows() {
		return rows;
	}
	
	public void setRows(Long rows) {
		this.rows = rows;
	}
	
	public Long getAvgRowLength() {
		return avgRowLength;
	}
	
	public void setAvgRowLength(Long avgRowLength) {
		this.avgRowLength = avgRowLength;
	}
	
	public Long getDataLength() {
		return dataLength;
	}
	
	public void setDataLength(Long dataLength) {
		this.dataLength = dataLength;
	}
	
	public Long getMaxDataLength() {
		return maxDataLength;
	}
	
	public void setMaxDataLength(Long maxDataLength) {
		this.maxDataLength = maxDataLength;
	}
	
	public Long getIndexLength() {
		return indexLength;
	}
	
	public void setIndexLength(Long indexLength) {
		this.indexLength = indexLength;
	}
	
	public Long getDataFree() {
		return dataFree;
	}
	
	public void setDataFree(Long dataFree) {
		this.dataFree = dataFree;
	}
	
	public Long getAutoIncrement() {
		return autoIncrement;
	}
	
	public void setAutoIncrement(Long autoIncrement) {
		this.autoIncrement = autoIncrement;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Date getCheckTime() {
		return checkTime;
	}
	
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	
	public String getCollation() {
		return collation;
	}
	
	public void setCollation(String collation) {
		this.collation = collation;
	}
	
	public String getChecksum() {
		return checksum;
	}
	
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	
	public String getCreateOptions() {
		return createOptions;
	}
	
	public void setCreateOptions(String createOptions) {
		this.createOptions = createOptions;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getDbType() {
		return dbType;
	}
	
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
}
