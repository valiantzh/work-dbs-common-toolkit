package com.dcdzsoft.sda.datarow;

import java.sql.SQLException;
import javax.sql.RowSet;
import javax.sql.RowSetReader;
import javax.sql.RowSetInternal;
import java.sql.ResultSetMetaData;
import javax.sql.RowSetMetaData;
import java.util.ArrayList;

import sun.jdbc.rowset.*;

import com.dcdzsoft.EduException;

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

public class DataRowToRowSet implements RowSetReader
{
    private DataRow dataRow = null;

    private CachedRowSet crs;
    private RowSetMetaDataImpl rsmdi;

    public DataRowToRowSet(DataRow dataRow)
    {
        this.dataRow = dataRow;
    }

    public static void main(String[] args) throws Exception
    {
        //ArrayToRowSet arrayToRowSet1 = new ArrayToRowSet();

        DataRow dRow = new DataRow();
        dRow.addIntColumn("TermID");
        dRow.addStrColumn("TermStr",60);

        for(int i = 0 ; i<8 ; i++)
        {
            ColFieldArray fArray = new ColFieldArray();
            fArray.addCol("TermID",i+1);
            fArray.addCol("TermStr","第"+(i+1)+"学期");
            dRow.addRow(fArray);
        }

        DataRowToRowSet at = new DataRowToRowSet(dRow);
        RowSet rs = at.arrayToRowSet();

        while(rs.next())
        {
            System.out.println("TermID:" + rs.getInt(1));
            System.out.println("TermStr:" + rs.getString(2));
        }

    }

    public RowSet arrayToRowSet() throws EduException
    {
        crs = null;
        try
        {
            crs = new CachedRowSet();
            crs.setReader(this);
            crs.execute(); //load from reader
        }
        catch(SQLException e)
        {
            throw new EduException("");
        }

        return crs;

    }

    /**
    * 实现RowSetReader接口的方法
    * @param caller RowSetInternal
    * @throws SQLException
    */
   public void readData(RowSetInternal caller) throws SQLException
   {
       rsmdi = null;
       rsmdi = new RowSetMetaDataImpl();

       int colCount = dataRow.getColumnCount();
       rsmdi.setColumnCount(colCount);

       ArrayList colList = dataRow.getColList();

       for (int j = 1; j <= colCount; j++)
       {
           ColField col = (ColField)colList.get(j-1);

           rsmdi.setAutoIncrement(j, false);
           rsmdi.setCaseSensitive(j, false);
           rsmdi.setCurrency(j, false);
           //rsmdi.setNullable(j, false);
           rsmdi.setSigned(j, false);
           rsmdi.setSearchable(j, false);
           //rsmdi.setColumnDisplaySize(j, resultsetmetadata.getColumnDisplaySize(j));
           //rsmdi.setColumnLabel(j, resultsetmetadata.getColumnLabel(j));
           rsmdi.setColumnName(j, col.getName());
           //rsmdi.setSchemaName(j, resultsetmetadata.getSchemaName(j));
           rsmdi.setPrecision(j, col.getPrecision());
           rsmdi.setScale(j, col.getScale());
           //rsmdi.setTableName(j, resultsetmetadata.getTableName(j));
           //rsmdi.setCatalogName(j, resultsetmetadata.getCatalogName(j));
           rsmdi.setColumnType(j, col.getType());
           //rsmdi.setColumnTypeName(j, resultsetmetadata.getColumnTypeName(j));
       }

       crs.setMetaData(rsmdi);

       ArrayList rowList = dataRow.getRowList();
       int count = rowList.size();

       for (int i = 0; i < count; i++)
        {
            // moveToInsertRow()将游标移动到可能包含初始列值的空白行
            crs.moveToInsertRow();

            ColFieldArray row = (ColFieldArray)rowList.get(i);

            for (int j = 1; j <= colCount; j++)
            {
                ColField col = (ColField)row.getColList().get(j-1);

                Object obj = col.getValue();


                crs.updateObject(j, obj);
            }

            //调用insertRow()将新行插入到游标最近位置的后面
            crs.insertRow();
        }

        //在调用其它导航命令之前调用moveToCurrentRow()重置游标位置
        crs.moveToCurrentRow();

        crs.beforeFirst();
   }
}
