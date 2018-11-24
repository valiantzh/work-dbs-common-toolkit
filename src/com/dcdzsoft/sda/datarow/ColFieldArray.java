package com.dcdzsoft.sda.datarow;

import org.apache.commons.collections.FastArrayList;

/**
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company:  杭州东城电子有限公司</p>
 *
 * @author wangzl
 * @version 1.0
 */
public class ColFieldArray
{
    //一个行中所有的列
    private FastArrayList colList = new FastArrayList();

    public ColFieldArray()
    {
        colList.setFast(true);
    }

    public void addCol(String name,int value)
    {
        ColField col = new ColField();
        col.setName(name);
        col.setValue(new Integer(value));

        colList.add(col);
    }

    public void addCol(String name,double value)
    {
        ColField col = new ColField();
        col.setName(name);
        col.setValue(new Double(value));

        colList.add(col);
    }

    public void addCol(String name,String value)
    {
        ColField col = new ColField();
        col.setName(name);
        col.setValue(value);

        colList.add(col);
    }
    public FastArrayList getColList()
    {
        return colList;
    }

    protected void finalize()
    {
        colList = null;
    }
}
