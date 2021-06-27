package com.zyplayer.doc.db.framework.db.parser;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 参数填充帮助类
 *
 * @author 暮光：城中城
 * @since 2019-10-10
 */
public class FillParamUtil {
	
	// 填充时参数map里传入的当前时间的key
	public static final String PARAM_NOW = "_now";
	
	public static String fillSqlParam(String sql, Map<String, Object> paramMap) {
		return FillParamUtil.fillSqlParam(sql, paramMap, (dateTime, paramOne, format, paramThree) -> {
			int addInt = NumberUtils.toInt(paramThree);
			if (Objects.equals("now", paramOne)) {
				dateTime.offset(DateField.DAY_OF_MONTH, addInt);
				format = (format == null) ? "yyyy-MM-dd HH:mm:ss" : format;
			} else if (Objects.equals("dt", paramOne)) {
				dateTime.offset(DateField.DAY_OF_MONTH, addInt);
				format = (format == null) ? "yyyyMMdd" : format;
			} else if (Objects.equals("now_d", paramOne)) {
				dateTime.offset(DateField.DAY_OF_MONTH, addInt);
				format = (format == null) ? "yyyyMMdd" : format;
			} else if (Objects.equals("now_m", paramOne)) {
				dateTime.offset(DateField.MONTH, addInt);
				format = (format == null) ? "yyyyMM" : format;
			}
			return format;
		});
	}
	
	/**
	 * 解析${column}预处理的参数，支持内置参数和动态参数
	 * 内置参数格式：
	 * ${now}
	 * ${now, yyyy-MM-dd 00:00:00}
	 * ${now, yyyy-MM-dd 00:00:00, 1}
	 * 内置参数说明：
	 * 参数1、now：当前时间  now_d：当前天  now_m：当前月
	 * 参数2、加减天数，负数为减。参数1为 now/now_d 时，按天加减，为 now_m 时按月加减
	 * 参数3、日期格式，yyyy-MM-dd HH:mm:ss
	 *
	 * @param sql
	 * @param paramMap
	 * @return
	 * @author 暮光：城中城
	 * @since 2019-10-10
	 */
	public static String fillSqlParam(String sql, Map<String, Object> paramMap, FillParamParser paramParser) {
		// 组装参数
		GenericTokenParser parser = new GenericTokenParser("${", "}", content -> {
			Object o = paramMap.get(content);
			if (o == null) {
				Object nowDate = paramMap.get(FillParamUtil.PARAM_NOW);
				String[] keyArr = content.split(",");
				if (keyArr.length == 1) {
					o = getFillParam(paramParser, nowDate, keyArr[0].trim(), null, null);
				} else if (keyArr.length == 2) {
					o = getFillParam(paramParser, nowDate, keyArr[0].trim(), keyArr[1].trim(), null);
				} else if (keyArr.length == 3) {
					o = getFillParam(paramParser, nowDate, keyArr[0].trim(), keyArr[1].trim(), keyArr[2].trim());
				}
			}
			return (o == null) ? null : String.valueOf(o);
		});
		return parser.parse(sql);
	}
	
	/**
	 * 内置参数填充
	 * ${now, 'yyyy-MM-dd 00:00:00', 1}
	 *
	 * @param name
	 * @param format
	 * @param add
	 * @return
	 * @author 暮光：城中城
	 * @since 2019-10-10
	 */
	private static String getFillParam(FillParamParser parser, Object nowDate, String name, String format, String add) {
		DateTime dateTime;
		// 格式化加不加单引号都支持
		if (format != null && format.startsWith("'") && format.endsWith("'")) {
			format = format.substring(1, format.length() - 1);
		}
		// 使用系统时间还是传入的时间作为初始值
		if (nowDate instanceof Date) {
			dateTime = DateTime.of((Date) nowDate);
		} else {
			dateTime = DateTime.now();
		}
		format = (format == null) ? null : format.trim();
		if (parser != null) {
			format = parser.parser(dateTime, name, format, add);
		}
		if (StringUtils.isNotBlank(format)) {
			return dateTime.toString(format);
		}
		return null;
		// 内置参数格式：
		// ${now}
		// ${now, yyyy-MM-dd 00:00:00}
		// ${now, yyyy-MM-dd 00:00:00, 1}
		// 内置参数说明：
		// 参数1、now：当前时间  now_d：当前天  now_m：当前月 dt：当前年月日，默认yyyyMMdd格式
		// 参数2、加减天数，负数为减。参数1为 now/now_d 时，按天加减，为 now_m 时按月加减
		// 参数3、日期格式，yyyy-MM-dd HH:mm:ss
	}
	
	public static void main(String[] args) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("day", 4);
		String s = fillSqlParam("${now, yyyy-MM-dd 00:00:00, 2}\n dasda", paramMap);
		System.out.println(s);
	}
}
