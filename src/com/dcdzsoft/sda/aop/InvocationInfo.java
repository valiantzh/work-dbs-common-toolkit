package com.dcdzsoft.sda.aop;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodProxy;

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
public class InvocationInfo
{
    /**动态生成的被代理类的子类或者被代理接口的实现*/
    private Object proxy;
    /**被代理类被拦截的方法*/
    private Method method;
    /**被代理类被拦截方法的参数*/
    private Object[] args;
    /**动态生成的被代理类的子类的方法*/
    private MethodProxy methodProxy;
    /**被拦截方法执行后的结果*/
    private Object result;
    /**被拦截方法抛出的异常*/
    private Throwable exception;

    public InvocationInfo(Object proxy,
                          Method method,
                          Object[] args,
                          MethodProxy methodProxy,
                          Object result,
                          Throwable exception)
    {
        this.proxy = proxy;
        this.method = method;
        this.args = args;
        this.methodProxy = methodProxy;
        this.result = result;
        this.exception = exception;
    }

    public Object[] getArgs()
    {
        return args;
    }

    public void setArgs(Object[] args)
    {
        this.args = args;
    }

    public Throwable getException()
    {
        return exception;
    }

    public void setException(Throwable exception)
    {
        this.exception = exception;
    }

    public Method getMethod()
    {
        return method;
    }

    public void setMethod(Method method)
    {
        this.method = method;
    }

    public MethodProxy getMethodProxy()
    {
        return methodProxy;
    }

    public void setMethodProxy(MethodProxy methodProxy)
    {
        this.methodProxy = methodProxy;
    }

    public Object getProxy()
    {
        return proxy;
    }

    public void setProxy(Object proxy)
    {
        this.proxy = proxy;
    }

    public Object getResult()
    {
        return result;
    }

    public void setResult(Object result)
    {
        this.result = result;
    }
}
