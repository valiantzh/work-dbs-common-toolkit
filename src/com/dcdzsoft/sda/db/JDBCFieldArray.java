package com.dcdzsoft.sda.db;

import org.apache.commons.collections.FastArrayList;
import org.apache.commons.lang.StringUtils;
import com.dcdzsoft.sda.db.support.CommonUtils;
import java.sql.Timestamp;

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
public class JDBCFieldArray {
    //保存所有字段
    protected FastArrayList list = new FastArrayList();

    public JDBCFieldArray() {
        list.setFast(true);
    }

    /**
     * 增加字符型的条件表达式,默认的操作符为=
     * @param name String
     * @param value String
     */
    public void add(String name, String value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(value);

        this.addField(field);
    }

    /**
     * 增加整型的条件表达式,默认的操作符为=
     * @param name String
     * @param value int
     */
    public void add(String name, int value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(new Integer(value));
        field.setType(JDBCField.INT_TYPE);

        this.addField(field);
    }

    /**
     * 增加长整型的条件表达式,默认的操作符为=
     * @param name String
     * @param value long
     */
    public void add(String name, long value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(new Long(value));
        field.setType(JDBCField.LONG_TYPE);

        this.addField(field);
    }

    /**
     * 增加双精度型的条件表达式，默认的操作符为=
     * @param name String
     * @param value double
     */
    public void add(String name, double value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(new Double(value));
        field.setType(JDBCField.DOUBLE_TYPE);

        this.addField(field);
    }

    /**
     * 增加日期型的条件表达式，默认的操作符为=
     * @param name String
     * @param value java.sql.Date
     */
    public void add(String name, java.sql.Date value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(value);
        field.setType(JDBCField.DATE_TYPE);

        this.addField(field);
    }

    /**
     * 增加日期时间型的条件表达式，默认的操作符为=
     * @param name String
     * @param value java.sql.Timestamp
     */
    public void add(String name, java.sql.Timestamp value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(value);
        field.setType(JDBCField.TIMESTAMP_TYPE);

        this.addField(field);
    }

