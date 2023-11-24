package com.zyplayer.doc.db.framework.utils;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlLexer;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleLexer;
import com.alibaba.druid.sql.dialect.sqlserver.parser.SQLServerLexer;
import com.alibaba.druid.sql.parser.Keywords;
import com.zyplayer.doc.db.framework.db.enums.DatabaseProductEnum;
import com.zyplayer.doc.db.framework.db.sql.dialect.mysql.MySqlToOracleOutputVisitor;
import com.zyplayer.doc.db.framework.db.sql.dialect.mysql.MySqlToSqlServerOutputVisitor;
import com.zyplayer.doc.db.framework.db.sql.dialect.oracle.OracleToMySqlOutputVisitor;
import com.zyplayer.doc.db.framework.db.sql.dialect.sqlserver.SqlServerToMySqlOutputVisitor;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;

/**
 * sql语句转换工具类
 *
 * @author diantu
 * @since 2023年1月29日
 */
public class SQLTransformUtils {

    /**
     * oracle sql语句转换为mysql sql语句
     * @param sql
     * @return
     */
    public static String translateOracleToMySql(String sql) {
        List<SQLStatement> stmtList = SQLUtils.toStatementList(sql, DbType.oracle);
        StringBuilder out = new StringBuilder();
        OracleToMySqlOutputVisitor visitor = new OracleToMySqlOutputVisitor(out, false);

        for (SQLStatement sqlStatement : stmtList) {
            sqlStatement.accept(visitor);
        }

        return out.toString();
    }

    /**
     * sqlserver sql语句转换为mysql sql语句
     * @param sql
     * @return
     */
    public static String translateSqlServerToMySql(String sql) {
        List<SQLStatement> stmtList = SQLUtils.toStatementList(sql, DbType.sqlserver);
        StringBuilder out = new StringBuilder();
        SqlServerToMySqlOutputVisitor visitor = new SqlServerToMySqlOutputVisitor(out, false);

        for (SQLStatement sqlStatement : stmtList) {
            sqlStatement.accept(visitor);
        }

        return out.toString();
    }

    /**
     * mysql sql语句转换为oracle sql语句
     * @param sql
     * @return
     */
    public static String translateMySqlToOracle(String sql) {
        List<SQLStatement> stmtList = SQLUtils.toStatementList(sql, DbType.mysql);
        StringBuilder out = new StringBuilder();
        MySqlToOracleOutputVisitor visitor = new MySqlToOracleOutputVisitor(out, false);

        for (SQLStatement sqlStatement : stmtList) {
            sqlStatement.accept(visitor);
        }

        return out.toString();
    }

    /**
     * mysql sql语句转换为sqlserver sql语句
     * @param sql
     * @return
     */
    public static String translateMySqlToSqlServer(String sql) {
        List<SQLStatement> stmtList = SQLUtils.toStatementList(sql, DbType.mysql);
        StringBuilder out = new StringBuilder();
        MySqlToSqlServerOutputVisitor visitor = new MySqlToSqlServerOutputVisitor(out, false);

        for (SQLStatement sqlStatement : stmtList) {
            sqlStatement.accept(visitor);
        }

        return out.toString();
    }

    /**
     * 将字CLOB转成STRING类型
     * @param clob
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static String ClobToString(Clob clob) throws SQLException, IOException {

        String reString = "";
        // 得到流
        java.io.Reader is = clob.getCharacterStream();
        BufferedReader br = new BufferedReader(is);
        String s = br.readLine();
        StringBuilder sb = new StringBuilder();
        // 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
        while (s != null) {
            sb.append(s);
            s = br.readLine();
        }
        reString = sb.toString();
        return reString;
    }

    /**
     * 是否包含关键词
     * @param name 字段名
     * @param dbType 数据库类型
     * @return
     */
    public static boolean containsKeyWords(String name,DbType dbType) {
        if (StringUtils.isEmpty(name)) {
            return false;
        }
        if(dbType == DbType.mysql){
            return MySqlLexer.DEFAULT_MYSQL_KEYWORDS.getKeywords().containsKey(name.toUpperCase());
        }else if(dbType == DbType.oracle){
            return OracleLexer.DEFAULT_ORACLE_KEYWORDS.getKeywords().containsKey(name.toUpperCase());
        }else if(dbType == DbType.sqlserver){
            return SQLServerLexer.DEFAULT_SQL_SERVER_KEYWORDS.getKeywords().containsKey(name.toUpperCase());
        }else if(dbType == DbType.dm){
            return Keywords.DM_KEYWORDS.getKeywords().containsKey(name.toUpperCase());
        }
        return Keywords.DEFAULT_KEYWORDS.getKeywords().containsKey(name.toUpperCase());
    }

    /**
     *  根据驱动程序类名获取数据库类型
     * @param driverClassName 驱动程序类名
     * @return DbType 数据库类型
     */
    public static DbType getDbTypeByDriverClassName(String driverClassName) {
        DbType dbType;
        if(driverClassName.equalsIgnoreCase(DatabaseProductEnum.MYSQL.getDriverClassName())){
            dbType = DbType.mysql;
        }else if(driverClassName.equalsIgnoreCase(DatabaseProductEnum.ORACLE.getDriverClassName())){
            dbType = DbType.oracle;
        }else if(driverClassName.equalsIgnoreCase(DatabaseProductEnum.DM.getDriverClassName())){
            dbType = DbType.dm;
        }else if(driverClassName.equalsIgnoreCase(DatabaseProductEnum.SQLSERVER.getDriverClassName())){
            dbType = DbType.sqlserver;
        }else if(driverClassName.equalsIgnoreCase(DatabaseProductEnum.POSTGRESQL.getDriverClassName())){
            dbType = DbType.postgresql;
        }else if(driverClassName.equalsIgnoreCase(DatabaseProductEnum.HIVE.getDriverClassName())){
            dbType = DbType.hive;
        }else{
            dbType = DbType.other;
        }
        return dbType;
    }
}
