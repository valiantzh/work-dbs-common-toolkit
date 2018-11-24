package com.dcdzsoft.dao.common.impl.mssql;

import java.sql.*;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dcdzsoft.dao.common.*;
import com.dcdzsoft.sda.db.*;
import com.dcdzsoft.util.*;
import com.dcdzsoft.EduException;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author wangzl
 * @version 1.0
 */
public class MsSQLUtilDAO implements UtilDAO {
    private static final Log log = LogFactory.getLog(MsSQLUtilDAO.class.
            getName());

    private DBSession dbSession = LocalSessionHolder.getCurrentDBSession();
    private Connection conn = dbSession.getConnection();
    private double jetLag = dbSession.getJetLag();

    public java.sql.Date getCurrentDate() throws EduException {
        Statement stmt = null;
        ResultSet rset = null;

        java.sql.Date currentDate = null;

        String sql = "SELECT CONVERT(date,getdate(),112)";
        if(jetLag != 0.0){
            sql = "SELECT CONVERT(date,dateadd(hour,"+jetLag+",GETDATE()),112)";
        }

        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            if (rset.next() == true) {
                currentDate = rset.getDate(1);
            } else {
                throw new EduException(EduException.EXCP_GETCURRENTTIMEFAIL);
            }

        } catch (SQLException e) {
            log.error("[sqlcode]:" + e.getErrorCode() + " [errmsg]:" +
                      e.getMessage());
            throw new EduException(EduException.EXCP_GETCURRENTTIMEFAIL);
        } finally {
            dbSession.closeResultSet(rset);
            dbSession.closeStatement(stmt);
        }

