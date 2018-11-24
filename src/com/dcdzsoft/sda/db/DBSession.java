package com.dcdzsoft.sda.db;

import java.sql.*;
import java.util.HashMap;

import javax.sql.RowSet;
import com.sun.rowset.CachedRowSetImpl;

import org.apache.commons.logging.Log;

import com.dcdzsoft.EduException;
import com.dcdzsoft.dao.common.UtilDAO;
import com.dcdzsoft.sda.db.support.ResultSetMetaDataWrapper;
import com.dcdzsoft.sda.db.support.ResultSetWrapper;

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
public class DBSession {
    private static Log log = org.apache.commons.logging.LogFactory.getLog(
            DBSession.class); //日志记录器

    private static int RSET_FETCH_SIZE = 50; //the number of rows to fetch


    private Connection conn = null;
    private int databaseType = DataSourceUtils.getInstance().getDatabaseType();
    private double jetLag = DataSourceUtils.getInstance().getJetLag();//时差

    //private java.util.Map typeMap = new HashMap();

    public DBSession() throws EduException {
        conn = DataSourceUtils.getInstance().getConnection();
    }

    /**
     *返回DBSession代表的数据库连接
     * @return Connection
     */
    public Connection getConnection() {
        return conn;
    }

    /**
     * 返回DBSession代表的数据类型
     * @return int
     */
    public int getDatabaseType() {
        return databaseType;
    }
    
    /**
     * 获取数据库服务器与本地的时差
     * @return
     */
    public double getJetLag() {
        return jetLag;
    }

    public RowSet populate(ResultSet rs) throws EduException {
        CachedRowSetImpl cacheSet = null;
        try {
            cacheSet = new CachedRowSetImpl();
            //cacheSet.setTypeMap(typeMap);

            //Test 2011.07.12
            //cacheSet.populate(new ResultSetWrapper(rs));
            cacheSet.populate(rs);
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_SQLERR;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + " [errmsg]:" + e.getMessage());

            e.printStackTrace();

            throw new EduException(errorCode);
        }

