package com.zyplayer.doc.db.controller.vo;

import com.zyplayer.doc.db.controller.vo.TableColumnVo.TableInfoVo;
import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 数据库表导出
 *
 * @author 暮光：城中城
 * @since 2018-11-27
 */
@Data
@AllArgsConstructor
public class DatabaseExportVo {

	// 表字段注释信息列表
	private Map<String, List<TableColumnDescDto>> columnList;

	// 数据库表列表
	private List<TableInfoVo> tableList;

}
