package com.dcdzsoft.sda.db;

import java.sql.*;
import java.sql.PreparedStatement;
import java.util.*;
import javax.sql.RowSet;

import com.sun.rowset.CachedRowSetImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.collections.FastArrayList;
import org.apache.commons.collections.FastHashMap;

import com.dcdzsoft.EduException;

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
public class ProcedureUtils
{
    private static Log log = LogFactory.getLog(ProcedureUtils.class.getName());


    private static int ORACLE_CURSOR = -10;

    /**
     * 存储过程的名称
     */
    private String procedureName = null;

    private FastArrayList inParams = new FastArrayList(20);
    private FastArrayList outParams = new FastArrayList(2);
    private FastHashMap outValues = new FastHashMap(2);

    /**
     * 构造器
     * @param procedureName String--存储过程的名称
     */
    public ProcedureUtils(String procedureName)
    {
        this.procedureName = procedureName;

        inParams.setFast(true);
        outParams.setFast(true);
        outValues.setFast(true);
    }

    /**
     * 执行不返回结果集的存储过程
     * @param session DBSession
     * @throws EduException
     * @return int
     */
    public int executeProcUpdate(DBSession session)
        throws EduException
    {
        CallableStatement cstmt = null;

        try
        {
            String sql = getProcedureUpdateSQL();

            cstmt = session.getConnection().prepareCall(sql);

            //输入参数
            for (Iterator itr = inParams.iterator(); itr.hasNext(); )
            {
                ProcedureParam param = (ProcedureParam) itr.next();
                registerProcedureInParameter(cstmt,
                                             param.getIndex(),
                                             param.getJdbcType(),
                                             param.getValue());
            }

            //输出参数
            for (Iterator itr = outParams.iterator(); itr.hasNext(); )
            {
                ProcedureParam param = (ProcedureParam) itr.next();
                registerProcedureOutParameter(cstmt, param.getIndex(),
                                              param.getJdbcType());
            }

            cstmt.executeUpdate();

            //输出值
            for (Iterator itr = outParams.iterator(); itr.hasNext(); )
            {
                ProcedureParam param = (ProcedureParam) itr.next();
                int index = param.getIndex();
                int jdbcType = param.getJdbcType();

                addOutValue(index, getParameter(cstmt, index, jdbcType));
            }

        }
        catch (SQLException e)
        {
            String errorCode = EduException.EXCP_EXECUTEPROCFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + " [errmsg]:" + e.getMessage());
            throw new EduException(errorCode);
        }
        finally
        {
            session.closeStatement(cstmt);
        }

