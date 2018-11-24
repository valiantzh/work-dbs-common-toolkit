package com.dcdzsoft.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.math.BigDecimal;

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
public final class NumberUtils
    extends org.apache.commons.lang.math.NumberUtils
{
    protected NumberUtils()
    {
    }

    /**
     * 转换字符串到整数
     * @param str String
     * @return int
     */
    public static int parseInt(String str)
    {
        if (StringUtils.isEmpty(str))
            return 0;

        try
        {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException e)
        {
            return 0;
        }
    }

    /**
     * 转换字符串到长整数
     * @param str String
     * @return long
     */
    public static long parseLong(String str)
    {
        if (StringUtils.isEmpty(str))
            return 0L;

        try
        {
            return Long.parseLong(str);
        }
        catch (NumberFormatException e)
        {
            return 0L;
        }
    }

    /**
     * 转换字符串到浮点数
     * @param str String
     * @return float
     */
    public static float parseFloat(String str)
    {
        if (StringUtils.isEmpty(str))
            return 0.0F;

        try
        {
            return Float.parseFloat(str);
        }
        catch (NumberFormatException e)
        {
            return 0.0F;
        }
    }

    /**
     * 转换字符串到双精度性
     * @param str String
     * @return double
     */
    public static double parseDouble(String str)
    {
        if (StringUtils.isEmpty(str))
            return 0.0D;

        try
        {
            return Double.parseDouble(str);
        }
        catch (NumberFormatException e)
        {
            return 0.0D;
        }

    }

    /**
     * 格式化double数值
     * @param value double
     * @param digitNum int
     * @return String
     */
    public static String formatNumber(double value, int digitNum)
    {
        String str = "0.0";

        if (value != 0.00D)
        {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < digitNum; i++)
            {
                sb.append("#");
            }

            DecimalFormat nf = new DecimalFormat("#." + sb.toString());

            nf.setParseIntegerOnly(false);
            nf.setDecimalSeparatorAlwaysShown(false);

            str = nf.format(value);
        }

        return str;
    }

    public static String formatNumber(double value){
        ////返回当前缺省语言环境的缺省数值格式。
        return NumberFormat.getInstance().format(value);

        //getCurrencyInstance()返回当前缺省语言环境的通用格式

        ///getNumberInstance() 返回当前缺省语言环境的通用数值格式。
    }

    public static double roundHalfUp(double value, int scale)
    {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);

        return bd.doubleValue();
    }

    public static double roundHalfUp(String value, int scale)
    {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);

        return bd.doubleValue();
    }

    public static void main(String[] args)
        throws Exception
    {

    }
}
