package com.dcdzsoft.print;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * <p>
 * Title: 智能自助包裹柜系统
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 * 
 * <p>
 * Company: 杭州东城电子有限公司
 * </p>
 * 
 * @author wangzl
 * @version 1.0
 */

public class PrintData implements JRDataSource
{
    private ResultSet resultSet = null;

    public PrintData(ResultSet rs) throws SQLException
    {
        resultSet = rs;
        resultSet.beforeFirst();
    }

    public boolean next() throws JRException
    {
        boolean hasNext = false;

        if (resultSet != null)
        {
            try
            {
                hasNext = resultSet.next();
            }
            catch (SQLException e)
            {
                throw new JRException("Unable to get next record.", e);
            }
        }

        return hasNext;
    }

    public Object getFieldValue(JRField field) throws JRException
    {
        Object objValue = null;

        if (field != null && resultSet != null)
        {
            Class clazz = field.getValueClass();

            try
            {
                if (clazz.equals(java.lang.Object.class))
                {
                    objValue = resultSet.getObject(field.getName());
                }
                else if (clazz.equals(java.lang.Boolean.class))
                {
                    objValue = resultSet.getBoolean(field.getName()) ?
                        Boolean.TRUE : Boolean.FALSE;
                }
                else if (clazz.equals(java.lang.Byte.class))
                {
                    objValue = new Byte(resultSet.getByte(field.getName()));
                    if (resultSet.wasNull())
                    {
                        objValue = new String("");
                    }
                }
                else if (clazz.equals(java.util.Date.class))
                {
                    objValue = resultSet.getDate(field.getName());
                    if (resultSet.wasNull())
                    {
                        objValue = new String("");
                    }
                }
                else if (clazz.equals(java.sql.Timestamp.class))
                {
                    objValue = resultSet.getTimestamp(field.getName());
                    if (resultSet.wasNull())
                    {
                        objValue = new String("");
                    }
                }
                else if (clazz.equals(java.sql.Time.class))
                {
                    objValue = resultSet.getTime(field.getName());
                    if (resultSet.wasNull())
                    {
                        objValue = new String("");
                    }
                }
                else if (clazz.equals(java.lang.Double.class))
                {
                    objValue = new Double(resultSet.getDouble(field.getName()));
                    if (resultSet.wasNull())
                    {
                        objValue = new Double(0.0D);
                    }
                }
                else if (clazz.equals(java.lang.Float.class))
                {
                    objValue = new Float(resultSet.getFloat(field.getName()));
                    if (resultSet.wasNull())
                    {
                        objValue = new Float(0.0f);
                    }
                }
                else if (clazz.equals(java.lang.Integer.class))
                {
                    objValue = new Integer(resultSet.getInt(field.getName()));
                    if (resultSet.wasNull())
                    {
                        objValue = new Integer(0);
                    }
                }
                else if (clazz.equals(java.io.InputStream.class))
                {
                    objValue = resultSet.getBinaryStream(field.getName());
                    if (resultSet.wasNull())
                    {
                        objValue = null;
                    }
                    else
                    {
                        InputStream is = (InputStream) objValue;
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        byte[] bytes = new byte[1000];
                        int ln = 0;
                        try
                        {
                            while ( (ln = is.read(bytes)) > 0)
                            {
                                baos.write(bytes, 0, ln);
                            }
                            baos.flush();
                        }
                        finally
                        {
                            try
                            {
                                baos.close();
                            }
                            catch (IOException e)
                            {
                            }
                        }
                        objValue = new ByteArrayInputStream(baos.toByteArray());
                    }
                }
                else if (clazz.equals(java.lang.Long.class))
                {
                    objValue = new Long(resultSet.getLong(field.getName()));
                    if (resultSet.wasNull())
                    {
                        objValue = new Long(0L);
                    }
                }
                else if (clazz.equals(java.lang.Short.class))
                {
                    objValue = new Short(resultSet.getShort(field.getName()));
                    if (resultSet.wasNull())
                    {
                        objValue = new Short((short)0);
                    }
                }
                else if (clazz.equals(java.math.BigDecimal.class))
                {
                    objValue = resultSet.getBigDecimal(field.getName());
                    if (resultSet.wasNull())
                    {
                        objValue = new java.math.BigDecimal(0);
                    }
                }
                else if (clazz.equals(java.lang.String.class))
                {
                    objValue = resultSet.getString(field.getName());
                    if (resultSet.wasNull())
                    {
                        objValue = new String("");
                    }
                }
            }
            catch (Exception e)
            {
                throw new JRException("Unable to get value for field '" +
                                      field.getName() + "' of class '" +
                                      clazz.getName() + "'", e);
            }
        }

        if(objValue == null)
            objValue = new String("");

        return objValue;
    }
}
