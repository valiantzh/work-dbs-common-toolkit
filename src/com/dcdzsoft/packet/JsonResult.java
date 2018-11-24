package com.dcdzsoft.packet;

import java.io.Serializable;


public class JsonResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8207778947081271537L;
	
	public boolean success;
	public String msg = "";
	public String result = "";
	
	public JsonResult(boolean success,String msg)
	{
		this.success = success;
		this.msg = msg;
	}
	
	public boolean getSuccess()
	{
		return success;
	}
	
	public void setSuccess(boolean value)
	{
		success = value;
	}
	
	public String getMsg()
	{
		return msg;
	}
	
	public void setMsg(String value)
	{
		msg = value;
	}
	
	public String getResult()
	{
		return result;
	}
	
	public void setResult(String value)
	{
		result = value;
	}
	
}
