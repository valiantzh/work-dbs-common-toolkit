package com.dcdzsoft.sda.db;

import org.apache.commons.lang.StringUtils;
import com.dcdzsoft.sda.db.JDBCFieldArray;
import org.apache.commons.collections.FastArrayList;

import com.dcdzsoft.util.DateUtils;

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
public class PreparedWhereExpression extends JDBCFieldArray {
    protected FastArrayList valueList = new FastArrayList();

    public PreparedWhereExpression() {
        valueList.setFast(true);
    }

    public void addPreparedSQL(String sql, JDBCFieldArray valueArray) {
        this.addSQL(sql);

        for (int i = 0; i < valueArray.size(); i++) {
            valueList.add(valueArray.get(i));
        }
    }

    public void addRange(String beginFieldName,int beginValue,String endFieldname,int endValue){
        if(beginValue > 0 && endValue > 0){
            this.add(beginFieldName,"<=",endValue);
            this.add(endFieldname,">=",beginValue);
        }else if(beginValue > 0 && endValue == 0){
            //this.add(beginFieldName,"<=",0);
            this.add(endFieldname,">=",beginValue);
        }else if(beginValue == 0 && endValue > 0){
            this.add(beginFieldName,"<=",endValue);
            this.add(endFieldname,">=",0);
        }

    }

    public void addRange(String beginFieldName,double beginValue,String endFieldname,double endValue){
        if(beginValue > 0.0D && endValue > 0.0D){
            this.add(beginFieldName,"<=",endValue);
            this.add(endFieldname,">=",beginValue);
        }else if(beginValue > 0.0D && endValue == 0.0D){
            //this.add(beginFieldName,"<=",0);
            this.add(endFieldname,">=",beginValue);
        }else if(beginValue == 0.0D && endValue > 0.0D){
            this.add(beginFieldName,"<=",endValue);
            this.add(endFieldname,">=",0.0D);
        }
    }

    public void addRange(String beginFieldName,java.sql.Date beginValue,String endFieldname,java.sql.Date endValue){
        if(beginValue != null && endValue != null){
            this.add(beginFieldName,"<=",endValue);
            this.add(endFieldname,">=",beginValue);
        }else if(beginValue != null && endValue == null){
            //this.add(beginFieldName,"<=",DateUtils.getMinDate());
            this.add(endFieldname,">=",beginValue);
        }else if(beginValue == null && endValue != null){
            this.add(beginFieldName,"<=",endValue);
            this.add(endFieldname,">=",DateUtils.getMinDate());
        }
    }



    /**
     * 返回构造后的SQL语句
     * @return String
     */
    public String getPreparedWhereSQL() {
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

    protected void addField(JDBCField f) {
        super.addField(f);

        if (f.name != null)
            valueList.add(f);
    }

    public int getValueFieldSize() {
        return valueList.size();
    }

    public JDBCField getValueField(int index) {
        return (JDBCField) valueList.get(index);
    }
}
