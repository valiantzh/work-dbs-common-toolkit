package com.dcdzsoft.sda.db;

import java.sql.SQLException;
import java.sql.ResultSet;
import javax.sql.RowSet;
import java.io.*;

import org.apache.commons.logging.Log;

import com.dcdzsoft.EduException;
import com.dcdzsoft.util.DateUtils;
import java.sql.ResultSetMetaData;

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
public class RowSetUtils
{
    private static Log log = org.apache.commons.logging.LogFactory.getLog(
        RowSetUtils.class); //日志记录器
    private RowSetUtils()
    {}

    /**
     *Retrieves the count of  RowSet..
     * @param rowset RowSet
     * @return int
     * @throws EduException
     */
    public static int getCountOfRowSet(RowSet rowset)
        throws EduException
    {
        int count = 0;
        try
        {
            rowset.beforeFirst();
            if (rowset.next())
            {
                rowset.last();
                count = rowset.getRow();
            }

            rowset.beforeFirst();
        }
        catch (Exception e)
        {
            e.printStackTrace();

            String errorCode = EduException.EXCP_TRAVERSEROWSETFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[获得RowSet记录数出错]:" +
                      e.getMessage());
            throw new EduException(errorCode);
        }
        return count;
    }

    /**
     * Moves the cursor down one row from its current position
     * @param rset RowSet
     * @return boolean
     * @throws EduException
     */
    public static boolean rowsetNext(RowSet rset)
        throws EduException
    {
        boolean b = false;
        try
        {
            b = rset.next();
        }
        catch (SQLException e)
        {
            String errorCode = EduException.EXCP_TRAVERSEROWSETFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + "[errmsg]:" + e.getMessage());
            throw new EduException(errorCode);
        }
        return b;
    }

    /**
     * Moves the cursor to the front of this RowSet object, just before the first row
     * @param rset RowSet
     * @throws EduException
     */
    public static void rowsetBeforeFirst(RowSet rset)
        throws EduException
    {
        try
        {
            rset.beforeFirst();
        }
        catch (SQLException e)
        {
            String errorCode = EduException.EXCP_TRAVERSEROWSETFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + "[errmsg]:" + e.getMessage());
            throw new EduException(errorCode);
        }
    }

    /**
     * Retrieves the value of the designated column in the current row of this RowSet object as *an int in the Java programming language
     * @param rset RowSet
     * @param colName String
     * @return int
     * @throws EduException
     */
    public static int getIntValue(RowSet rset, String colName)
        throws EduException
    {
        //return getIntValue(rset,getColIdxByName(rset,colName));
        int value = 0;
        try {
            value = rset.getInt(colName);
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_TRAVERSEROWSETFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + "[errmsg]:" + e.getMessage());
            throw new EduException(errorCode);
        }
        return value;

    }

    /**
     * Retrieves the value of the designated column in the current row of this RowSet object as *an int in the Java programming language
     * @param rset RowSet
     * @param index int
     * @return int
     * @throws EduException
     */
    public static int getIntValue(RowSet rset, int index)
        throws EduException
    {
        int value = 0;
        try
        {
            value = rset.getInt(index);
        }
        catch (SQLException e)
        {
            String errorCode = EduException.EXCP_TRAVERSEROWSETFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + "[errmsg]:" + e.getMessage());
            throw new EduException(errorCode);
        }
        return value;
    }

    public static long getLongValue(RowSet rset, String colName)
        throws EduException
    {
        long value = 0L;
        try
        {
            value = rset.getLong(colName);
        }
        catch (SQLException e)
        {
            String errorCode = EduException.EXCP_TRAVERSEROWSETFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + "[errmsg]:" + e.getMessage());
            throw new EduException(errorCode);
        }
        return value;
    }

    public static long getLongValue(RowSet rset, int index)
        throws EduException
    {
        long value = 0L;
        try
        {
            value = rset.getLong(index);
        }
        catch (SQLException e)
        {
            String errorCode = EduException.EXCP_TRAVERSEROWSETFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + "[errmsg]:" + e.getMessage());
            throw new EduException(errorCode);
        }
        return value;
    }



    /**
     * Retrieves the value of the designated column in the current row of this RowSet object as *an double in the Java programming language
     * @param rset RowSet
     * @param colName String ：the SQL name of the column
     * @return double ：the column value; if the value is SQL NULL, the value returned is 0
     * @throws EduException
     */
    public static double getDoubleValue(RowSet rset, String colName)
        throws EduException
    {
        double value = 0.0D;
        try
        {
            value = rset.getDouble(colName);
        }
        catch (SQLException e)
        {
            String errorCode = EduException.EXCP_TRAVERSEROWSETFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + "[errmsg]:" + e.getMessage());
            throw new EduException(errorCode);
        }
        return value;
    }

    /**
     * Retrieves the value of the designated column in the current row of this RowSet object as *an int in the Java programming language
     * @param rset RowSet
     * @param index int：the first column is 1, the second is 2, ...
     * @return double ：the column value; if the value is SQL NULL, the value returned is 0
     * @throws EduException
     */
    public static double getDoubleValue(RowSet rset, int index)
        throws EduException
    {
        double value = 0;
        try
        {
            value = rset.getDouble(index);
        }
        catch (SQLException e)
        {
            String errorCode = EduException.EXCP_TRAVERSEROWSETFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + "[errmsg]:" + e.getMessage());
            throw new EduException(errorCode);
        }
        return value;
    }

    /**
     * Retrieves the value of the designated column in the current row of this RowSet object as *an double in the Java programming language
     * @param rset RowSet
     * @param colName String ：the SQL name of the column
     * @return String ：the column value; if the value is SQL NULL, the value returned is 0
     * @throws EduException
     */
    public static String getStringValue(RowSet rset, String colName)
        throws EduException
    {
        String value = "";
        try
        {
            value = rset.getString(colName);
            if (value == null)
                value = "";
        }
        catch (SQLException e)
        {
            String errorCode = EduException.EXCP_TRAVERSEROWSETFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + "[errmsg]:" + e.getMessage());
            throw new EduException(errorCode);
        }
        return value;
    }

    /**
     * Retrieves the value of the designated column in the current row of this RowSet object as *an int in the Java programming language
     * @param rset RowSet
     * @param index int：the first column is 1, the second is 2, ...
     * @return String ：the column value; if the value is SQL NULL, the value returned is 0
     * @throws EduException
     */
    public static String getStringValue(RowSet rset, int index)
        throws EduException
    {
        String value = "";
        try
        {
            value = rset.getString(index);
            if (value == null)
                value = "";
        }
        catch (SQLException e)
        {
            String errorCode = EduException.EXCP_TRAVERSEROWSETFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + "[errmsg]:" + e.getMessage());
            throw new EduException(errorCode);
        }
        return value;
    }

    public static java.sql.Date getDateValue(RowSet rset, int index) throws
            EduException {
        java.sql.Date value = null;

        try {
            java.lang.Object tmpObj = rset.getObject(index);
            if (tmpObj instanceof oracle.sql.DATE)
                value = ((oracle.sql.DATE) tmpObj).dateValue();
            else
                value = (java.sql.Date) tmpObj;

        } catch (SQLException e) {
            String errorCode = EduException.EXCP_TRAVERSEROWSETFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + "[errmsg]:" + e.getMessage());
            throw new EduException(errorCode);
        }

        return value;
    }

    public static java.sql.Date getDateValue(RowSet rset, String colName) throws
            EduException {
        java.sql.Date value = null;

        try {
            java.lang.Object tmpObj = rset.getObject(colName);

            if(tmpObj instanceof java.sql.Timestamp){
                value = DateUtils.toSQLDate((java.sql.Timestamp)tmpObj);
            }else{
                if (tmpObj instanceof oracle.sql.TIMESTAMP)
                    value = DateUtils.toSQLDate(((oracle.sql.TIMESTAMP) tmpObj).timestampValue());
                else if (tmpObj instanceof oracle.sql.DATE)
                    value = ((oracle.sql.DATE) tmpObj).dateValue();
                else
                    value = (java.sql.Date) tmpObj;
            }
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_TRAVERSEROWSETFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + "[errmsg]:" + e.getMessage());
            throw new EduException(errorCode);
        }

        return value;
    }

    public static java.sql.Timestamp getTimestampValue(RowSet rset, int index) throws
            EduException {
        java.sql.Timestamp value = null;

        try {
            if(DataSourceUtils.DB_TYPE_ORACLE == DataSourceUtils.getInstance().getDatabaseType()){
               java.lang.Object tmpObj = rset.getObject(index);

                if (tmpObj instanceof oracle.sql.TIMESTAMP)
                    value = ((oracle.sql.TIMESTAMP) tmpObj).timestampValue();
                else
                    value = (java.sql.Timestamp) tmpObj;
            }
            else
                value = rset.getTimestamp(index);
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_TRAVERSEROWSETFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + "[errmsg]:" + e.getMessage());
            throw new EduException(errorCode);
        }

        return value;
    }

    public static java.sql.Timestamp getTimestampValue(RowSet rset, String colName) throws
            EduException {
        java.sql.Timestamp value = null;

        try {
            if(DataSourceUtils.DB_TYPE_ORACLE == DataSourceUtils.getInstance().getDatabaseType()){
                java.lang.Object tmpObj = rset.getObject(colName);
                if (tmpObj instanceof oracle.sql.TIMESTAMP)
                    value = ((oracle.sql.TIMESTAMP) tmpObj).timestampValue();
                else
                    value = (java.sql.Timestamp) tmpObj;
            }
            else
                value = rset.getTimestamp(colName);


            //value = rset.getTimestamp(colName, DateUtils.getUTCCal());
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_TRAVERSEROWSETFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + "[errmsg]:" + e.getMessage());
            throw new EduException(errorCode);
        }

        return value;
    }

    public static String getLongString(ResultSet rs, int index)
        throws EduException
    {
        String value = null;

        try
        {
            java.io.Reader textData = rs.getCharacterStream(index);
            value = getLongString(textData);
        }
        catch (SQLException e)
        {
            log.error("[get long string error]:" + e.getMessage());
            throw new EduException(EduException.EXCP_GETTEXTDATAFAIL);
        }

        return value;

    }

    public static String getLongString(ResultSet rs, String columnname)
        throws EduException
    {
        String value = null;

        try
        {
            java.io.Reader textData = rs.getCharacterStream(columnname);
            value = getLongString(textData);
        }
        catch (SQLException e)
        {
            log.error("[get long string error]:" + e.getMessage());
            throw new EduException(EduException.EXCP_GETTEXTDATAFAIL);
        }

        return value;
    }

    private static String getLongString(Reader textData)
        throws SQLException
    {
        String value = null;

        if (textData != null)
        {
            try
            {
                // Use a modest buffer here to reduce function call overhead
                // when reading extremely large data.
                StringBuffer textBuffer = new StringBuffer(1000);
                char[] tmpBuffer = new char[1000];
                int charsRead = 0;

                while ( (charsRead = textData.read(tmpBuffer)) != -1)
                    textBuffer.append(tmpBuffer, 0, charsRead);

                value = textBuffer.toString();
            }
            catch (java.io.IOException e)
            {
                throw new SQLException(e.getMessage());
            }
            finally
            {
                try
                {
                    if (textData != null)
                        textData.close();
                }
                catch (java.io.IOException e)
                {
                    throw new SQLException(e.getMessage());
                }
            }
        }

        return value;

    }

    public static byte[] getByteArray(InputStream input)
        throws SQLException
    {
        if (input == null)
            return null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try
        {
            // Use a modest buffer here to reduce function call overhead
            // when reading extremely large data.
            byte[] tmpBuffer = new byte[1000];
            int bytesRead = 0;

            while ( (bytesRead = input.read(tmpBuffer)) != -1)
                baos.write(tmpBuffer, 0, bytesRead);

            return baos.toByteArray();
        }
        catch (java.io.IOException ioException)
        {
            throw new SQLException(ioException.getMessage());
        }
        finally
        {
            try
            {
                if (baos != null)
                    baos.close();
            }
            catch (java.io.IOException e)
            {
                throw new SQLException(e.getMessage());
            }
        }
    }

    private static int getColIdxByName(RowSet rset, String name) throws
            EduException {
        ResultSetMetaData metaData = null;
        try
        {
            metaData = rset.getMetaData();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                String colName = metaData.getColumnName(i + 1);

                if (colName != null)
                    if (name.equalsIgnoreCase(colName))
                        return (i);
                    else
                        continue;
            }
        }catch(java.sql.SQLException e){
            String errorCode = EduException.EXCP_TRAVERSEROWSETFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + "[errmsg]:" + e.getMessage());
            throw new EduException(errorCode);
        }

        log.error("invalid column");
        throw new EduException(EduException.EXCP_TRAVERSEROWSETFAIL);
    }


    /**
     * 关闭结果集
     * @param rset ResultSet
     * @throws EduException
     */
    public static void closeResultSet(ResultSet rset)
        throws EduException
    {
        try
        {
            if (rset != null)
                rset.close();
        }
        catch (SQLException e)
        {
            String errorCode = EduException.EXCP_SQLERR;
            log.error("[DBERROR-(" + errorCode + ")]==>close ResultSet fail:" +
                      e.getMessage());
            throw new EduException(errorCode);
        }
    }
}