        return currentDate;
    }

    public java.sql.Time getCurrentTime() throws EduException {
        Statement stmt = null;
        ResultSet rset = null;

        java.sql.Time currentTime = null;

        String sql = "SELECT CONVERT(time,getdate(),114)";
        if(jetLag!=0){
            sql = "SELECT CONVERT(time,dateadd(hour,"+jetLag+",GETDATE()),114)";
        }

        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            if (rset.next() == true) {
                currentTime = rset.getTime(1);
            } else {
                throw new EduException(EduException.EXCP_GETCURRENTTIMEFAIL);
            }

        } catch (SQLException e) {
            log.error("[sqlcode]:" + e.getErrorCode() + " [errmsg]:" +
                      e.getMessage());
            throw new EduException(EduException.EXCP_GETCURRENTTIMEFAIL);
        } finally {
            dbSession.closeResultSet(rset);
            dbSession.closeStatement(stmt);
        }

        return currentTime;
    }

    public java.sql.Timestamp getCurrentDateTime() throws EduException {
        Statement stmt = null;
        ResultSet rset = null;

        java.sql.Timestamp currentDateTime = null;

        String sql = "SELECT CONVERT(datetime,getdate(),121)";
        if(jetLag!=0.0){
            sql = "SELECT CONVERT(datetime,dateadd(hour,"+jetLag+",GETDATE()),121)";
        }

        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            if (rset.next() == true) {
                //currentDateTime = rset.getTimestamp(1,DateUtils.getUTCCal());
                currentDateTime = rset.getTimestamp(1);
            } else {
                throw new EduException(EduException.EXCP_GETCURRENTTIMEFAIL);
            }

        } catch (SQLException e) {
            log.error("[sqlcode]:" + e.getErrorCode() + " [errmsg]:" +
                      e.getMessage());
            throw new EduException(EduException.EXCP_GETCURRENTTIMEFAIL);
        } finally {
            dbSession.closeResultSet(rset);
            dbSession.closeStatement(stmt);
        }

        return currentDateTime;
    }

    /**
     * 构造查询从第N条到第M条的语句
     * @param querySQL String
     * @param orderByField List
     * @param recordBegin int
     * @param recordNum int
     * @return String
     */
    public String constructNMSql(String querySQL, List orderByField,
                                 int recordBegin, int recordNum) {
    	/*String sql = "";
        StringBuffer sbField = new StringBuffer();
        StringBuffer sbFieldA = new StringBuffer();
        StringBuffer sbFieldB = new StringBuffer();

        int recordEnd = 0;

        int count = 0;
        for (Iterator itr = orderByField.listIterator(); itr.hasNext(); )
        {
            count++;

            String fieldName = (String) itr.next();
            if (count > 1)
            {
                sbField.append(",");
                sbFieldA.append(",");
                sbFieldB.append(",");
            }

            String upperFieldName = fieldName.toUpperCase();
            if(upperFieldName.indexOf("ASC") != -1)
            {
            	fieldName = fieldName.replaceAll("ASC", "");
            	
	        	sbField.append(fieldName);
	            sbFieldA.append("A." + fieldName);
	            sbFieldB.append("B." + fieldName + " ASC ");
            }else if(upperFieldName.indexOf("DESC") != -1){
            	fieldName = fieldName.replaceAll("DESC", "");
            	
	        	sbField.append(fieldName);
	            sbFieldA.append("A." + fieldName);
	            sbFieldB.append("B." + fieldName + " DESC ");
            }else{
            	 sbField.append(fieldName);
                 sbFieldA.append("A." + fieldName);
                 sbFieldB.append("B." + fieldName + " DESC ");
            }
        }

        if (recordBegin == 0 && recordNum == 0)
        {
            querySQL = querySQL + " ORDER BY " + sbField.toString();
            return querySQL;
        }

        if (recordBegin == 0)
            recordBegin = 1;

        recordEnd = recordBegin + recordNum - 1;

        String orderBy = " ORDER BY " + sbField.toString() +  " ASC)A ORDER BY "
            + sbFieldA.toString() + " DESC)B ORDER BY " + sbFieldB.toString();

        String header = "SELECT * FROM (SELECT TOP " + recordNum + " * FROM (SELECT TOP " + recordEnd + " ";

        sql = header + querySQL.trim().substring(6) + orderBy;

        return sql;*/
    	
    	String sql = "";
        StringBuffer sbField = new StringBuffer();

        int recordEnd = 0;

        int count = 0;
        for (Iterator itr = orderByField.listIterator(); itr.hasNext(); ) {
            count++;

            String fieldName = (String) itr.next();
            if (count > 1) {
                sbField.append(",");
            }

            sbField.append(fieldName);
        }

        if ((recordBegin == 0 || recordBegin == 1) && recordNum == 0) {
            querySQL = querySQL + " ORDER BY " + sbField.toString();
            return querySQL;
        }

        if (recordBegin == 0)
            recordBegin = 1;

        recordEnd = recordBegin + recordNum - 1;

        String orderBy = " ORDER BY " + sbField.toString();

        sql = "SELECT t.* FROM (SELECT row_number() over(" + orderBy + ") AS rownum, " + querySQL.trim().substring(6) + ") t ";
        sql += " WHERE t.rownum <=" + recordEnd + " AND t.rownum >= " + recordBegin;

        return sql;
    }

    /**
     * 构造查询从第N条到第M条的语句
     * @param querySQL String
     * @param orderByField List
     * @param tablePrefix String
     * @param recordBegin int
     * @param recordNum int
     * @return String
     */
    public String constructNMSql(String querySQL,
                                 List orderByField,
                                 String tablePrefix,
                                 int recordBegin,
                                 int recordNum) {
    	 /*String sql = "";
         StringBuffer sbField = new StringBuffer();
         StringBuffer sbFieldA = new StringBuffer();
         StringBuffer sbFieldB = new StringBuffer();

         int recordEnd = 0;

         int count = 0;
         for (Iterator itr = orderByField.listIterator(); itr.hasNext(); )
         {
             count++;

             String fieldName = (String) itr.next();
             if (count > 1)
             {
                 sbField.append(",");
                 sbFieldA.append(",");
                 sbFieldB.append(",");
             }
             
             String upperFieldName = fieldName.toUpperCase();
             if(upperFieldName.indexOf("ASC") != -1)
             {
             	fieldName = fieldName.replaceAll("ASC", "");
             	
             	sbField.append(tablePrefix + "." + fieldName);
 	            sbFieldA.append("A." + fieldName);
 	            sbFieldB.append("B." + fieldName + " ASC ");
             }else if(upperFieldName.indexOf("DESC") != -1){
             	fieldName = fieldName.replaceAll("DESC", "");
             	
             	sbField.append(tablePrefix + "." + fieldName);
 	            sbFieldA.append("A." + fieldName);
 	            sbFieldB.append("B." + fieldName + " DESC ");
             }else{
            	 sbField.append(tablePrefix + "." + fieldName);
                 sbFieldA.append("A." + fieldName);
                 sbFieldB.append("B." + fieldName  + " DESC ");
             }
         }

         if (recordBegin == 0 && recordNum == 0)
         {
             querySQL = querySQL + " ORDER BY " + sbField.toString();
             return querySQL;
         }

         if (recordBegin == 0)
             recordBegin = 1;

         recordEnd = recordBegin + recordNum - 1;

         String orderBy = " ORDER BY " + sbField.toString() + " ASC)A ORDER BY "
             + sbFieldA.toString() + " DESC)B ORDER BY " + sbFieldB.toString();

         String header = "SELECT * FROM (SELECT TOP " + recordNum + " * FROM (SELECT TOP " + recordEnd + " ";

         sql = header + querySQL.trim().substring(6) + orderBy;

         return sql;*/
    	
    	String sql = "";
        StringBuffer sbField = new StringBuffer();

        int recordEnd = 0;

        int count = 0;
        for (Iterator itr = orderByField.listIterator(); itr.hasNext(); ) {
            count++;

            String fieldName = (String) itr.next();
            if (count > 1) {
                sbField.append(",");
            }

            sbField.append(tablePrefix + "." + fieldName);
        }

        if ((recordBegin == 0 || recordBegin == 1) && recordNum == 0) {
            querySQL = querySQL + " ORDER BY " + sbField.toString();
            return querySQL;
        }

        if (recordBegin == 0)
            recordBegin = 1;

        recordEnd = recordBegin + recordNum - 1;

        String orderBy = " ORDER BY " + sbField.toString();

        sql = "SELECT t.* FROM (SELECT row_number() over(" + orderBy + ") AS rownum, " + querySQL.trim().substring(6) + ") t ";
        sql += " WHERE t.rownum <=" + recordEnd + " AND t.rownum >= " + recordBegin;

        return sql;
    }

    /**
     * 构造取子串的语句；beginIdex从1开始
     * <p>
     * Examples:
     * <blockquote><pre>
     * "wangzl".substring(1,3) returns "wan"
     * </pre></blockquote>
     * @param colName String
     * @param beginIndex int the beginning index, inclusive.
     * @param len the length of substring.
     * @return String
     */
    public String getSubstringSQL(String colName, int beginIndex, int len) {
    	String subSQL = "SUBSTRING(" + colName + ",";

        if (beginIndex == 0)
            beginIndex = 1;

        subSQL = subSQL + beginIndex + "," + len + ")";

        return subSQL;
    }

    /**
     * 构造charIndex语句；判断colName是否存在与str中
     * @param colName String
     * @param str String
     * @return String
     */
    public String getCharIndexSQL(String colName, String str) {
    	 if (str.startsWith(",") == false)
         {
             str = "," + str;
         }

         if (str.endsWith(",") == false)
         {
             str = str + ",";
         }

         String strCharIndex = "CHARINDEX(" + colName + "," + StringUtils.addQuote(str) + ")";

         return strCharIndex;
    }

    /**
     * 构造charIndex语句,在colName左右加上",",判断str是否存在与colName中
     * @param colName String
     * @param str String
     * @return String
     */
    public String getCharIndexFlagSQL(String str, String colName) {
        if (str.startsWith(",") == false)
        {
            str = "," + str;
        }

        if (str.endsWith(",") == false)
        {
            str = str + ",";
        }

        String strCharIndex = "CHARINDEX(" + StringUtils.addQuote(str) + "," + colName + ")";

        return strCharIndex;
    }

    /**
     * 构造InSQL语句
     * @param colName String
     * @param str String
     * @return String
     */
    public String getFlagInSQL(String colName, String str) {
        String inSQL = " AND " + getFlagInClearSQL(colName, str);

        return inSQL;
    }

    /**
     * 构造InSQL语句
     * @param colName String
     * @param str String
     * @return String
     */
    public String getFlagInClearSQL(String colName, String str) {
        String inSQL = "";
        StringBuffer sbFlag = new StringBuffer(256);
        String[] flags = null;
        if (str.indexOf(",") != -1)
            flags = str.split(",");
        else
            flags = str.split(";");

        int count = flags.length;
        for (int i = 0; i < count; i++) {
            if (i != 0) {
                sbFlag.append(",");
            }

            sbFlag.append(StringUtils.addQuote(flags[i]));
        }

        inSQL = colName + " IN(" + sbFlag.toString() + ") ";

        return inSQL;

    }

    /**
     * 构造NotInSQL语句
     * @param colName String
     * @param str String
     * @return String
     */
    public String getFlagNotInSQL(String colName, String str) {
        String notInSQL = "";
        StringBuffer sbFlag = new StringBuffer(256);

        String[] flags = null;
        if (str.indexOf(",") != -1)
            flags = str.split(",");
        else
            flags = str.split(";");

        int count = flags.length;
        for (int i = 0; i < count; i++) {
            if (i != 0) {
                sbFlag.append(",");
            }

            sbFlag.append(StringUtils.addQuote(flags[i]));
        }

        notInSQL = " AND " + colName + " NOT IN(" + sbFlag.toString() + ") ";

        return notInSQL;

    }


    /**
     * 构造InSQL语句
     * @param colName String
     * @param str String
     * @return String
     */
    public String getFlagInSQL2Num(String colName, String str) {
        String inSQL = "";
        StringBuffer sbFlag = new StringBuffer(256);

        String[] flags = null;
        if (str.indexOf(",") != -1)
            flags = str.split(",");
        else
            flags = str.split(";");

        int count = flags.length;
        for (int i = 0; i < count; i++) {
            if (i != 0) {
                sbFlag.append(",");
            }

            sbFlag.append(NumberUtils.parseInt(flags[i]));
        }

        inSQL = " AND " + colName + " IN(" + sbFlag.toString() + ") ";

        return inSQL;

    }

    /**
     * 获得删除某个表数据的SQL语句
     * @param tableName String
     * @return String
     */
    public String getDeleteSQL(String tableName) {
    	String sql = "DELETE " + tableName + " FROM " + tableName + " a ";

        return sql;
    }

    /**
     * 获得NULL的语句
     * @param columnName String
     * @return String
     */
    public String getNullSQL(String columnName) {
    	return " (" + columnName + " IS NULL OR " + columnName + " = '')";
    }

    /**
     * 获得NOT NULL的语句
     * @param columnName String
     * @return String
     */
    public String getNotNullSQL(String columnName) {
    	return " (" + columnName + " IS NOT NULL AND " + columnName + " <> '')";
    }

    /**
     * 获得空值替换语句(数字型)
     * @param columnName String
     * @param repaceValue String
     * @return String
     */
    public String nvlNumSQL(String columnName, String repaceValue) {
    	return " ISNULL(" + columnName + "," + repaceValue + ") ";
    }

    /**
     * 获得空值替换语句(字符型)
     * @param columnName String
     * @param repaceValue String
     * @return String
     */
    public String nvlStrSQL(String columnName, String repaceValue) {
    	return " ISNULL(" + columnName + ",'" + repaceValue + "') ";
    }


    /**
     * 获得to_date语句
     * @param date Date
     * @return String
     */
    public String getDateSQL(java.sql.Date date) {
        String dateStr = DateUtils.dateToString2(date);

        return "CONVERT(DATE,'" + dateStr + "',112)";
    }

    /**
     * 获得to_timestamp语句
     * @param timestamp Timestamp
     * @return String
     */
    public String getTimestampSQL(java.sql.Timestamp timestamp) {
        String timestampStr = DateUtils.timestampToString(timestamp);

        return "CONVERT(DATETIME,'" + timestampStr + "',120)";
    }

    /**
     * 获得转换大写的SQL函数语句
     * @param columnName String
     * @return String
     */
    public String getUpperSQL(String columnName) {
        return "UPPER(" + columnName + ")";
    }

    /**
     *  获得限制sql语句
     * @param DepartmentID String
     * @return String
     */
    public String getLimitSQL(String DepartmentID) {
        String limitSQL = "";

        limitSQL = " AND EXISTS(SELECT * FROM MBDepartment b WHERE b.DepartmentID = a.DepartmentID"
                   + " AND (b.Level1= " + StringUtils.addQuote(DepartmentID) + " OR"
                   + " b.Level2= " + StringUtils.addQuote(DepartmentID) + " OR"
                   + " b.Level3= " + StringUtils.addQuote(DepartmentID) + " OR"
                   + " b.Level4= " + StringUtils.addQuote(DepartmentID) + " OR"
                   + " b.Level5= " + StringUtils.addQuote(DepartmentID) + " OR"
                   + " b.Level6= " + StringUtils.addQuote(DepartmentID) + "))";

        return limitSQL;
    }


}
