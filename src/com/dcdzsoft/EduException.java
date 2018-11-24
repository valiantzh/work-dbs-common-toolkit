package com.dcdzsoft;

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

public class EduException extends Exception{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3674625526599986057L;

	public final static String TIP_MSG="System Internal Error,please contact your system administrator";

    public final static String	EXCP_PARMERR = "-1"; 				//参数错误
    public final static String	EXCP_NORECORD = "-2"; 				//数据库错误
    public final static String	EXCP_SQLERR = "-3"; 				//执行SQL错误
    public final static String	EXCP_ORACLERESOURCEBUSY = "-4"; 	//数据库资源忙
    public final static String	EXCP_GETCURRENTDATEFAIL = "-5"; 	//表示取当前时间出错
    public final static String	EXCP_GETCURRENTTIMEFAIL = "-6"; 	//表示取服务器时间出错
    public final static String	EXCP_GETSEQUENCEERR = "-7"; 		//计数器获取失败
    public final static String	EXCP_OPERATEDBERROR = "-8"; 		//操作数据库失败
    public final static String	EXCP_EXECUTEPROCFAIL = "-9"; 		//执行存储过程失败
    public final static String	EXCP_GETCONNECTIONFAIL = "-10"; 	//获取数据库联接失败
    public final static String	EXCP_TRAVERSEROWSETFAIL = "-11"; 	//遍历结果集失败
    public final static String	EXCP_GETTEXTDATAFAIL = "-12"; 		//获取文本数据失败
    public final static String	EXCP_GETBINDATAFAIL = "-13"; 		//获取二进制数据失败
    public final static String	EXCP_BACKUPFAIL = "-20"; 			//备份失败
    public final static String	EXCP_RECOVERFAIL = "-21"; 			//恢复失败
    public final static String	EXCP_CONVERTXMLDATA = "-51"; 		//转换XMLData数据包出错
    public final static String	EXCP_NOTSUPPORTDATATYPE = "-52"; 	//不支持的数据类型
    public final static String	EXCP_NOTEXISTCOLUMN = "-53"; 	//不存在的列

    static{

    }

    public EduException(String msg){
        super(msg);
    }
}
