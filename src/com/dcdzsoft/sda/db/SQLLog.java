package com.dcdzsoft.sda.db;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: 杭州东城电子有限公司</p>
 *
 * @author wangzl
 * @version 1.0
 */
public class SQLLog {
    protected SQLLog() {
    }

    public static void logInsert(Log log,Object dto){
        if(log.isDebugEnabled())
            log.debug("[insert sql]" + dto.toString());
    }

    public static void logUpdate(Log log,String tableName,JDBCFieldArray setCols,JDBCFieldArray whereCols)
    {
        if(log.isDebugEnabled()){
            String sql = "UPDATE " + tableName + " SET ";
            sql = sql + setCols.toLogSetSQL();
            if (whereCols != null)
                sql = sql + " WHERE 1=1 " + whereCols.toLogWhereSQL();

            log.debug("[update sql]" + sql);
        }
    }

    public static void logDelete(Log log,String tableName,JDBCFieldArray whereCols)
    {
        if(log.isDebugEnabled()){
            String sql = "DELETE FROM " + tableName + " WHERE 1=1 ";
            if (whereCols != null)
                sql = whereCols.toLogWhereSQL(sql);

            log.debug("[delete sql]" + sql);
        }
    }

    public static void logIsExist(Log log,String tableName,JDBCFieldArray whereCols)
    {
        if(log.isDebugEnabled()){
            String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE 1=1 ";
            if (whereCols != null)
                sql = whereCols.toLogWhereSQL(sql);

            log.debug("[isExist sql]" + sql);
        }
    }

    public static void logSelectRowSet(Log log,String sql,JDBCFieldArray whereCols)
    {
        if(log.isDebugEnabled()){
            if (whereCols != null)
                sql = whereCols.toLogWhereSQL(sql);

            log.debug("[RowSet sql]" + sql);
        }
    }

    public static void logSelectFunction(Log log, String sql,JDBCFieldArray whereCols) {
        if (log.isDebugEnabled()) {
            if (whereCols != null)
                sql = whereCols.toLogWhereSQL(sql);

            log.debug("[selectFunctions sql]" + sql);
        }
    }
}
