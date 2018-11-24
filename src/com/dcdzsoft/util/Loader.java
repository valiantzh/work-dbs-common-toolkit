package com.dcdzsoft.util;

import java.net.URL;
import java.lang.IllegalAccessException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
public class Loader
{

    static final private Log log = LogFactory.getLog(Loader.class.getName());

    static final private String TSTR = "Caught Exception while in Loader.getResource. This may be innocuous.";
    /**
     * Get the Thread Context Loader which is a JDK 1.2 feature. If we
     * are running under JDK 1.1 or anything else goes wrong the method
     * @returns <code>null<code>.
     * @throws InvocationTargetException,IllegalAccessException
     *  */
    private static ClassLoader getTCL()
        throws IllegalAccessException, InvocationTargetException
    {
        // Are we running on a JDK 1.2 or later system?
        Method method = null;
        try
        {
            method = Thread.class.getMethod("getContextClassLoader", new Class[]{});
        }
        catch (NoSuchMethodException e)
        {
            // We are running on JDK 1.1
            return null;
        }

        return (ClassLoader) method.invoke(Thread.currentThread());
    }

    /**
     * If running under JDK 1.2 load the specified class using the
     *  <code>Thread</code> <code>contextClassLoader</code> if that
     *  fails try Class.forname. Under JDK 1.1 only Class.forName is
     *  used.
     * @param clazz The Name of Class
     * @return Class
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("rawtypes")
	static public Class loadClass(String clazz)
        throws ClassNotFoundException
    {
        // Just call Class.forName(clazz) if we are running under JDK 1.1
        // or if we are instructed to ignore the TCL.
        try
        {
            return getTCL().loadClass(clazz);
        }
        catch (Throwable e)
        {
            // we reached here because tcl was null or because of a
            // security exception, or because clazz could not be loaded...
            // In any case we now try one more time
            return Class.forName(clazz);
        }
    }

    /**
     This method will search for <code>resource</code> in different
     places. The rearch order is as follows:
     <ol>
     <p><li>Search for <code>resource</code> using the thread context
     class loader under Java2. If that fails, search for
     <code>resource</code> using the class loader that loaded this
     class (<code>Loader</code>). Under JDK 1.1, only the the class
     loader that loaded this class (<code>Loader</code>) is used.
     <p><li>Try one last time with
     <code>ClassLoader.getSystemResource(resource)</code>, that is is
     using the system class loader in JDK 1.2 and virtual machine's
     built-in class loader in JDK 1.1.
     </ol>
     * @param resource The name of resource
     * @return URL
     */
    static public URL getResource(String resource)
    {
        ClassLoader classLoader = null;
        URL url = null;

        try
        {
            classLoader = getTCL();
            if (classLoader != null)
            {
                log.debug("Trying to find [" + resource +
                          "] using context classloader " + classLoader + ".");
                url = classLoader.getResource(resource);
                if (url != null)
                    return url;
            }

            // We could not find resource. Ler us now try with the
            // classloader that loaded this class.
            classLoader = Loader.class.getClassLoader();
            if (classLoader != null)
            {
                log.debug("Trying to find [" + resource + "] using " +
                          classLoader + " class loader.");
                url = classLoader.getResource(resource);
                if (url != null)
                    return url;
            }
        }
        catch (Throwable t)
        {
            log.warn(TSTR, t);
        }

        // Last ditch attempt: get the resource from the class path. It
        // may be the case that clazz was loaded by the Extentsion class
        // loader which the parent of the system class loader. Hence the
        // code below.
        log.debug("Trying to find [" + resource +
                  "] using ClassLoader.getSystemResource().");
        return ClassLoader.getSystemResource(resource);
    }

}
