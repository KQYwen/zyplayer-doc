package com.zyplayer.doc.db.framework.utils;

import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.zyplayer.doc.db.framework.db.sql.dialect.oracle.OracleToMySqlOutputVisitor;

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

        for(int i = 0; i < stmtList.size(); ++i) {
            ((SQLStatement)stmtList.get(i)).accept(visitor);
        }

        String mysqlSql = out.toString();
        return mysqlSql;
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
        StringBuffer sb = new StringBuffer();
        // 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
        while (s != null) {
            sb.append(s);
            s = br.readLine();
        }
        reString = sb.toString();
        return reString;
    }
}
