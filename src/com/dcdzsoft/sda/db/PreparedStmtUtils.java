package com.dcdzsoft.sda.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.sql.Types;
import java.util.TimeZone;

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
public class PreparedStmtUtils {
    protected PreparedStmtUtils() {
    }

    public static void setDate(PreparedStatement pstmt,int parameterIndex, java.util.Date x)
	    throws SQLException
    {
        if(x == null)
            pstmt.setNull(parameterIndex,Types.DATE);
        else{
            java.sql.Date date = new java.sql.Date(x.getTime());
            //pstmt.setDate(parameterIndex,date,getUTCCal());
            pstmt.setDate(parameterIndex,date);
        }
    }

    public static void setDate(PreparedStatement pstmt,int parameterIndex, java.sql.Date x)
           throws SQLException
   {
       if(x == null)
           pstmt.setNull(parameterIndex,Types.DATE);
       else
           //pstmt.setDate(parameterIndex,x,getUTCCal());
           pstmt.setDate(parameterIndex,x);
   }


    public static void setTime(PreparedStatement pstmt,int parameterIndex, java.util.Date x) throws SQLException
    {
        if (x == null)
           pstmt.setNull(parameterIndex, Types.TIME);
       else {
           java.sql.Time time = new java.sql.Time(x.getTime());
           //pstmt.setTime(parameterIndex, time, getUTCCal());
           pstmt.setTime(parameterIndex, time);
       }
    }

    public static void setTime(PreparedStatement pstmt,int parameterIndex, java.sql.Time x) throws SQLException
    {
        if (x == null)
           pstmt.setNull(parameterIndex, Types.TIME);
       else
           //pstmt.setTime(parameterIndex, x, getUTCCal());
           pstmt.setTime(parameterIndex, x);
    }


    public static void setTimestamp(PreparedStatement pstmt,int parameterIndex, java.util.Date x)
	    throws SQLException
    {
        if (x == null)
            pstmt.setNull(parameterIndex, Types.TIMESTAMP);
        else {
            java.sql.Timestamp timestamp = new java.sql.Timestamp(x.getTime());
            //pstmt.setTimestamp(parameterIndex, timestamp, getUTCCal());
            pstmt.setTimestamp(parameterIndex, timestamp);
        }
    }

    public static void setTimestamp(PreparedStatement pstmt,int parameterIndex, java.sql.Timestamp x)
            throws SQLException
    {
        if (x == null)
            pstmt.setNull(parameterIndex, Types.TIMESTAMP);
        else
            //pstmt.setTimestamp(parameterIndex, x, getUTCCal());
            pstmt.setTimestamp(parameterIndex, x);
    }


    public static void setString(PreparedStatement pstmt,int parameterIndex, String x) throws SQLException
    {
        if(x == null)
            pstmt.setNull(parameterIndex,Types.VARCHAR);
        else{
            pstmt.setString(parameterIndex,x);
        }
    }

    public static void setInt(PreparedStatement pstmt,int parameterIndex, int x) throws SQLException
    {
        pstmt.setInt(parameterIndex,x);
    }

    public static void setLong(PreparedStatement pstmt,int parameterIndex, long x) throws SQLException
   {
       pstmt.setLong(parameterIndex,x);
   }

    public static void setDouble(PreparedStatement pstmt,int parameterIndex, double x) throws SQLException
    {
        pstmt.setDouble(parameterIndex,x);
    }

    public static void setFloat(PreparedStatement pstmt,int parameterIndex, float x) throws SQLException
    {
        pstmt.setFloat(parameterIndex,x);
    }

    public static void setObject(PreparedStatement pstmt,int parameterIndex,JDBCField f) throws SQLException
    {
        if (f.name != null) {
            if (f.type == JDBCField.STRING_TYPE)
                setString(pstmt,parameterIndex, f.getStringValue());
            else if (f.type == JDBCField.INT_TYPE)
                setInt(pstmt,parameterIndex, f.getIntValue());
            else if (f.type == JDBCField.LONG_TYPE)
                setLong(pstmt,parameterIndex, f.getLongValue());
            else if (f.type == JDBCField.DOUBLE_TYPE)
                setDouble(pstmt,parameterIndex, f.getDoubleValue());
            else if (f.type == JDBCField.DATE_TYPE)
                setDate(pstmt,parameterIndex, f.getDateValue());
            else if (f.type == JDBCField.TIMESTAMP_TYPE)
                setTimestamp(pstmt,parameterIndex, f.getDateTimeValue());
        }
    }
}
