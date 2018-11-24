package com.dcdzsoft.sda.db;

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
public class ProcedureParam
{
    private Object value;
    private int jdbcType;
    private int index;


    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public int getJdbcType()
    {
        return jdbcType;
    }

    public void setJdbcType(int jdbcType)
    {
        this.jdbcType = jdbcType;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }
}
