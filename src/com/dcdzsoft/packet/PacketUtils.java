package com.dcdzsoft.packet;

import java.util.UUID;

import org.apache.commons.logging.Log;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import com.dcdzsoft.util.JsonUtils;

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
public class PacketUtils {
	private static Log log = org.apache.commons.logging.LogFactory.getLog(
			PacketUtils.class);

	public static final String PACKET_FORMAT_ERROR = "wrong packet format.";

	public static JsonPacket toJsonPacket(String message)
	{
		JsonPacket packet = null;
		
		try{
		    JSONObject json = JSONObject.fromObject(message);
		    
			if(json != null){
				packet = (JsonPacket)JSONObject.toBean(json,JsonPacket.class);
			}else{
				log.error("[unpack error:],msg=" + message);
			}
		}catch(Exception e){
			log.error("[handle request:],error= " + e.getMessage() + ",msg=" + message );
		}
		
		return packet;
	}
	
	/**
	 * json自动转为DTO(客户端发起的请求)
	 * @param packet
	 * @param inClass
	 * @return
	 */
	public static Object buildBussinessDTOParam(JsonPacket packet,Class inClass)
	{
		Object dto = null;

		try
		{
			JSONObject jsonObject = JSONObject.fromObject(packet.body);
			//dto = JsonUtils.jsonObjectToBean(jsonObject,inClass);
			dto = JsonUtils.jsonObjectToDTO(jsonObject,inClass);
		}catch(Exception e)
		{
			log.error("[wrong packet format2:],errMsg=" + e.getMessage());
		}


		return dto;
	}
	
	public static String outParamToJson(Object obj)
	{
		Class c = obj.getClass();
		if(c == java.util.LinkedList.class)
		{
			java.util.List list = (java.util.List)obj;
			StringBuffer sb = new StringBuffer(1024);
			sb.append("[");
			for(int i = 0; i < list.size(); i++)
			{
				if(i > 0)
					sb.append(",");

				sb.append(JsonUtils.dtoToJson(list.get(i)));
			}

			sb.append("]");

			return sb.toString();
		}
		else if(c == java.lang.String.class
		        || c == java.lang.Integer.class)
		{
			return JSONUtils.valueToString(obj);
		}
		else
		{
			return JsonUtils.dtoToJson(obj);
		}
	}

	

	/**
	 * 生成成功的返回消息
	 * @param sourcePacket
	 * @param result
	 * @return
	 */
	public static String buildSuccessResult(JsonPacket sourcePacket,Object result)
	{
		if(result != null)
		{
			//JSONObject jsonResultBody = JSONObject.fromObject(result);

			JsonResult jsonResult = new JsonResult(true,"OK");

			//成功结果
			jsonResult.result = outParamToJson(result);

			//包主体
			sourcePacket.body = JsonUtils.dtoToJson(jsonResult);

			JSONObject json = JSONObject.fromObject(sourcePacket);
			if(json != null)
				return json.toString();
		}

		return PACKET_FORMAT_ERROR;
	}

	/**
	 * 生成失败的返回结果
	 * @param sourcePacket
	 * @param errMsg
	 * @return
	 */
	public static String buildFailResult(JsonPacket sourcePacket,String errMsg)
	{
		JsonResult result = new JsonResult(false,errMsg);
		String resultBody = JsonUtils.dtoToJson(result);

		sourcePacket.body = resultBody;

		JSONObject json = JSONObject.fromObject(sourcePacket);
		if(json != null)
			return json.toString();

		return PACKET_FORMAT_ERROR;
	}

	/**
     * 创建请求报文
     * @param request
     * @param terminalNo
     * @param cmdType
     * @return
     */
    public static String createRequestPacket(Object request,String terminalNo, int cmdType){
      //包头
        JsonPacket packet = new JsonPacket();
        packet._CmdType = cmdType;
        //packet._TerminalUid = terminalNo;
        packet._MessageId = UUID.randomUUID().toString(); //replaceAll("-", "");
        packet._ServiceName = request.getClass().getSimpleName();

        //包体
        String body = JsonUtils.dtoToJson(request);

        packet.body = body;
        //packet._Sign = ""; //md5(m_sSecretKey + body)

        JSONObject json = JSONObject.fromObject(packet);
        if(json != null)
            return json.toString();

        return PACKET_FORMAT_ERROR;
    }
    
	/**
	 * 创建服务端发起的请求报文
	 * @param request
	 * @param terminalNo
	 * @return
	 */
	public static String createRequestPacket(Object request,String terminalNo)
    {
		return createRequestPacket(request, terminalNo, JsonPacket.MSG_SENT_BY_SERVER);
    }
	
	/**
     * 创建客户端发起的请求报文
     * @param request
     * @param terminalNo
     * @return
     */
    public static String createRequestPacketFromClient(Object request,String terminalNo)
    {
        return createRequestPacket(request, terminalNo, JsonPacket.MSG_SENT_BY_CLIENT);
    }
	
	public static JsonResult toJsonResult(String message)
    {
        JsonResult result = null;
        
        try{
            JSONObject json = JSONObject.fromObject(message);
            
            if(json != null){
                result = new JsonResult(json.optBoolean("success"),json.optString("msg"));
                result.setResult(json.optString("result"));
            }else{
                log.error("[unpack error:],msg=" + message);
            }
        }catch(Exception e){
            log.error("[handle result:],error= " + e.getMessage() + ",msg=" + message );
        }
        
        return result;
    }
    
}
