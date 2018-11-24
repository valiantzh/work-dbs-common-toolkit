package com.dcdzsoft.constant;

public class BaseErrorCode {
	public final static String OK_SUCCESS = "0"; // 正确"
	public final static String OK_WARNING = "1"; // 警告"
	public final static String ERR_PARMERR = "-1"; // 参数错误"
	public final static String ERR_NORECORD = "-2"; // 数据库错误"
	public final static String ERR_SQLERR = "-3"; // SQL错误"
	public final static String ERR_ORACLERESOURCEBUSY = "-4"; // 数据库资源忙,请求用了NOWAIT选项"
	public final static String ERR_GETCURRENTDATEFAIL = "-5"; // 表示取当前时间出错"
	public final static String ERR_GETCURRENTTIMEFAIL = "-6"; // 表示取服务器时间出错"
	public final static String ERR_GETSEQUENCEERR = "-7"; // 计数器获取失败"
	public final static String ERR_OPERATEDBERROR = "-8"; // 操作数据库失败"
	public final static String ERR_EXECUTEPROCFAIL = "-9"; // 执行存储过程失败"
	public final static String ERR_GETCONNECTIONFAIL = "-10"; // 获取数据库连接失败"
	public final static String ERR_TRAVERSEROWSETFAIL = "-11"; // 遍历结果集失败"
	public final static String ERR_GETTEXTDATAFAIL = "-12"; // 获取文本数据失败"
	public final static String ERR_GETBINDATAFAIL = "-13"; // 获取二进制数据失败"
	public final static String ERR_BACKUPFAIL = "-20"; // 备份失败"
	public final static String ERR_RECOVERFAIL = "-21"; // 恢复失败"
	public final static String ERR_CONVERTXMLDATA = "-51"; // 转换XMLData数据包出错"
	public final static String ERR_NOTSUPPORTDATATYPE = "-52"; // 不支持的数据类型"
	public final static String ERR_WRONGPUSHMSGFORMAT = "-61"; // 错误的数据格式
	public final static String ERR_NETSERVICEEXCPTION = "-81"; // 网络服务异常，请稍候再试
	
	public static final String ERR_RECORDNOTEXIST = "-9001"; // 记录不存在
	public static final String ERR_RECORDEXISTS = "-9002"; // 记录已经存在
}
