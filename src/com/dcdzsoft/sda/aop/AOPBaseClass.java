package com.dcdzsoft.sda.aop;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ArrayList;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodProxy;

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
public abstract class AOPBaseClass implements MethodInterceptor {
    private static Log logger = org.apache.commons.logging.LogFactory.getLog(
         AOPBaseClass.class);

     /**用于动态产生被代理类的子类*/
     private Enhancer enhancer = new net.sf.cglib.proxy.Enhancer();

     /**存放所有的拦截器**/
    protected List<Interceptor> interceptors = new ArrayList<Interceptor>();

     /**要拦截方法的名称集合*/
     protected List<String> interMethodNames = new ArrayList<String>();

     public AOPBaseClass()
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
      *动态产生被代理类的子类的实例..
      * @param clz Class被代理类的Class
      * @return Object 返回被代理类的子类的实例
      */
     @SuppressWarnings("rawtypes")
	public Object bind(Class clz)
     {
         enhancer.setSuperclass(clz); //设置超类
         enhancer.setCallback(this); //设置回调
         Object obj = enhancer.create(); //生成被代理类的子类实例
         return obj;
     }

     /**
      *对被代理的类的方法进行拦截
      * @param proxy Object 动态生成的被代理类的子类
      * @param method Method 被代理类被拦截的方法
      * @param methodParameters Object[] 被代理类被拦截的方法的参数列表
      * @param methodProxy MethodProxy  动态生成的被代理类的子类的方法
      * @throws Throwable
      * @return Object 返回被代理类被拦截的方法执行的结果
      */
     public Object intercept(Object proxy, Method method,
                             Object[] methodParameters, MethodProxy methodProxy)
         throws Throwable

     {
         Object result = null;
         InvocationInfo invInfo = null;
         Throwable ex = null;

         String methodName = method.getName();
         invInfo = new InvocationInfo(proxy,
                                      method,
                                      methodParameters,
                                      methodProxy,
                                      result,
                                      ex);

         /*if(methodParameters != null){
             for(Object po:methodParameters){
                 if(po != null)
                     System.out.println(po.toString());
             }
         }*/

         if (interMethodNames.contains(methodName)) //如果调用的方法名称存在于拦截方法的名称集合
         {
             try
             {

                 //调用 Before Intercetpors
                 invokeInterceptorsBefore(invInfo);

                 result = methodProxy.invokeSuper(proxy, methodParameters);

                 //调用  After Intercetpors
                 invInfo.setResult(result);
                 invokeInterceptorsAfter(invInfo);
             }
             catch (Throwable tr)
             {
                 //调用 Exception Intercetpors
                 invInfo.setException(tr);
                 invokeInterceptorsExceptionThrow(invInfo);
                 if(tr instanceof EduException){
                     //业务返回ErrorCode，在前端提取本地化错误信息  mode by zxy 20160224
                     //throw new EduException(ErrorMsgConfig.getLocalizedMessage(tr.getMessage()));
                     throw new EduException(tr.getMessage());
                 }else
                     throw tr;
             }
             finally
             {
                 //调用last Intercetpors
                 invokeInterceptorsLast(invInfo);
             }
         }
         else //否则如果调用的方法名称存在于拦截方法的名称集合
         {
             //直接调用被代理类的方法
             result = methodProxy.invokeSuper(proxy, methodParameters);
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
     private void invokeInterceptorsAfter(InvocationInfo invInfo)throws EduException
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
