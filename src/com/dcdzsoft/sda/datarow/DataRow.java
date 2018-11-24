package com.dcdzsoft.sda.datarow;

import java.sql.Types;
import java.util.ArrayList;

import org.apache.commons.collections.FastArrayList;

/**
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: 杭州东城电子有限公司</p>
 *
 * @author wangzl
 * @version 1.0
 */
public class DataRow
{
    public final static int INT_TYPE = 1;
    public final static int STRING_TYPE = 2;
    public final static int DOUBLE_TYPE = 3;

    //行信息
    private FastArrayList rowList = new FastArrayList();

    //列信息(保存原数据)
    private FastArrayList colList = new FastArrayList();

    public DataRow()
    {
        rowList.setFast(true);
        colList.setFast(true);
    }

    public void addStrColumn(String name,int len)
    {
        ColField col = new ColField();

        col.setName(name);
        col.setType(Types.VARCHAR);
        col.setPrecision(len);
        col.setScale(0);

        colList.add(col);
    }

    public void addIntColumn(String name)
    {
        ColField col = new ColField();

        col.setName(name);
        col.setType(Types.INTEGER);
        col.setPrecision(4);
        col.setScale(0);

        colList.add(col);
    }

    public void addDoubleColumn(String name)
    {
        ColField col = new ColField();

        col.setName(name);
        col.setType(Types.DOUBLE);
        col.setPrecision(8);
        col.setScale(3);

        colList.add(col);
    }

    public void addRow(ColFieldArray row)
    {
        rowList.add(row);
    }

    public int getColumnCount()
    {
        return colList.size();
    }

    public ArrayList getColList()
    {
        return colList;
    }

    public ArrayList getRowList()
    {
        return rowList;
    }

    protected void finalize()
    {
        rowList = null;
        colList = null;
    }
}