        return 0;
    }

    /**
    * 独立执行不返回结果集的存储过程
    * @throws EduException
    * @return int
    */
   public int executeProcUpdate()
       throws EduException
   {
       Connection conn = null;
       CallableStatement cstmt = null;
       DBSession dbSession = new DBSession();

       try
       {
           String sql = getProcedureUpdateSQL();

           conn = dbSession.getConnection();
           cstmt = conn.prepareCall(sql);

           //输入参数
           for (Iterator itr = inParams.iterator(); itr.hasNext(); )
           {
               ProcedureParam param = (ProcedureParam) itr.next();
               registerProcedureInParameter(cstmt,
                                            param.getIndex(),
                                            param.getJdbcType(),
                                            param.getValue());
           }

           //输出参数
           for (Iterator itr = outParams.iterator(); itr.hasNext(); )
           {
               ProcedureParam param = (ProcedureParam) itr.next();
               registerProcedureOutParameter(cstmt, param.getIndex(),
                                             param.getJdbcType());
           }

           cstmt.executeUpdate();

           //输出值
           for (Iterator itr = outParams.iterator(); itr.hasNext(); )
           {
               ProcedureParam param = (ProcedureParam) itr.next();
               int index = param.getIndex();
               int jdbcType = param.getJdbcType();

               addOutValue(index, getParameter(cstmt, index, jdbcType));
           }

           //必须独立提交
          dbSession.commit();
       }
       catch (SQLException e)
       {
           String errorCode = EduException.EXCP_EXECUTEPROCFAIL;
           log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                     e.getErrorCode() + " [errmsg]:" + e.getMessage());
           throw new EduException(errorCode);
       }
       finally
       {
           dbSession.closeStatement(cstmt);

           dbSession.closeConnection();
           conn = null;
       }

       return 0;
   }


    /**
     * 执行返回结果集的存储过程
     * @param session DBSession
     * @throws EduException
     * @return RowSet
     */
    public RowSet executeProcQuery(DBSession session)
        throws EduException
    {
        CallableStatement cstmt = null;
        ResultSet rset = null;

        CachedRowSetImpl cacheSet = null;

        int databaseType = session.getDatabaseType();

        try
        {
            String sql = getProcedureQuerySQL();
            cstmt = session.getConnection().prepareCall(sql);
            cacheSet = new CachedRowSetImpl();

            //输入参数
            for (Iterator itr = inParams.iterator(); itr.hasNext(); )
            {
                ProcedureParam param = (ProcedureParam) itr.next();
                registerProcedureInParameter(cstmt,
                                             param.getIndex() + 1,
                                             param.getJdbcType(),
                                             param.getValue());
            }

            //输出参数
            for (Iterator itr = outParams.iterator(); itr.hasNext(); )
            {
                ProcedureParam param = (ProcedureParam) itr.next();
                registerProcedureOutParameter(cstmt, param.getIndex() + 1,
                                              param.getJdbcType());
            }

            if (databaseType == DataSourceUtils.DB_TYPE_ORACLE)
            {
                //第一个输出参数
                registerProcedureOutParameter(cstmt, 1, ORACLE_CURSOR);

                cstmt.execute();

                rset = (ResultSet) cstmt.getObject(1);

                if (rset == null)
                    throw new EduException(EduException.EXCP_EXECUTEPROCFAIL);

                cacheSet.populate(rset);
            }

            //输出值
            for (Iterator itr = outParams.iterator(); itr.hasNext(); )
            {
                ProcedureParam param = (ProcedureParam) itr.next();
                int index = param.getIndex();
                int jdbcType = param.getJdbcType();

                addOutValue(index, getParameter(cstmt, index + 1, jdbcType));
            }
        }
        catch (SQLException e)
        {
            String errorCode = EduException.EXCP_EXECUTEPROCFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + " [errmsg]:" + e.getMessage());
            throw new EduException(errorCode);
        }
        finally
        {
            session.closeResultSet(rset);
            session.closeStatement(cstmt);
        }

        return cacheSet;
    }

    /**
     * 注册存储过程的字符型的输入参数
     * @param index int--the first column is 1, the second is 2, ...
     * @param x String--输入参数值
     */
    public void registerInParameter(int index, String x)
    {
        ProcedureParam param = new ProcedureParam();
        param.setIndex(index);
        param.setJdbcType(Types.VARCHAR);
        param.setValue(x);

        inParams.add(param);
    }

    /**
     * 注册存储过程的整型的输入参数
     * @param index int--the first column is 1, the second is 2, ...
     * @param x int--输入参数值
     */
    public void registerInParameter(int index, int x)
    {
        ProcedureParam param = new ProcedureParam();
        param.setIndex(index);
        param.setJdbcType(Types.INTEGER);
        param.setValue(new Integer(x));

        inParams.add(param);
    }

    /**
     * 注册存储过程的整型的输入参数
     * @param index int--the first column is 1, the second is 2, ...
     * @param x long--输入参数值
     */
    public void registerInParameter(int index, long x)
    {
        ProcedureParam param = new ProcedureParam();
        param.setIndex(index);
        param.setJdbcType(Types.BIGINT);
        param.setValue(new Long(x));

        inParams.add(param);
    }


    /**
     * 注册存储过程的双精度型的输入参数
     * @param index int--the first column is 1, the second is 2, ...
     * @param x int--输入参数值
     */
    public void registerInParameter(int index, double x)
    {
        ProcedureParam param = new ProcedureParam();
        param.setIndex(index);
        param.setJdbcType(Types.DOUBLE);
        param.setValue(new Double(x));

        inParams.add(param);
    }

    /**
     * 注册存储过程的字符型型输出参数
     * @param index int--the first column is 1, the second is 2, ...(和输入参数一起累加)
     */
    public void registerOutString(int index)
    {
        ProcedureParam param = new ProcedureParam();
        param.setIndex(index);
        param.setJdbcType(Types.VARCHAR);

        outParams.add(param);
    }

    /**
     * 注册存储过程的整型输出参数
     * @param index int--the first column is 1, the second is 2, ...(和输入参数一起累加)
     */
    public void registerOutInteger(int index)
    {
        ProcedureParam param = new ProcedureParam();
        param.setIndex(index);
        param.setJdbcType(Types.INTEGER);

        outParams.add(param);
    }

    /**
     * 注册存储过程的双精度型输出参数
     * @param index int--the first column is 1, the second is 2, ...(和输入参数一起累加)
     */
    public void registerOutDouble(int index)
    {
        ProcedureParam param = new ProcedureParam();
        param.setIndex(index);
        param.setJdbcType(Types.DOUBLE);

        outParams.add(param);
    }

    /**
     * 获得String类型的输出值
     * @param index int--对应注册的输出参数的索引
     * @return String
     */
    public String getString(int index)
    {
        Object value = outValues.get(new Integer(index));

        if (value != null)
            return value.toString();

        return null;
    }

    /**
     * 获得int类型的输出值
     * @param index int--对应注册的输出参数的索引
     * @return int
     */
    public int getInt(int index)
    {
        Object value = outValues.get(new Integer(index));
        if (value != null)
            return ( (Integer) value).intValue();

        return 0;
    }

    /**
     * 获得double类型的输出值
     * @param index int--对应注册的输出参数的索引
     * @return double
     */
    public double getDouble(int index)
    {
        Object value = outValues.get(new Integer(index));
        if (value != null)
            return ( (Double) value).doubleValue();

        return 0.0;
    }

    protected String getProcedureUpdateSQL()
    {
        StringBuffer sb = new StringBuffer(128);
        int count = inParams.size() + outParams.size();

        sb.append("{call " + procedureName + "(");
        for (int i = 0; i < count; i++)
        {
            sb.append("?");

            if (i != count - 1)
                sb.append(",");
        }

        sb.append(")}");

        return sb.toString();
    }

    protected String getProcedureQuerySQL()
    {
        StringBuffer sb = new StringBuffer(128);
        int count = inParams.size() + outParams.size();

        sb.append("{? = call " + procedureName + "(");
        for (int i = 0; i < count; i++)
        {
            sb.append("?");

            if (i != count - 1)
                sb.append(",");
        }

        sb.append(")}");

        return sb.toString();
    }

    protected void registerProcedureInParameter(
        CallableStatement cs,
        int index,
        int jdbcType,
        Object value)
        throws SQLException, EduException
    {
        //
        // null
        //
        if (value == null)
        {
            cs.setNull(index, jdbcType);
            return;
        }

        switch (jdbcType)
        {
            case Types.INTEGER:
            case Types.SMALLINT:
            case Types.TINYINT:
            {
                cs.setInt(index, ( (Integer) value).intValue());
                break;
            }
            case Types.BIGINT:
            {
               cs.setLong(index, ( (Long) value).longValue());
               break;
            }
            case Types.DOUBLE:
            case Types.FLOAT:
            {
                cs.setDouble(index, ( (Double) value).doubleValue());
                break;
            }
            case Types.VARCHAR:
            case Types.CHAR:
            {
                cs.setString(index, value.toString());
                break;
            }
            default:
            {
                String errorCode = EduException.EXCP_NOTSUPPORTDATATYPE;
                log.error("[DBERROR-(" + errorCode + ")]");

                throw new EduException(errorCode);
            }
        }

    }

    protected void registerProcedureOutParameter(
        CallableStatement cs,
        int index,
        int jdbcType)
        throws SQLException
    {
        cs.registerOutParameter(index, jdbcType);
    }

    protected Object getParameter(
        CallableStatement cs,
        int index,
        int jdbcType)
        throws SQLException, EduException
    {
        Object value = null;

        switch (jdbcType)
        {
            case Types.INTEGER:
            case Types.SMALLINT:
            case Types.TINYINT:
            {
                value = new Integer(cs.getInt(index));
                break;
            }
            case Types.DOUBLE:
            case Types.FLOAT:
            {
                value = new Double(cs.getDouble(index));
                break;
            }
            case Types.VARCHAR:
            case Types.CHAR:
            {
                value = cs.getString(index);
                break;
            }
            default:
            {
                String errorCode = EduException.EXCP_NOTSUPPORTDATATYPE;
                log.error("[DBERROR-(" + errorCode + ")]");

                throw new EduException(errorCode);
            }
        }

        return value;
    }

    protected void addOutValue(int index, Object value)
    {
        outValues.put(new Integer(index), value);
    }

}
