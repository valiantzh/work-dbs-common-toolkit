package com.dcdzsoft.dao.common;

import java.util.*;

import com.dcdzsoft.EduException;

public interface UtilDAO
{
    public java.sql.Date getCurrentDate() throws EduException;
    public java.sql.Time getCurrentTime() throws EduException;
    public java.sql.Timestamp getCurrentDateTime() throws EduException;

    /**
     * 构造查询从第N条到第M条的语句
     * @param querySQL String
     * @param orderByField List
     * @param recordBegin int
     * @param recordNum int
     * @return String
     */
    public String constructNMSql(String querySQL, List orderByField,
                                 int recordBegin, int recordNum);

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
                                 int recordNum);


    /**
     * 构造取子串的语句；beginIdex从1开始
     * <p>
     * Examples:
     * <blockquote><pre>
     * "wangzl".substring(1,3) returns "wan"
     * </pre></blockquote>
     * @param colName String
     * @param beginIndex int the beginning index, inclusive.
     * @param len the len of substring.
     * @return String
     */
    public String getSubstringSQL(String colName, int beginIndex, int len);

    /**
     * 构造charIndex语句
     * @param colName String
     * @param str String
     * @return String
     */
    public String getCharIndexSQL(String colName, String str);

    /**
     * 构造charIndex语句,在colName左右加上","
     * @param colName String
     * @param str String
     * @return String
     */
    public String getCharIndexFlagSQL(String str, String colName);

    /**
    * 构造InSQL语句
    * @param colName String
    * @param str String
    * @return String
    */
   public String getFlagInSQL(String colName,String str);

   /**
   * 构造InSQL语句
   * @param colName String
   * @param str String
   * @return String
   */
  public String getFlagInClearSQL(String colName,String str);


   /**
   * 构造InSQL语句
   * @param colName String
   * @param str String
   * @return String
   */
  public String getFlagNotInSQL(String colName,String str);


   /**
    * 构造InSQL语句
    * @param colName String
    * @param str String
    * @return String
    */
   public String getFlagInSQL2Num(String colName,String str);

   /**
    * 获得删除某个表数据的SQL语句
    * @param tableName String
    * @return String
    */
    public String getDeleteSQL(String tableName);


    /**
     * 获得NULL的语句
     * @param columnName String
     * @return String
     */
    public String getNullSQL(String columnName);

    /**
     * 获得NOT NULL的语句
     * @param columnName String
     * @return String
     */
    public String getNotNullSQL(String columnName);

    /**
     * 获得空值替换语句(数字型)
     * @param columnName String
     * @param repaceValue String
     * @return String
     */
    public String nvlNumSQL(String columnName,String repaceValue);

    /**
     * 获得空值替换语句(字符型)
     * @param columnName String
     * @param repaceValue String
     * @return String
     */
    public String nvlStrSQL(String columnName,String repaceValue);

    /**
     *  获得限制sql语句
     * @param DepartmentID String
     * @return String
     */
    public String getLimitSQL(String DepartmentID);

    /**
     * 获得转换大写的SQL函数语句
     * @param columnName String
     * @return String
     */
    public String getUpperSQL(String columnName);

    /**
     * 获得to_date语句
     * @param date Date
     * @return String
     */
    public String getDateSQL(java.sql.Date date);

    /**
     * 获得to_timestamp语句
     * @param timestamp Timestamp
     * @return String
     */
    public String getTimestampSQL(java.sql.Timestamp timestamp);
}