        return cacheSet;
    }

    /**
     * Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE
     * statement or an SQL statement that returns nothing, such as an SQL DDL statement
     * @param sql String－an SQL INSERT, UPDATE or DELETE statement or an SQL
     *                  statement that returns nothing
     * @return int－either the row count for INSERT, UPDATE or DELETE statements, or 0 for *           SQL statements that return nothing
     * @throws EduException
     */
    public int executeUpdate(String sql) throws EduException {

        Statement stmt = null;
        int result = 0;

        log.debug("[update sql]:" + sql);

        try {
            stmt = conn.createStatement();
            result = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_SQLERR;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + " [errmsg]:" + e.getMessage() +
                      " [sql]:" + sql);

            e.printStackTrace();

            throw new EduException(errorCode);
        } finally {
            closeStatement(stmt);
        }
        return result;
    }

    /**
     * Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE
     * @param sql String
     * @param expression PreparedWhereExpression
     * @return int
     * @throws EduException
     */
    public int executeUpdate(String sql, PreparedWhereExpression expression) throws EduException {

        PreparedStatement pstmt = null;
        int result = 0;

        if (log.isDebugEnabled())
            log.debug("[update sql]:" + expression.toLogWhereSQL(sql));

        try {
            pstmt = conn.prepareStatement(sql);

            int parameterIndex = 0;
            if (expression != null) {
                for (int j = 0; j < expression.getValueFieldSize(); j++) {
                    parameterIndex++;
                    PreparedStmtUtils.setObject(pstmt, parameterIndex, expression.getValueField(j));
                }
            }

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_SQLERR;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + " [errmsg]:" + e.getMessage() +
                      " [sql]:" + sql);

            e.printStackTrace();

            throw new EduException(errorCode);
        } finally {
            closeStatement(pstmt);
        }
        return result;
    }

    /**
     * Executes the given SQL statement, which returns a single RowSet object
     * @param sql String－an SQL statement to be sent to the database, typically a static SQL
     *                 SELECT statement
     * @return RowSet －a RowSet object that contains the data produced by the given query;
     *                 never null
     * @throws EduException
     */
    public RowSet executeQuery(String sql) throws EduException {

        Statement stmt = null;
        ResultSet rset = null;
        RowSet cacheSet = null;

        log.debug("[query sql]:" + sql);
        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            rset.setFetchSize(RSET_FETCH_SIZE);
            cacheSet = this.populate(rset);
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_SQLERR;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + " [errmsg]:" + e.getMessage() +
                      " [sql]:" + sql);

            e.printStackTrace();

            throw new EduException(errorCode);
        } finally {
            closeResultSet(rset);
            closeStatement(stmt);
        }
        return cacheSet;
    }

    /**
     * Executes the given SQL statement, which returns a single RowSet object
     * @param sql String
     * @param keyList List
     * @param recordBegin int
     * @param recordNum int
     * @return RowSet
     * @throws EduException
     */
    public RowSet executeQuery(String sql, java.util.List<String> keyList, int recordBegin, int recordNum) throws EduException {
        UtilDAO utilDAO = DataSourceUtils.getInstance().getUtilDAO();
        sql = utilDAO.constructNMSql(sql, keyList, recordBegin, recordNum);

        return executeQuery(sql);
    }

    /**
     * 查询结果集中的记录数
     * @param sql String
     * @return int
     * @throws EduException
     */
    public int executeQueryCount(String sql) throws EduException {

        Statement stmt = null;
        ResultSet rset = null;
        int count = 0;

        log.debug("[query sql]:" + sql);
        try {
            stmt = conn.createStatement();
            rset = stmt.executeQuery(sql);

            if (rset.next())
                count = rset.getInt(1);
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_SQLERR;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + " [errmsg]:" + e.getMessage() +
                      " [sql]:" + sql);

            e.printStackTrace();

            throw new EduException(errorCode);
        } finally {
            closeResultSet(rset);
            closeStatement(stmt);
        }

        return count;
    }

    public RowSet executeQuery(String sql, PreparedWhereExpression expression) throws EduException {

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        RowSet cacheSet = null;

        if (log.isDebugEnabled())
            log.debug("[query sql]:" + expression.toLogWhereSQL(sql));

        try {
            pstmt = conn.prepareStatement(sql);

            int parameterIndex = 0;
            if (expression != null) {
                for (int j = 0; j < expression.getValueFieldSize(); j++) {
                    parameterIndex++;
                    PreparedStmtUtils.setObject(pstmt, parameterIndex, expression.getValueField(j));
                }
            }

            rset = pstmt.executeQuery();

            rset.setFetchSize(RSET_FETCH_SIZE);
            cacheSet = this.populate(rset);
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_SQLERR;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + " [errmsg]:" + e.getMessage() +
                      " [sql]:" + sql);

            e.printStackTrace();

            throw new EduException(errorCode);
        } finally {
            closeResultSet(rset);
            closeStatement(pstmt);
        }
        return cacheSet;
    }

    public RowSet executeQuery(String sql, java.util.List<String> keyList, int recordBegin, int recordNum, PreparedWhereExpression expression) throws
            EduException {
        UtilDAO utilDAO = DataSourceUtils.getInstance().getUtilDAO();

        if (log.isDebugEnabled()) {
            log.debug("[query0 sql]:" + utilDAO.constructNMSql(expression.toLogWhereSQL(sql), keyList, recordBegin, recordNum));
        }

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        RowSet cacheSet = null;

        sql = utilDAO.constructNMSql(sql, keyList, recordBegin, recordNum);

        try {
            pstmt = conn.prepareStatement(sql);

            int parameterIndex = 0;
            if (expression != null) {
                for (int j = 0; j < expression.getValueFieldSize(); j++) {
                    parameterIndex++;
                    PreparedStmtUtils.setObject(pstmt, parameterIndex, expression.getValueField(j));
                }
            }

            rset = pstmt.executeQuery();

            rset.setFetchSize(RSET_FETCH_SIZE);
            cacheSet = this.populate(rset);
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_SQLERR;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + " [errmsg]:" + e.getMessage() +
                      " [sql]:" + sql);

            e.printStackTrace();

            throw new EduException(errorCode);
        } finally {
            closeResultSet(rset);
            closeStatement(pstmt);
        }
        return cacheSet;

        //return executeQuery(sql,expression);
    }

    public int executeQueryCount(String sql, PreparedWhereExpression expression) throws EduException {

        PreparedStatement pstmt = null;
        ResultSet rset = null;
        int count = 0;

        if (log.isDebugEnabled())
            log.debug("[query sql]:" + expression.toLogWhereSQL(sql));

        try {
            pstmt = conn.prepareStatement(sql);

            int parameterIndex = 0;
            if (expression != null) {
                for (int j = 0; j < expression.getValueFieldSize(); j++) {
                    parameterIndex++;
                    PreparedStmtUtils.setObject(pstmt, parameterIndex, expression.getValueField(j));
                }
            }

            rset = pstmt.executeQuery();

            if (rset.next())
                count = rset.getInt(1);
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_SQLERR;
            log.error("[DBERROR-(" + errorCode + ")]==>[sqlcode]:" +
                      e.getErrorCode() + " [errmsg]:" + e.getMessage() +
                      " [sql]:" + sql);

            e.printStackTrace();

            throw new EduException(errorCode);
        } finally {
            closeResultSet(rset);
            closeStatement(pstmt);
        }

        return count;
    }


    /**
     * 关闭连接
     * @throws EduException
     */
    public void closeConnection() throws EduException {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_SQLERR;
            log.error("[DBERROR-(" + errorCode + ")]==>close connection fail:" +
                      e.getMessage());
            throw new EduException(errorCode);
        }
    }

    /**
     * 关闭语句
     * @param stmt Statement
     * @throws EduException
     */
    public void closeStatement(Statement stmt) throws EduException {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_SQLERR;
            log.error("[DBERROR-(" + errorCode + ")]==>close statement fail:" +
                      e.getMessage());
            throw new EduException(errorCode);
        }
    }

    /**
     * 关闭结果集
     * @param rset ResultSet
     * @throws EduException
     */
    public void closeResultSet(ResultSet rset) throws EduException {
        try {
            if (rset != null)
                rset.close();
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_SQLERR;
            log.error("[DBERROR-(" + errorCode + ")]==>close ResultSet fail:" +
                      e.getMessage());
            throw new EduException(errorCode);
        }
    }

    /**
     *提交事务
     * @throws EduException
     */
    public void commit() throws EduException {
        try {
            if (conn != null)
                conn.commit();
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_SQLERR;
            log.error("[DBERROR-(" + errorCode + ")]==>commit connection fail:" +
                      e.getMessage());
            throw new EduException(errorCode);
        }
    }

    /**
     *回滚事务
     * @throws EduException
     */
    public void rollback() throws EduException {
        try {
            if (conn != null)
                conn.rollback();
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_SQLERR;
            log.error("[DBERROR-(" + errorCode +
                      ")]==>rollback connection fail:" + e.getMessage());
            throw new EduException(errorCode);
        }
    }

    /**
     *提交事务同时关闭连接
     * @throws EduException
     */
    public void commitAndClose() throws EduException {
        try {
            if (conn != null) {
                conn.commit();
                conn.close();
            }
        } catch (SQLException e) {
            String errorCode = EduException.EXCP_SQLERR;
            log.error("[DBERROR-(" + errorCode +
                      ")]==>commit and close connection fail:" + e.getMessage());
            throw new EduException(errorCode);
        }
    }

}
