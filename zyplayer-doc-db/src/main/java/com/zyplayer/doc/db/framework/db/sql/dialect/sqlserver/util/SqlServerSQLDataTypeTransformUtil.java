package com.zyplayer.doc.db.framework.db.sql.dialect.sqlserver.util;

import com.alibaba.druid.sql.SQLTransformUtils;
import com.alibaba.druid.sql.ast.SQLDataType;
import com.alibaba.druid.sql.ast.SQLDataTypeImpl;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLIntegerExpr;
import com.alibaba.druid.util.FnvHash;

import java.util.List;

/**
 * sqlserver sql字段类型转换工具类
 *
 * @author diantu
 * @since 2023年2月2日
 */
public class SqlServerSQLDataTypeTransformUtil extends SQLTransformUtils {

    /**
     * SqlServer->MySql字段类型转换
     */
    public static SQLDataType transformSqlServerToMySql(SQLDataType x) {
        final String name = x.getName();
        final long nameHash = x.nameHashCode64();
        if (name == null) {
            return x;
        }
        List<SQLExpr> argumentns = x.getArguments();
        SQLDataType dataType;
        if (nameHash == FnvHash.Constants.SMALLINT) {
            int precision = 0;
            if (!argumentns.isEmpty()) {
                precision = ((SQLIntegerExpr) argumentns.get(0)).getNumber().intValue();
            }
            dataType = new SQLDataTypeImpl("int", precision);

        } else if (nameHash == FnvHash.Constants.TIME) {
            dataType = new SQLDataTypeImpl("datetime");

        } else if (nameHash == FnvHash.Constants.TEXT) {
            dataType = new SQLDataTypeImpl("MEDIUMTEXT");

        } else if(nameHash == FnvHash.Constants.NVARCHAR){
            int precision = 0;
            if (!argumentns.isEmpty()) {
                precision = ((SQLIntegerExpr) argumentns.get(0)).getNumber().intValue();
            }
            dataType = new SQLDataTypeImpl("varchar", precision);
        } else {
            dataType = x;
        }

        if (dataType != x) {
            dataType.setParent(x.getParent());
        }

        return dataType;
    }

}
