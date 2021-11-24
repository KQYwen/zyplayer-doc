package com.zyplayer.doc.db.controller.vo;

import com.zyplayer.doc.db.controller.vo.TableColumnVo.TableInfoVo;
import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;

import java.util.List;
import java.util.Map;

/**
 * 数据库表导出
 *
 * @author 暮光：城中城
 * @since 2018-11-27
 */
public class DatabaseExportVo {
	
	private Map<String, List<TableColumnDescDto>> columnList;
	
	private List<TableInfoVo> tableList;
	
	public DatabaseExportVo(Map<String, List<TableColumnDescDto>> columnList, List<TableInfoVo> tableList) {
		this.columnList = columnList;
		this.tableList = tableList;
	}
	
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
