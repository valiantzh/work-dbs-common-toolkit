package com.dcdzsoft.sda.aop;

import java.util.List;
import java.util.ArrayList;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.apache.commons.logging.Log;

import com.dcdzsoft.EduException;
import com.dcdzsoft.config.ErrorMsgConfig;

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
public abstract class AOPBaseInterface implements InvocationHandler
{
    private static Log logger = org.apache.commons.logging.LogFactory.getLog(
        AOPBaseInterface.class);

    /**被代理类的实例*/
    private Object originalObject;

    /**存放所有的拦截器*/
    protected List interceptors = new ArrayList();

    /**要拦截方法的名称集合*/
    protected List interMethodNames = new ArrayList();

    public AOPBaseInterface()
    {
        initInterceptors(); //初始化拦截器
        initInterMethodNames(); //初始化拦截方法名称集合
    }

    /***
     * 初始化拦截器
     */
    protected abstract void initInterceptors();

    /**
     * 初始化拦截的方法的名称集合
     */
    protected abstract void initInterMethodNames();

    /**
     *返回动态代理实例.
     * @param obj Object被代理类的实例
     * @return Object返回动态代理实例
     */
    public Object bind(Object obj)
    {
        this.originalObject = obj;

        //返回动态代理实例
        Object objt = Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                                             obj.getClass().getInterfaces(), this);
        return objt;
    }

    /**
     *对被代理类的方法进行拦截
     * @param proxy Object
     * @param method Method
     * @param methodParameters Object[]
     * @throws Throwable
     * @return Object
     */
    public Object intercept(Object proxy, Method method,
                            Object[] methodParameters)
        throws Throwable
    {

        Object result = null;
        Throwable ex = null;
        InvocationInfo invInfo = null;

        String methodName = method.getName();

        invInfo = new InvocationInfo(proxy,
                                     method,
                                     methodParameters,
                                     null,
                                     result,
                                     ex);

        //如果调用的方法名称存在于拦截方法的名称集合
        if (interMethodNames.contains(methodName))
        {
            try
            {
                invokeInterceptorsBefore(invInfo);

                result = method.invoke(originalObject, methodParameters);

                invInfo.setResult(result);
                invokeInterceptorsAfter(invInfo);
            }
            catch (Throwable tr)
            {
                invInfo.setException(tr);
                invokeInterceptorsExceptionThrow(invInfo);

                if (tr instanceof EduException) {
                    throw new EduException(ErrorMsgConfig.getLocalizedMessage(
                            tr.getMessage()));
                } else
                    throw tr;

            }
            finally
            {
                //调用last Intercetpors
                invokeInterceptorsLast(invInfo);
            }
        }
        else
        {
            result = method.invoke(originalObject, methodParameters);
        }

        return result;
    }

    /**
     * 调用Before Interceptor
     * @param invInfo InvocationInfo
     * @throws EduException
     */
    private void invokeInterceptorsBefore(InvocationInfo invInfo) throws EduException
    {

        //递增循环调用所有拦截器(interceptors)的before方法；
        for (int i = 0; i < interceptors.size(); i++)
        {
            Interceptor brt = (Interceptor) interceptors.get(i);
            brt.before(invInfo);
        }
    }

    /**
     * 调用After Interceptor
     * @param invInfo InvocationInfo
     * @throws EduException
     */
    private void invokeInterceptorsAfter(InvocationInfo invInfo) throws EduException
    {

        //递减循环调用所有拦截器(interceptors)的After方法；
        for (int i = interceptors.size() - 1; i >= 0; i--)
        {
            Interceptor brt = (Interceptor) interceptors.get(i);
            brt.after(invInfo);
        }
    }

    /**
     * 调用Last Interceptor
     * @param invInfo InvocationInfo
     * @throws EduException
     */
    private void invokeInterceptorsLast(InvocationInfo invInfo)throws EduException
    {

        //递减循环调用所有拦截器(interceptors)的After方法；
        for (int i = interceptors.size() - 1; i >= 0; i--)
        {
            Interceptor brt = (Interceptor) interceptors.get(i);
            brt.last(invInfo);
        }
    }

    /**
     * 调用exceptionThrow Interceptor
     * @param invInfo InvocationInfo
     * @throws EduException
     */
    private void invokeInterceptorsExceptionThrow(InvocationInfo invInfo)throws EduException
    {

        //递减循环调用所有拦截器(interceptors)的exceptionThrow方法；
        for (int i = interceptors.size() - 1; i >= 0; i--)
        {
            Interceptor brt = (Interceptor) interceptors.get(i);
            brt.exceptionThrow(invInfo);
        }
    }

}
