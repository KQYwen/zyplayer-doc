package com.zyplayer.doc.db.framework.db.transfer;

import com.zyplayer.doc.db.framework.db.mapper.base.ExecuteParam;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;
import org.springframework.util.CollectionUtils;

import java.io.StringReader;
import java.util.*;

/**
 * sql预处理工具
 *
 * @author 暮光：城中城
 * @since 2019年9月28日
 */
public class SqlParseUtil {
	
	/**
	 * 通过SQL和参数列表获取处理后的SQL和参数
	 * @param sql
	 * @param paramList
	 * @return
	 */
	public static List<ExecuteParam> getExecuteParamList(String sql, List<Map<String, Object>> paramList) {
		// 单条操作
		if (paramList == null || paramList.size() <= 1) {
			Map<String, Object> paramMap = CollectionUtils.isEmpty(paramList) ? Collections.emptyMap() : paramList.get(0);
			ExecuteParam executeParam = getSingleExecuteParam(sql, paramMap);
			return Collections.singletonList(executeParam);
		}
		// 批量的insert语法
		ExecuteParam multiExecuteParam = getMultiExecuteParam(sql, paramList);
		if (multiExecuteParam != null) {
			return Collections.singletonList(multiExecuteParam);
		}
		// 不是insert语法单条处理
		List<ExecuteParam> executeParamList = new LinkedList<>();
		for (Map<String, Object> paramMap : paramList) {
			ExecuteParam executeParam = getSingleExecuteParam(sql, paramMap);
			executeParamList.add(executeParam);
		}
		return executeParamList;
	}
	
	/**
	 * 解析sql，如果是insert则把values拼成多个，用于支持批量插入
	 * 不是insert则返回null
	 * 不支持insert set语法，不支持values以外使用#{column}语法
	 * @param sql
	 * @param paramList
	 * @return
	 */
	public static ExecuteParam getMultiExecuteParam(String sql, List<Map<String, Object>> paramList) {
		List<Object> resultParamList = new LinkedList<>();
		SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder(new Configuration());
		String insertSql = sql.replaceAll("(#\\{\\w+})", "'$1'");
		// 处理${column}参数
		Insert insert = getInsertStmt(insertSql);
		if (insert == null) {
			return null;
		}
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("INSERT ");
		if (insert.getModifierPriority() != null) {
			sqlBuilder.append(insert.getModifierPriority().name()).append(" ");
		}
		if (insert.isModifierIgnore()) {
			sqlBuilder.append("IGNORE ");
		}
		sqlBuilder.append("INTO ");
		sqlBuilder.append(insert.getTable()).append(" ");
		if (insert.getColumns() != null) {
			sqlBuilder.append(PlainSelect.getStringList(insert.getColumns(), true, true)).append(" ");
		}
		if (insert.isUseValues()) {
			sqlBuilder.append("VALUES ");
		}
		if (insert.getItemsList() != null) {
			ExpressionList expressionList = (ExpressionList) insert.getItemsList();
			String fillSql = expressionList.toString().replaceAll("'(#\\{\\w+})'", "$1");
			StringBuilder itemsSb = new StringBuilder();
			for (Map<String, Object> paramMap : paramList) {
				if (itemsSb.length() > 0) {
					itemsSb.append(", ");
				}
				itemsSb.append(fillSql);
				// 解析获取参数
				StaticSqlSource parse = (StaticSqlSource) sqlSourceBuilder.parse(fillSql, Object.class, Collections.emptyMap());
				BoundSql boundSql = parse.getBoundSql(new Object());
				List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
				for (ParameterMapping parameterMapping : parameterMappings) {
					resultParamList.add(paramMap.get(parameterMapping.getProperty()));
				}
			}
			sqlBuilder.append(itemsSb.toString());
		}
//		pagehelper 使用了jsqlparser包，而且版本很低，，低版本没这个方法
//		if (insert.isUseSet()) {
//			throw new RuntimeException("暂不支持insert set语法");
//		}
		if (insert.isUseDuplicate()) {
			sqlBuilder.append(" ON DUPLICATE KEY UPDATE ");
			List<Column> duplicateUpdateColumns = insert.getDuplicateUpdateColumns();
			for (int i = 0; i < duplicateUpdateColumns.size(); i++) {
				if (i != 0) {
					sqlBuilder.append(", ");
				}
				sqlBuilder.append(duplicateUpdateColumns.get(i)).append(" = ");
				sqlBuilder.append(insert.getDuplicateUpdateExpressionList().get(i));
			}
		}
		if (insert.isReturningAllColumns()) {
			sqlBuilder.append(" RETURNING *");
		} else if (insert.getReturningExpressionList() != null) {
			sqlBuilder.append(" RETURNING ").append(PlainSelect.getStringList(insert.getReturningExpressionList(), true, false));
		}
		StaticSqlSource parse = (StaticSqlSource) sqlSourceBuilder.parse(sqlBuilder.toString(), Object.class, Collections.emptyMap());
		BoundSql boundSql = parse.getBoundSql(new Object());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		// values 里面的参数个数和所需参数个数不一致，则不支持批量，说明values外面还存在#{column}的参数
		if (parameterMappings.size() != resultParamList.size()) {
			throw new RuntimeException("insert除values以外不支持#{column}的动态参数");
		}
		// 组装结果
		ExecuteParam executeParam = new ExecuteParam();
		executeParam.setParameterMappings(parameterMappings);
		executeParam.setSql(boundSql.getSql());
		executeParam.setParamList(resultParamList);
		return executeParam;
	}
	
