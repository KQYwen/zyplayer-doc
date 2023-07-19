package com.zyplayer.doc.db.framework.db.mapper.base;

import lombok.Data;
import org.apache.ibatis.mapping.ParameterMapping;

import java.util.List;

/**
 * 通过SQL和参数列表处理后的执行参数对象
 *
 * @author 暮光：城中城
 * @since 2019-09-28
 */
@Data
public class ExecuteParam {
	private String sql;
	private List<Object> paramList;
	private List<ParameterMapping> parameterMappings;
	private Long datasourceId;
	private Integer maxRows;
	private String executeId;
	private String prefixSql;
	private ExecuteType executeType;
	private Boolean isLastTime;
}
