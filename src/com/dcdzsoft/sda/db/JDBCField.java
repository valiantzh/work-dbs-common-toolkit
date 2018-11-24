package com.dcdzsoft.sda.db;

import java.text.DecimalFormat;

import com.dcdzsoft.sda.db.support.CommonUtils;

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
public class JDBCField
{
    public final static int INT_TYPE = 1;
    public final static int LONG_TYPE = 2;
    public final static int DOUBLE_TYPE = 3;
    public final static int STRING_TYPE = 4;
    public final static int DATE_TYPE = 5;
    public final static int TIMESTAMP_TYPE = 6;

    public String name;
    public Object value;
    public String operator = " = ";
    public int type = STRING_TYPE;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public String getStringValue() {
        String result = "";
        if (value != null)
            result = (String) value;

        return result;
    }

    public int getIntValue(){
        int result = 0;

        if(value != null){
            result = ((Integer)value).intValue();
        }

        return result;
    }

    public long getLongValue(){
        long result = 0L;
        if(value != null){
            result = ((Long)value).longValue();
        }

        return result;
    }

    public double getDoubleValue(){
        double result = 0.0D;

        if(value != null){
            result = ((Double)value).doubleValue();
        }

        return result;
    }

    public java.sql.Date getDateValue(){
        java.sql.Date result = null;

        if(value != null){
            result = (java.sql.Date)value;
        }

        return result;
    }

    public java.sql.Timestamp getDateTimeValue(){
        java.sql.Timestamp result = null;

        if(value != null){
            result = (java.sql.Timestamp)value;
        }

        return result;
    }


    public String quoteVal()
    {
        String str = "";

        if (value != null)
        {
            if (value instanceof String){
                str = CommonUtils.addQuote( (String) value);
            }else if (value instanceof Double){
                str = CommonUtils.formatDouble(((Double)value).doubleValue());
            }else if (value instanceof java.sql.Date){
                str = CommonUtils.formatDate((java.sql.Date)value);
            }else if (value instanceof java.sql.Timestamp){
                str = CommonUtils.formatTimestamp((java.sql.Timestamp)value);
            }else
            {
                str = value.toString();
            }
        }

        return str;
    }
}
