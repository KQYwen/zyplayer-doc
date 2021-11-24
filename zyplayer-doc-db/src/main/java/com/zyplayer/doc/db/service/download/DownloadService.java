package com.zyplayer.doc.db.service.download;

import com.zyplayer.doc.db.controller.param.DataViewParam;
import com.zyplayer.doc.db.framework.db.dto.TableColumnDescDto;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteParam;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 下载服务
 *
 * @author 暮光：城中城
 * @since 2021-06-17
 */
@Service
public interface DownloadService {
	
	DatabaseProductEnum getDatabaseProductEnum();
	
	/**
	 * 导出数据为insert语句格式
	 *
	 * @author 暮光：城中城
	 * @since 2020年6月5日
	 */
	public String downloadDataByInsert(DataViewParam param, ExecuteParam executeParam, List<TableColumnDescDto> dataCols, Set<String> conditionSet) throws Exception;
	
	/**
	 * 导出数据为update语句格式
	 *
	 * @author 暮光：城中城
	 * @since 2020年6月5日
	 */
	public String downloadDataByUpdate(DataViewParam param, ExecuteParam executeParam, List<TableColumnDescDto> dataCols, Set<String> conditionSet) throws Exception;
	
	/**
	 * 导出数据为json格式
	 *
	 * @author 暮光：城中城
	 * @since 2020年6月5日
	 */
	public String downloadDataByJson(DataViewParam param, ExecuteParam executeParam, List<TableColumnDescDto> dataCols, Set<String> conditionSet) throws Exception;
	
	
	/**
	 * 是否是数值类型
	 *
	 * @param type 类型
	 * @return 结果
	 */
	public boolean isNumber(String type);
}
