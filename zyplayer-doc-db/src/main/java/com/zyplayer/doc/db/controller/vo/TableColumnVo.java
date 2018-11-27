package com.zyplayer.doc.db.controller.vo;

import java.util.List;

import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;

public class TableColumnVo {

	private List<TableColumnDescDto> columnList;

	private TableInfoVo tableInfo;

	public static class TableInfoVo {
		private String tableName;
		private String description;

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
	}

	public List<TableColumnDescDto> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<TableColumnDescDto> columnList) {
		this.columnList = columnList;
	}

	public TableInfoVo getTableInfo() {
		return tableInfo;
	}

	public void setTableInfo(TableInfoVo tableInfo) {
		this.tableInfo = tableInfo;
	}

}
