package com.dcdzsoft.sda.db;

import java.sql.*;

import com.dcdzsoft.*;

import org.apache.commons.dbcp.*;
import org.apache.commons.logging.*;

import com.dcdzsoft.dao.common.UtilDAO;
import com.dcdzsoft.dao.common.impl.mysql.MysqlUtilDAO;
import com.dcdzsoft.dao.common.impl.oracle.OracleUtilDAO;
import com.dcdzsoft.dao.common.impl.mssql.MsSQLUtilDAO;

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
public class DataSourceUtils
{
   private static Log log = org.apache.commons.logging.LogFactory.getLog(
        DataSourceUtils.class);

    /**
     * 数据库的类型
     */
    public static final int DB_TYPE_ORACLE = 1; 
    public static final int DB_TYPE_SQLSERVER = 2;
    public static final int DB_TYPE_DB2 = 3;
    public static final int DB_TYPE_MYSQL = 4; //默认的数据库类型为Mysql
    public int databaseType = DB_TYPE_MYSQL;

    //数据库连接的参数
    private String driverClassName; //JDBC驱动的名称
    private String url; //连接数据库的URL
    private String username; //连接数据库的用户
    private String password; //连接数据库的用户密码

    //数据库连接池的参数
    private int maxActive;
    private int maxIdle;
    private int minIdle;
    private long maxWait;
    private long timeBetweenEvictionRunsMillis;
    private String validationQuery = "";
    
    //
    private double jetLag = 0.0;//数据库服务器时间和本地时间的时差

    private BasicDataSource ds;
    
    protected DataSourceUtils()
    {
        //初始化datasource
        ds = new BasicDataSource();
        ds.setDefaultAutoCommit(false);
        ds.setDefaultReadOnly(false);
    }

    private static class SingletonHolder{
    	 private static DataSourceUtils instance = new DataSourceUtils(); //自己的实例
    }
    
    public static DataSourceUtils getInstance()
    {
        return SingletonHolder.instance;
    }

    /**
     *获得默认数据库配置的Connection.
     * @return Connection
     * @throws EduException
     */
    public Connection getConnection()
        throws EduException
    {

        Connection conn = null;

        try
        {
            conn = ds.getConnection();
            conn.setAutoCommit(false);
        }
        catch (SQLException e)
        {
            String errorCode = EduException.EXCP_GETCONNECTIONFAIL;
            log.error("[DBERROR-(" + errorCode + ")]==>get connection fail:" +
                      e.getMessage());
            throw new EduException(errorCode);
        }
        return conn;
    }

    protected void closeDataSource()
    {
        if (ds != null)
        {
            try
            {
                ds.close();
            }
            catch (SQLException e)
            {
                System.err.println("close dataSource error:" + e.getMessage());
            }
        }
    }

    public int getDatabaseType()
    {
        return databaseType;
    }

     /**
     *
     * @return String
     */
    public String getDriverClassName()
    {
        return driverClassName;
    }

    public int getNumActive(){
        return ds.getNumActive();
    }

    public int getNumIdle(){
        return ds.getNumIdle();
    }

    /**
     *
     * @return int
     */
    public int getMaxActive()
    {
        return maxActive;
    }

    /**
     *
     * @return int
     */
    public int getMaxIdle()
    {
        return maxIdle;
    }

    /**
     *
     * @return long
     */
    public long getMaxWait()
    {
        return maxWait;
    }

    /**
     *
     * @return int
     */
    public int getMinIdle()
    {
        return minIdle;
    }

    /**
     *
     * @return String
     */
    public String getPassword()
    {
        return password;
    }

    /**
     *
     * @return long
     */
    public long getTimeBetweenEvictionRunsMillis()
    {
        return timeBetweenEvictionRunsMillis;
    }

    /**
     *
     * @return String
     */
    public String getUrl()
    {
        return url;
    }

    /**
     *
     * @return String
     */
    public String getUsername()
    {
        return username;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    /**
     *
     * @return BasicDataSource
     */
    public BasicDataSource getBasicDataSource()
    {
        return ds;
    }

    /**
     *
     * @param databaseType int
     */
    public void setDatabaseType(int databaseType)
    {
        this.databaseType = databaseType;
    }

    /**
     *
     * @param driverClassName String
     */
    public void setDriverClassName(String driverClassName)
    {
        this.driverClassName = driverClassName;
        ds.setDriverClassName(driverClassName);
    }

    /**
     *
     * @param maxActive int
     */
    public void setMaxActive(int maxActive)
    {
        this.maxActive = maxActive;
        ds.setMaxActive(maxActive);
    }

    /**
     *
     * @param maxIdle int
     */
    public void setMaxIdle(int maxIdle)
    {
        this.maxIdle = maxIdle;
        ds.setMaxIdle(maxIdle);
    }

    /**
     *
     * @param maxWait long
     */
    public void setMaxWait(long maxWait)
    {
        this.maxWait = maxWait;
        ds.setMaxWait(maxWait);
    }

    /**
     *
     * @param minIdle int
     */
    public void setMinIdle(int minIdle)
    {
        this.minIdle = minIdle;
        ds.setMinIdle(minIdle);
    }

    /**
     *
     * @param timeBetweenEvictionRunsMillis long
     */
    public void setTimeBetweenEvictionRunsMillis(long
                                                 timeBetweenEvictionRunsMillis)
    {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
        ds.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
    }

    /**
     *
     * @param url String
     */
    public void setUrl(String url)
    {
        this.url = url;
        ds.setUrl(url);
    }

    /**
     *
     * @param password String
     */
    public void setPassword(String password)
    {
        this.password = password;
        ds.setPassword(password);
    }

    /**
     *
     * @param username String
     */
    public void setUsername(String username)
    {
        this.username = username;
        ds.setUsername(username);
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
        ds.setValidationQuery(validationQuery);
    }
    
    public double getJetLag() {
        return jetLag;
    }

    public void setJetLag(double jetLag) {
        this.jetLag = jetLag;
    }

    public UtilDAO getUtilDAO()
    {
    	if(databaseType == DB_TYPE_ORACLE)
    		return new OracleUtilDAO();
    	else if(databaseType == DB_TYPE_SQLSERVER)
    		return new MsSQLUtilDAO();
    	
    	return new MysqlUtilDAO();
    }

    /**
     *
     * @param ds BasicDataSource
     */
    public void setBasicDataSource(BasicDataSource ds)
    {
        this.ds = ds;
    }

    public void setTestEnvironment()
    {
        databaseType = DB_TYPE_ORACLE;

        //数据库连接的参数
        this.setDriverClassName("oracle.jdbc.driver.OracleDriver"); //JDBC驱动的名称
        this.setUrl("jdbc:oracle:thin:@10.10.1.188:1521:orcl"); //连接数据库的URL
        this.setUsername("mccspmanager"); //连接数据库的用户
        this.setPassword("mccspmanager"); //连接数据库的用户密码

        //数据库连接池的参数
        this.setMaxActive(300);
        this.setMaxIdle(10);
        this.setMinIdle(1);
        this.setMaxWait(300);
        this.setTimeBetweenEvictionRunsMillis(3000);

        String logPath = "D:/apache-tomcat-7.0.11/webapps/ROOT/log";

        System.setProperty("log.home", logPath);
        org.apache.log4j.PropertyConfigurator.configure(logPath+"/log4j.properties");
    }

    public static void main(String[] args)
        throws Exception
    {
        DataSourceUtils dbUtils = DataSourceUtils.getInstance();
        dbUtils.setTestEnvironment();

        System.out.println("begin");
        dbUtils.getConnection();
        System.out.println("end");
    }


}
