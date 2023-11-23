package com.zyplayer.doc.db.framework.db.sql.dialect.mysql.util;

import com.alibaba.druid.sql.SQLTransformUtils;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLDataType;
import com.alibaba.druid.sql.ast.SQLDataTypeImpl;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLIntegerExpr;
import com.alibaba.druid.sql.ast.expr.SQLNumericLiteralExpr;
import com.alibaba.druid.sql.ast.statement.SQLCharacterDataType;
import com.alibaba.druid.util.FnvHash;

import java.util.List;

/**
 * mysql sql字段类型转换工具类
 *
 * @author diantu
 * @since 2023年1月30日
 */
public class MySqlSQLDataTypeTransformUtil extends SQLTransformUtils {

    /**
     * MySql->Oracle字段类型转换
     */
    public static SQLDataType transformMySqlToOracle(SQLDataType x) {
        final String name = x.getName();
        final long nameHash = x.nameHashCode64();
        if (name == null) {
            return x;
        }
        List<SQLExpr> argumentns = x.getArguments();
        SQLDataType dataType;
        if (nameHash == FnvHash.Constants.SMALLINT
                //|| nameHash == FnvHash.Constants.MEDIUMINT
                || nameHash == FnvHash.Constants.INT
                || nameHash == FnvHash.Constants.BIGINT
                || nameHash == FnvHash.Constants.TINYINT) {
            if(!argumentns.isEmpty()){
                int len;
                SQLExpr arg0 = argumentns.get(0);
                if (arg0 instanceof SQLNumericLiteralExpr) {
                    len = ((SQLNumericLiteralExpr) arg0).getNumber().intValue();
                } else {
                    throw new UnsupportedOperationException(SQLUtils.toMySqlString(x));
                }
                dataType = new SQLDataTypeImpl("NUMBER",len);
            } else {
                dataType = new SQLCharacterDataType("NUMBER");
            }

        } else if (nameHash == FnvHash.Constants.FLOAT
                || nameHash == FnvHash.Constants.DOUBLE
                || nameHash == FnvHash.Constants.DECIMAL) {
            dataType = new SQLDataTypeImpl("DECIMAL");
            int precision = 0;
            if (!argumentns.isEmpty()) {
                precision = ((SQLIntegerExpr) argumentns.get(0)).getNumber().intValue();
                dataType = new SQLDataTypeImpl("DECIMAL",precision);
            }

            int scale = 0;
            if (argumentns.size() > 1) {
                scale = ((SQLIntegerExpr) argumentns.get(1)).getNumber().intValue();
                dataType = new SQLDataTypeImpl("DECIMAL",precision,scale);
            }

        } else if (nameHash == FnvHash.Constants.DATE
                || nameHash == FnvHash.Constants.DATETIME) {
            dataType = new SQLDataTypeImpl("DATE");

        } else if (nameHash == FnvHash.Constants.TIMESTAMP) {
            dataType = new SQLDataTypeImpl("TIMESTAMP");

        } else if (nameHash == FnvHash.Constants.VARCHAR
                ||nameHash == FnvHash.Constants.CHAR) {
            if(!argumentns.isEmpty()){
                int len;
                SQLExpr arg0 = argumentns.get(0);
                if (arg0 instanceof SQLNumericLiteralExpr) {
                    len = ((SQLNumericLiteralExpr) arg0).getNumber().intValue();
                } else {
                    throw new UnsupportedOperationException(SQLUtils.toMySqlString(x));
                }
                dataType = new SQLDataTypeImpl("VARCHAR2",len);
            }else{
                dataType = new SQLDataTypeImpl("VARCHAR2");
            }

        } else if (nameHash == FnvHash.Constants.LONGTEXT) {
            argumentns.clear();
            dataType = new SQLCharacterDataType("CLOB");

        } else if (nameHash == FnvHash.Constants.BOOLEAN) {
            dataType = new SQLDataTypeImpl("NUMBER",1);

        } else {
            dataType = x;
        }

        if (dataType != x) {
            dataType.setParent(x.getParent());
        }

        return dataType;
    }

    /**
     * MySql->SqlServer字段类型转换
     */
    public static SQLDataType transformMySqlToSqlServer(SQLDataType x) {
        final String name = x.getName();
        final long nameHash = x.nameHashCode64();
        if (name == null) {
            return x;
        }
        List<SQLExpr> argumentns = x.getArguments();
        SQLDataType dataType;
        if (nameHash == FnvHash.Constants.ENUM) {
            argumentns.clear();
            dataType = new SQLCharacterDataType("enum");

        } else if(nameHash == FnvHash.Constants.SET){
            argumentns.clear();
            dataType = new SQLCharacterDataType("set");

        }else if(nameHash == FnvHash.Constants.BIGINT){
            dataType = new SQLCharacterDataType("bigint");

        }else if(nameHash == FnvHash.Constants.INT){
            dataType = new SQLCharacterDataType("int");

        }else if(nameHash == FnvHash.Constants.TINYINT){
            dataType = new SQLCharacterDataType("tinyint");

        }else if(nameHash == FnvHash.Constants.SMALLINT){
            dataType = new SQLCharacterDataType("smallint");

        }else {
            dataType = x;
        }

        if (dataType != x) {
            dataType.setParent(x.getParent());
        }

        return dataType;
    }
}
