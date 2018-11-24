package com.dcdzsoft.sda.db;

import org.apache.commons.lang.StringUtils;
import com.dcdzsoft.sda.db.support.CommonUtils;

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
public class WhereExpression {
    private StringBuffer sb = new StringBuffer(128);

    /**
     * 增加字符型数值的条件表达式,默认的操作符为=
     * @param name String--column name
     * @param value String--column value
     * @param flag boolean--是否需要判断非空
     */
    public void add(String name, String value, boolean flag) {
        String whereSQL = " AND " + name + " = " +
                          CommonUtils.addQuote(value);

        if (flag == true) {
            if (StringUtils.isNotEmpty(value))
                sb.append(whereSQL);
        } else
            sb.append(whereSQL);
    }

    /**
     * 增加整型的条件表达式,默认的操作符为=
     * @param name String
     * @param value int
     * @param flag boolean
     */
    public void add(String name, int value, boolean flag) {
        String whereSQL = " AND " + name + " = " + value;

        if (flag == true) {
            if (value > 0) {
                sb.append(whereSQL);
            }
        } else
            sb.append(whereSQL);
    }

    /**
     * 增加长整型的条件表达式,默认的操作符为=
     * @param name String
     * @param value long
     * @param flag boolean
     */
    public void add(String name, long value, boolean flag) {
        String whereSQL = " AND " + name + " = " + value;

        if (flag == true) {
            if (value > 0L) {
                sb.append(whereSQL);
            }
        } else
            sb.append(whereSQL);
    }


    /**
     * 增加double型的条件表达式,默认的操作符为=
     * @param name String
     * @param value double
     * @param flag boolean
     */
    public void add(String name, double value, boolean flag) {
        String whereSQL = " AND " + name + " = " + value;

        if (flag == true) {
            if (value > 0) {
                sb.append(whereSQL);
            }
        } else
            sb.append(whereSQL);

    }

    /**
     * 增加字符型数值的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value String
     * @param flag boolean
     */
    public void add(String name, String operator, String value, boolean flag) {
        String whereSQL = " AND " + name + " " + operator + " " +
                          CommonUtils.addQuote(value);

        if (flag == true) {
            if (StringUtils.isNotEmpty(value))
                sb.append(whereSQL);
        } else
            sb.append(whereSQL);
    }

    /**
     * 增加整型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value int
     * @param flag boolean
     */
    public void add(String name, String operator, int value, boolean flag) {
        String whereSQL = " AND " + name + " " + operator + " " + value;

        if (flag == true) {
            if (value > 0) {
                sb.append(whereSQL);
            }
        } else
            sb.append(whereSQL);
    }

    /**
     * 增加长整型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value int
     * @param flag boolean
     */
    public void add(String name, String operator, long value, boolean flag) {
        String whereSQL = " AND " + name + " " + operator + " " + value;

        if (flag == true) {
            if (value > 0L) {
                sb.append(whereSQL);
            }
        } else
            sb.append(whereSQL);
    }


    /**
     * 增加双精度型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value double
     * @param flag boolean
     */
    public void add(String name, String operator, double value, boolean flag) {
        String whereSQL = " AND " + name + " " + operator + " " + value;

        if (flag == true) {
            if (value > 0) {
                sb.append(whereSQL);
            }
        } else
            sb.append(whereSQL);
    }

    public void addOR(String name, String value, boolean flag) {
        String whereSQL = " OR " + name + " = " +
                          CommonUtils.addQuote(value);

        if (flag == true) {
            if (StringUtils.isNotEmpty(value))
                sb.append(whereSQL);
        } else
            sb.append(whereSQL);
    }

    public void addOR(String name, int value, boolean flag) {
        String whereSQL = " OR " + name + " = " + value;

        if (flag == true) {
            if (value > 0) {
                sb.append(whereSQL);
            }
        } else
            sb.append(whereSQL);
    }

    public void addOR(String name, long value, boolean flag) {
        String whereSQL = " OR " + name + " = " + value;

        if (flag == true) {
            if (value > 0L) {
                sb.append(whereSQL);
            }
        } else
            sb.append(whereSQL);
    }


    public void addOR(String name, double value, boolean flag) {
        String whereSQL = " OR " + name + " = " + value;

        if (flag == true) {
            if (value > 0) {
                sb.append(whereSQL);
            }
        } else
            sb.append(whereSQL);

    }

    public void addOR(String name, String operator, String value, boolean flag) {
        String whereSQL = " OR " + name + " " + operator + " " +
                          CommonUtils.addQuote(value);

        if (flag == true) {
            if (StringUtils.isNotEmpty(value))
                sb.append(whereSQL);
        } else
            sb.append(whereSQL);
    }

    public void addOR(String name, String operator, int value, boolean flag) {
        String whereSQL = " OR " + name + " " + operator + " " + value;

        if (flag == true) {
            if (value > 0) {
                sb.append(whereSQL);
            }
        } else
            sb.append(whereSQL);
    }

    public void addOR(String name, String operator, long value, boolean flag) {
        String whereSQL = " OR " + name + " " + operator + " " + value;

        if (flag == true) {
            if (value > 0L) {
                sb.append(whereSQL);
            }
        } else
            sb.append(whereSQL);
    }


    public void addOR(String name, String operator, double value, boolean flag) {
        String whereSQL = " OR " + name + " " + operator + " " + value;

        if (flag == true) {
            if (value > 0) {
                sb.append(whereSQL);
            }
        } else
            sb.append(whereSQL);
    }

    /**
     * 直接传入SQL 表达式
     * @param sql String
     */
    public void addSQL(String sql) {
        sb.append(" " + sql + " ");
    }

    /**
     * 返回构造后的SQL语句
     * @return String
     */
    public String getWhereSQL() {
        return sb.toString();
    }

}