    /**
     * 增加字符型的条件表达式,默认的操作符为=
     * @param name String
     * @param value String
     */
    public void checkAdd(String name, String value) {
        if (CommonUtils.isNotEmpty(value)) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(value);

            this.addField(field);
        }
    }

    /**
     * 增加整型的条件表达式,默认的操作符为=
     * @param name String
     * @param value int
     */
    public void checkAdd(String name, int value) {
        if (value > 0) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(new Integer(value));
            field.setType(JDBCField.INT_TYPE);

            this.addField(field);
        }
    }

    /**
     * 增加长整型的条件表达式,默认的操作符为=
     * @param name String
     * @param value long
     */
    public void checkAdd(String name, long value) {
        if (value > 0L) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(new Long(value));
            field.setType(JDBCField.LONG_TYPE);

            this.addField(field);
        }
    }


    /**
     * 增加双精度型的条件表达式，默认的操作符为=
     * @param name String
     * @param value double
     */
    public void checkAdd(String name, double value) {
        if (value > 0.0D) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(new Double(value));
            field.setType(JDBCField.DOUBLE_TYPE);

            this.addField(field);
        }
    }

    /**
     * 增加日期型的条件表达式，默认的操作符为=
     * @param name String
     * @param value java.sql.Date
     */
    public void checkAdd(String name, java.sql.Date value) {
        if (value != null ) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(value);
            field.setType(JDBCField.DATE_TYPE);

            this.addField(field);
        }
    }

    /**
     * 增加日期时间型的条件表达式，默认的操作符为=
     * @param name String
     * @param value java.sql.Timestamp
     */
    public void checkAdd(String name, java.sql.Timestamp value) {
        if (value != null ) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(value);
            field.setType(JDBCField.TIMESTAMP_TYPE);

            this.addField(field);
        }
    }


    /**
     * 增加字符型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value String
     */
    public void add(String name, String operator, String value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(value);
        field.setOperator(operator);

        this.addField(field);
    }

    /**
     * 增加整型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value int
     */
    public void add(String name, String operator, int value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(new Integer(value));
        field.setType(JDBCField.INT_TYPE);
        field.setOperator(operator);

        this.addField(field);
    }

    /**
    * 增加长整型的条件表达式,操作符为传入的operator
    * @param name String
    * @param operator String
    * @param value long
    */
   public void add(String name, String operator, long value) {
       JDBCField field = new JDBCField();

       field.setName(name);
       field.setValue(new Long(value));
       field.setType(JDBCField.LONG_TYPE);
       field.setOperator(operator);

       this.addField(field);
   }


    /**
     * 增加双精度型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value double
     */
    public void add(String name, String operator, double value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(new Double(value));
        field.setType(JDBCField.DOUBLE_TYPE);
        field.setOperator(operator);

        this.addField(field);
    }

    /**
     * 增加日期型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value java.sql.Date
     */
    public void add(String name, String operator, java.sql.Date value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(value);
        field.setType(JDBCField.DATE_TYPE);
        field.setOperator(operator);

        this.addField(field);
    }

    /**
     * 增加日期时间型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value java.sql.Timestamp
     */
    public void add(String name, String operator, java.sql.Timestamp value) {
        JDBCField field = new JDBCField();

        field.setName(name);
        field.setValue(value);
        field.setType(JDBCField.TIMESTAMP_TYPE);
        field.setOperator(operator);

        this.addField(field);
    }



    /**
     * 增加字符型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value String
     */
    public void checkAdd(String name, String operator, String value) {
        if (CommonUtils.isNotEmpty(value)) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(value);
            field.setOperator(operator);

            this.addField(field);
        }
    }

    /**
     * 增加整型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value int
     */
    public void checkAdd(String name, String operator, int value) {
        if (value > 0) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(new Integer(value));
            field.setType(JDBCField.INT_TYPE);
            field.setOperator(operator);

            this.addField(field);
        }
    }

    /**
     * 增加整型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value long
     */
    public void checkAdd(String name, String operator, long value) {
        if (value > 0) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(new Long(value));
            field.setType(JDBCField.LONG_TYPE);
            field.setOperator(operator);

            this.addField(field);
        }
    }


    /**
     * 增加双精度型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value double
     */
    public void checkAdd(String name, String operator, double value) {
        if (value > 0.0D) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(new Double(value));
            field.setType(JDBCField.DOUBLE_TYPE);
            field.setOperator(operator);

            this.addField(field);
        }
    }

    /**
     * 增加日期型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value java.sql.Date
     */
    public void checkAdd(String name, String operator, java.sql.Date value) {
        if (value != null) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(value);
            field.setType(JDBCField.DATE_TYPE);
            field.setOperator(operator);

            this.addField(field);
        }
    }

    /**
     * 增加日期时间型的条件表达式,操作符为传入的operator
     * @param name String
     * @param operator String
     * @param value java.sql.Timestamp
     */
    public void checkAdd(String name, String operator, java.sql.Timestamp value) {
        if (value != null) {
            JDBCField field = new JDBCField();

            field.setName(name);
            field.setValue(value);
            field.setType(JDBCField.TIMESTAMP_TYPE);
            field.setOperator(operator);

            this.addField(field);
        }
    }


    /**
     * 直接传入SQL语句表达式
     * @param expression String
     */
    public void addSQL(String expression) {
        JDBCField field = new JDBCField();

        field.setName(null);
        field.setValue(expression);

        this.addField(field);
    }

    public int size() {
        return list.size();
    }

    public JDBCField get(int index) {
        return (JDBCField) list.get(index);
    }

    public String toSetSQL(){
        StringBuffer sbSet = new StringBuffer(256);
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).name == null)
                sbSet.append(this.get(i).value.toString());
            else
                sbSet.append(this.get(i).name + " " +
                             this.get(i).operator + " " + "?");

            if (i != (this.size() - 1))
                sbSet.append(",");
        }

        return sbSet.toString();
    }

    public String toWhereSQL(){
        StringBuffer sbWhere = new StringBuffer(256);

        for (int j = 0; j < this.size(); j++) {
            if (this.get(j).name == null)
                sbWhere.append(this.get(j).value.toString());
            else
                sbWhere.append(" AND " + this.get(j).name + " " +
                               this.get(j).operator + " " + "?");
        }

        return sbWhere.toString();
    }

    public String toLogSetSQL(){
        StringBuffer sbSet = new StringBuffer(256);
        for (int i = 0; i < this.size(); i++) {
            JDBCField f = this.get(i);

            if (f.name == null)
                sbSet.append(f.value.toString());
            else
                sbSet.append(f.name + " " + f.operator + " " + f.quoteVal());

            if (i != (this.size() - 1))
                sbSet.append(",");
        }

        return sbSet.toString();
    }

    public String toLogWhereSQL(){
        StringBuffer sbWhere = new StringBuffer(256);

        for (int j = 0; j < this.size(); j++) {
            JDBCField f = this.get(j);
            if (f.name == null)
                sbWhere.append(f.value.toString());
            else
                sbWhere.append(" AND " + f.name + " " + f.operator + " " + f.quoteVal());
        }

        return sbWhere.toString();
    }

    public String toLogWhereSQL(String sql){
        StringBuffer sbWhere = new StringBuffer(256);

        int index = sql.indexOf(" 1=1 ");
        if(index != -1){
            sql = sql.substring(0,index) + " 1=1 ";
            sbWhere.append(sql);
        }

        for (int j = 0; j < this.size(); j++) {
            JDBCField f = this.get(j);
            if (f.name == null)
                sbWhere.append(f.value.toString());
            else
                sbWhere.append(" AND " + f.name + " " + f.operator + " " + f.quoteVal());
        }

        return sbWhere.toString();
    }



    protected void addField(JDBCField f){
        this.list.add(f);
    }
}
