package com.dcdzsoft.sda.aop;

import com.dcdzsoft.EduException;

/**
 * <p>Title: 智能自助包裹柜系统</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2014</p>
 *
 * <p>Company: dcdzsoft</p>
 *
 * @author wangzl
 * @version 1.0
 */
public interface Interceptor
{
    public void before(InvocationInfo invInfo)
        throws EduException;

    public void after(InvocationInfo invInfo)
        throws EduException;

    public void last(InvocationInfo invInfo)
        throws EduException;

    public void exceptionThrow(InvocationInfo invInfo)
        throws EduException;

}
