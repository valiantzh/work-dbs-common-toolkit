package com.dcdzsoft.sda.db.support;

import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;

import com.dcdzsoft.sda.db.DBSession;
import com.dcdzsoft.sda.db.LocalSessionHolder;
import com.dcdzsoft.sda.db.DataSourceUtils;
import com.dcdzsoft.util.DateUtils;

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
public final class CommonUtils
{
    /**
     * 格式化double输出,保留3位小数点
     * @param value double
     * @return String
     */
    public static String formatDouble(double value)
    {
        String str = "0.0";

        if (value != 0.00D)
        {
            //不支持国际化;支持国际化用NumberFormat
            //DecimalFormat nf = new DecimalFormat("#.00");
            DecimalFormat nf = new DecimalFormat("#.###");

            nf.setParseIntegerOnly(false);
            nf.setDecimalSeparatorAlwaysShown(false);

            str = nf.format(value);
        }

        return str;
    }

    public static String formatDate(java.sql.Date date){
    	int whichFactory = DataSourceUtils.getInstance().getDatabaseType();
    	switch (whichFactory)
		{
			case DataSourceUtils.DB_TYPE_ORACLE:
				return getDateSQL4Oracle(date);
			case DataSourceUtils.DB_TYPE_MYSQL:
				return getDateSQL4Mysql(date);
			default:
				return "";
		}
    }

    public static String formatTimestamp(java.sql.Timestamp timestamp){
    	int whichFactory = DataSourceUtils.getInstance().getDatabaseType();
    	switch (whichFactory)
		{
			case DataSourceUtils.DB_TYPE_ORACLE:
				return getTimestampSQL4Oracle(timestamp);
			case DataSourceUtils.DB_TYPE_MYSQL:
				return getTimestampSQL4Mysql(timestamp);
			default:
				return "";
		}
   }

    /**
     * pad String with quote
     *
     * StringUtils.addQuote(“wangzl”)  = ’wangzl’
     * @param str String
     * @return String
     */
    public static String addQuote(String str)
    {
        str = "'" + encodeSingleQuotedString(str) + "'";

        return str;
    }

    /**
     * left pad String with quote
     *
     * StringUtils.leftQuote(“wangzl”)  = "’wangzl"
     * @param str String
     * @return String
     */
    public static String leftQuote(String str)
    {
        str = "'" + encodeSingleQuotedString(str) + "";

        return str;
    }

    /**
     * right pad String with quote
     *
     * StringUtils.rightQuote(“wangzl”)  = "wangzl’"
     * @param str String
     * @return String
     */
    public static String rightQuote(String str)
    {
        str = "" + encodeSingleQuotedString(str) + "'";

        return str;
    }

    /**
     * right pad String with quote
     *
     * @param str String
     * @return String
     */
    public static String encodeSingleQuotedString(String str)
    {
        StringBuffer sb;
        char c;
        if (StringUtils.isNotEmpty(str))
        {
            sb = new StringBuffer(64);
            for (int i = 0; i < str.length(); i++)
            {
                c = str.charAt(i);
                if (c == '\'')
                    sb.append("\'\'");
                else
                    sb.append( (char) c);
            }
            return sb.toString();
        }

        return str;
    }

    public static boolean isNotEmpty(String str)
    {
        return StringUtils.isNotEmpty(str);
    }
    
    /**
     * 获得to_date语句
     * @param date Date
     * @return String
     */
    public static String getDateSQL4Mysql(java.sql.Date date) {
        String dateStr = DateUtils.dateToString(date);

        return "STR_TO_DATE('" + dateStr + "','%Y-%m-%d')";
    }

    /**
     * 获得to_timestamp语句
     * @param timestamp Timestamp
     * @return String
     */
    public static String getTimestampSQL4Mysql(java.sql.Timestamp timestamp) {
        String timestampStr = DateUtils.timestampToString(timestamp);

        return "STR_TO_DATE('" + timestampStr + "','%Y-%m-%d %H:%i%S')";
    }
    
    /**
     * 获得to_date语句
     * @param date Date
     * @return String
     */
    public static String getDateSQL4Oracle(java.sql.Date date) {
        String dateStr = DateUtils.dateToString(date);

        return "TO_DATE('" + dateStr + "','YYYY-MM-DD')";
    }

    /**
     * 获得to_timestamp语句
     * @param timestamp Timestamp
     * @return String
     */
    public static String getTimestampSQL4Oracle(java.sql.Timestamp timestamp) {
        String timestampStr = DateUtils.timestampToString(timestamp);

        return "TO_TIMESTAMP('" + timestampStr + "','YYYY-MM-DD HH24:MI:SSxFF')";
    }

}
