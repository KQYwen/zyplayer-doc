package com.zyplayer.doc.db.controller.vo;

import java.util.List;
import java.util.Map;

import com.zyplayer.doc.db.controller.vo.TableColumnVo.TableInfoVo;
import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;

public class DatabaseExportVo {

	private Map<String, List<TableColumnDescDto>> columnList;

	private List<TableInfoVo> tableList;

	public Map<String, List<TableColumnDescDto>> getColumnList() {
		return columnList;
	}

	public void setColumnList(Map<String, List<TableColumnDescDto>> columnList) {
		this.columnList = columnList;
	}

	public List<TableInfoVo> getTableList() {
		return tableList;
	}

	public void setTableList(List<TableInfoVo> tableList) {
		this.tableList = tableList;
	}

}
