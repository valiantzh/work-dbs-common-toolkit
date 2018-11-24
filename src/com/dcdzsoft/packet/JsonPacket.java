package com.dcdzsoft.packet;

import java.io.Serializable;

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
public class JsonPacket implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7257426786076925312L;

	public JsonPacket() {
    }

    public static final int MSG_SENT_BY_CLIENT = 1;
    public static final int MSG_SENT_BY_SERVER = 2;
    public static final int MSG_SENT_BY_MOBILE = 3;

    //public String _Version = "1.0";
    public int _CmdType; //消息发起方 1:终端发起 2:服务端 3:移动端

    public String _MessageId = ""; //消息ID

    //public String _TerminalUid = ""; //设备号
    public String _ServiceName = ""; //服务名称
    public String _Sign = ""; //消息签名

    public String body = ""; //业务数据
    
      
    public String get_MessageId()
    {
    	return _MessageId;
    }
    
    public void set_MessageId(String value)
    {
    	_MessageId = value;
    }
    
    public String get_ServiceName()
    {
    	return _ServiceName;
    }
    
    public void set_ServiceName(String value)
    {
    	_ServiceName = value;
    }
    
    public String getBody()
    {
    	return body;
    }
    
    public void setBody(String value)
    {
    	body = value;
    }
    
    public int get_CmdType()
    {
    	return _CmdType;
    }
    
    public void set_CmdType(int value)
    {
    	_CmdType = value;
    }
    
    public String get_Sign()
    {
    	return _Sign;
    }
    
    public void set_Sign(String value)
    {
    	_Sign = value;
    }
    
    /*public String get_Version()
    {
    	return _Version;
    }
    
    public void set_Version(String value)
    {
    	_Version = value;
    }
    
    public String get_TerminalUid()
    {
    	return _TerminalUid;
    }
    
    public void set_TerminalUid(String value)
    {
    	_TerminalUid = value;
    }
  */
}
