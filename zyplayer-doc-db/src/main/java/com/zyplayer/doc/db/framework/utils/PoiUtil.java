package com.zyplayer.doc.db.framework.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.io.IoUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.zyplayer.doc.db.controller.vo.DatabaseExportVo;
import com.zyplayer.doc.db.controller.vo.TableColumnVo;
import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * poi导出相关工具
 *
 * @author 暮光：城中城
 * @since 2020年8月21日
 */
public class PoiUtil {
	
	/**
	 * 导出为Text
	 *
	 * @param exportVo
	 * @param response
	 * @throws Exception
	 */
	public static void exportByText(DatabaseExportVo exportVo, HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename=database.js");
		response.setCharacterEncoding("utf-8");
		// 将文件输入流写入response的输出流中
		String content = "var docDbDatabase = " + JSON.toJSONString(exportVo);
		IoUtil.write(response.getOutputStream(), "utf-8", true, content);
	}
	
	/**
	 * 导出为Excel
	 *
	 * @param exportVo
	 * @param response
	 * @throws Exception
	 */
	public static void exportByXlsx(DatabaseExportVo exportVo, HttpServletResponse response) throws Exception {
		List<TableColumnVo.TableInfoVo> tableList = exportVo.getTableList();
		Map<String, List<TableColumnDescDto>> columnList = exportVo.getColumnList();
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		String fileName = URLEncoder.encode("数据库表导出", "UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
		WriteSheet writeSheet = EasyExcel.writerSheet(0, "表信息").head(TableColumnVo.TableInfoVo.class).build();
		excelWriter.write(tableList, writeSheet);
		int index = 1;
		for (Map.Entry<String, List<TableColumnDescDto>> entry : columnList.entrySet()) {
			writeSheet = EasyExcel.writerSheet(index++, entry.getKey()).head(TableColumnDescDto.class).build();
			excelWriter.write(entry.getValue(), writeSheet);
		}
		excelWriter.finish();
	}
	
	/**
	 * 导出为Word
	 *
	 * @param exportVo
	 * @param response
	 * @throws Exception
	 */
	public static void exportByDocx(String dbName, DatabaseExportVo exportVo, HttpServletResponse response) throws Exception {
		List<TableColumnVo.TableInfoVo> tableList = exportVo.getTableList();
		Map<String, List<TableColumnDescDto>> columnMap = exportVo.getColumnList();
		XWPFDocument document = new XWPFDocument();
		XWPFParagraph titleParagraph = document.createParagraph();
		titleParagraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun titleParagraphRun = titleParagraph.createRun();
		titleParagraphRun.setText("库表信息");
		titleParagraphRun.setColor("000000");
		titleParagraphRun.setFontSize(20);
		// 写入表信息
		PoiUtil.createEmptyLine(document);
		document.createParagraph().createRun().setText("数据库名：" + dbName);
		document.createParagraph().createRun().setText("导出时间：" + DateTime.now().toString());
		document.createParagraph().createRun().setText("导出说明：本文档使用zyplayer-doc生成并导出");
		document.createParagraph().createRun().setText("所有库表：");
		List<List<String>> baseDataList = new LinkedList<>();
		baseDataList.add(Arrays.asList("表名", "说明"));
		for (TableColumnVo.TableInfoVo dto : tableList) {
			baseDataList.add(Arrays.asList(dto.getTableName(), dto.getDescription()));
		}
		PoiUtil.createTable(document, baseDataList);
		// 所有表信息写入
		for (int i = 0; i < tableList.size(); i++) {
			TableColumnVo.TableInfoVo tableInfoVo = tableList.get(i);
			PoiUtil.createEmptyLine(document);
			XWPFParagraph firstParagraph = document.createParagraph();
			XWPFRun run = firstParagraph.createRun();
			// 写入标题
			String description = StringUtils.isBlank(tableInfoVo.getDescription()) ? "" : "（" + tableInfoVo.getDescription() + "）";
			run.setText(String.format("%s. %s%s", (i + 1), tableInfoVo.getTableName(), description));
			run.setColor("000000");
			run.setFontSize(18);
			List<List<String>> dataList = new LinkedList<>();
			List<TableColumnDescDto> tableColumnDescDtos = columnMap.get(tableInfoVo.getTableName());
			dataList.add(Arrays.asList("字段名", "是否自增", "类型", "空值", "长度", "主键", "注释"));
			// 写入表格
			for (TableColumnDescDto dto : tableColumnDescDtos) {
				dataList.add(Arrays.asList(dto.getName(), dto.getIsidenity(), dto.getType(),
						dto.getNullable(), dto.getLength(), dto.getIspramary(), dto.getDescription()));
			}
			PoiUtil.createTable(document, dataList);
		}
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		String fileName = URLEncoder.encode("数据库表导出", "UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".docx");
		ServletOutputStream outputStream = response.getOutputStream();
		document.write(outputStream);
		outputStream.close();
	}
	
	/**
	 * 创建Word的表格
	 */
	private static void createTable(XWPFDocument document, List<List<String>> dataList) {
		XWPFTable infoTable = document.createTable();
		//列宽自动分割
		CTTblWidth infoTableWidth = infoTable.getCTTbl().addNewTblPr().addNewTblW();
		infoTableWidth.setType(STTblWidth.DXA);
		infoTableWidth.setW(BigInteger.valueOf(9072));
		for (int i = 0; i < dataList.size(); i++) {
			XWPFTableRow xwpfTableRow;
			String bgColor = null;
			if (i == 0) {
				bgColor = "eeeeee";
				xwpfTableRow = infoTable.getRow(0);
			} else {
				xwpfTableRow = infoTable.createRow();
			}
			PoiUtil.createTableLine(xwpfTableRow, i, dataList.get(i), bgColor);
		}
	}
	
	/**
	 * 创建Word一个空白行
	 */
	private static void createEmptyLine(XWPFDocument document) {
		XWPFParagraph paragraph1 = document.createParagraph();
		XWPFRun paragraphRun1 = paragraph1.createRun();
		paragraphRun1.setText("\r");
	}
	
	/**
	 * 创建Word表格的一行
	 */
	private static void createTableLine(XWPFTableRow xwpfTableRow, int index, List<String> titleList, String bgColor) {
		for (int i = 0; i < titleList.size(); i++) {
			XWPFTableCell cell;
			if (i == 0 || index > 0) {
				cell = xwpfTableRow.getCell(i);
			} else {
				cell = xwpfTableRow.addNewTableCell();
			}
			cell.setText(titleList.get(i));
			if (StringUtils.isNotBlank(bgColor)) {
				cell.getCTTc().addNewTcPr().addNewShd().setFill(bgColor);
			}
		}
	}
}