	/**
	 * 解析单条操作的SQL
	 * @param sql
	 * @param paramMap
	 * @return
	 */
	public static ExecuteParam getSingleExecuteParam(String sql, Map<String, Object> paramMap) {
		ExecuteParam executeParam = new ExecuteParam();
		
		SqlSourceBuilder sqlSourceBuilder = new SqlSourceBuilder(new Configuration());
		StaticSqlSource parse = (StaticSqlSource) sqlSourceBuilder.parse(sql, Object.class, paramMap);
		BoundSql boundSql =  parse.getBoundSql(new Object());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		List<Object> resultParamList = new LinkedList<>();
		for (ParameterMapping parameterMapping : parameterMappings) {
			resultParamList.add(paramMap.get(parameterMapping.getProperty()));
		}
		executeParam.setSql(boundSql.getSql());
		executeParam.setParameterMappings(parameterMappings);
		executeParam.setParamList(resultParamList);
		return executeParam;
	}
	
	/**
	 * 获取sql语句里面查询的列
	 *
	 * @return
	 */
	public static Insert getInsertStmt(String sql) {
		try {
			CCJSqlParserManager parser = new CCJSqlParserManager();
			Statement stmt = parser.parse(new StringReader(sql));
			if (stmt instanceof Insert) {
				Insert insertStmt = (Insert) stmt;
				ItemsList itemsList = insertStmt.getItemsList();
				if (itemsList instanceof ExpressionList) {
					return insertStmt;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取sql语句里面查询的列
	 *
	 * @return
	 */
	public static String getSelectCountSql(String sqlStr) {
		try {
			CCJSqlParserManager parser = new CCJSqlParserManager();
			Statement stmt = parser.parse(new StringReader(sqlStr));
			if (stmt instanceof Select) {
				Select selectStmt = (Select) stmt;
				SelectBody selectBody = selectStmt.getSelectBody();
				if (selectBody instanceof PlainSelect) {
					PlainSelect select = (PlainSelect) selectBody;
					StringBuilder countSql = new StringBuilder();
					countSql.append("SELECT count(0) counts");
					if (select.getFromItem() != null) {
						countSql.append(" FROM ").append(select.getFromItem());
						if (select.getJoins() != null) {
							Iterator<Join> it = select.getJoins().iterator();
							while (it.hasNext()) {
								Join join = it.next();
								if (join.isSimple()) {
									countSql.append(", ").append(join);
								} else {
									countSql.append(" ").append(join);
								}
							}
						}
						if (select.getWhere() != null) {
							countSql.append(" WHERE ").append(select.getWhere());
						}
						if (select.getOracleHierarchical() != null) {
							countSql.append(select.getOracleHierarchical().toString());
						}
						countSql.append(PlainSelect.getFormatedList(select.getGroupByColumnReferences(), "GROUP BY"));
						if (select.getHaving() != null) {
							countSql.append(" HAVING ").append(select.getHaving());
						}
					}
					return countSql.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		String storageSql = "select * from zyplayer_doc_manage.wiki_space where id=1 group by id";
		storageSql = storageSql.replaceAll("(#\\{\\w+})", "'$1'");
		String selectCountSql = getSelectCountSql(storageSql);
		System.out.println(selectCountSql);
	}
}
