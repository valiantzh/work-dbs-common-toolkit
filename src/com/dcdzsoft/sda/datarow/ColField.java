package com.dcdzsoft.sda.datarow;

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
public class ColField
{
    public ColField()
    {
    }

    public String name;
    public Object value;

    public int precision;
    public int scale;
    public int type;

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
    public int getPrecision()
    {
        return precision;
    }
    public int getScale()
    {
        return scale;
    }
    public void setScale(int scale)
    {
        this.scale = scale;
    }
    public void setPrecision(int precision)
    {
        this.precision = precision;
    }
}
