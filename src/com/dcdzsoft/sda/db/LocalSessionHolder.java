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
public class LocalSessionHolder
{
    protected static ThreadLocal dbSessionHolder = new ThreadLocal();

    /**
     * 设置当前的DBSession
     * @param session DBSession
     */
    public static void setCurrentDBSession(DBSession session)
    {
        dbSessionHolder.set(session);
    }

    /**
     * 返回当前的DBSession
     * @return DBSession
     */
    public static DBSession getCurrentDBSession()
    {
        Object obj = dbSessionHolder.get();
        if (obj != null)
            return (DBSession) obj;
        else
            return null;
    }

    public static void clearDBSession()
    {
        dbSessionHolder.set(null);
    }
}
