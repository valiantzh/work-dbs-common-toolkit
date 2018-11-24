package com.dcdzsoft.config;

import java.io.*;
import java.util.HashMap;

import com.dcdzsoft.util.NumberUtils;

/**
 * <p>Title: 智能自助包裹柜系统</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company:  杭州东城电子有限公司</p>
 * @author wangzl
 * @version 1.0
 */

public class ErrorMsgConfig {
    private static HashMap errMsgs = new HashMap();

    public static final int ERROR_SYSTEM_INTERNAL = -9000;
    public static final int ERROR_RECORD_NOT_EXIST = -9001;
    public static final int ERROR_QUERY_VOYAGE_DISTANCE = -9997;
    public static final int ERROR_REPEAT_SIGN = -9998;
    public static final int ERROR_SESSION_TIMEOUT = -9999;

    public static final String MSG_SYSTEM_INTERNAL = "System Internal Error,please contact your system administrator";
    public static final String MSG_RECORD_NOT_EXIST = "Record does not exist";
    public static final String MSG_REPEAT_SIGN = "Account at another location of your login, you were forced to exit the system.\nIf this is not your own operation, then your password is likely to have leaked. \nI suggest you change your password.\n";
    public static final String MSG_SESSION_TIMEOUT = "Session timeout, please re-login";

  	
    private ErrorMsgConfig() {
    }

    public static void load(String filename) throws IOException {
        FileInputStream inps = new FileInputStream(filename);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inps,
                "utf-8"));

        String line = "";

        while ((line = reader.readLine()) != null) {
            if (line.trim().compareTo("") == 0
                || line.trim().startsWith("#") == true)
                continue;

            int idx = line.indexOf("=");
            if (idx == -1)continue;

            String key = line.substring(0, idx).trim();
            String value = line.substring(idx + 1).trim();

            idx = value.indexOf("//");
            if (idx != -1)
                value = value.substring(0, idx).trim();

            //System.out.println(key + ":" + value);
            errMsgs.put(key, value);
        }

        reader.close();
    }

    public static String getLocalizedMessage(String errorCode) {
        String msg = "";
        /*try
                 {
            msg = new String( ( (String) errMsgs.get(errorCode)).getBytes(
                "GB2312"), "ISO8859_1");
                 }
                 catch(Exception e)
                 {
            e.printStackTrace();
                 }*/

        int iErrorCode = Math.abs(NumberUtils.parseInt(errorCode));
        if(iErrorCode == 0){
            msg = errorCode;
        }else{
            if (iErrorCode >= 2 && iErrorCode < 10000) {
                if (iErrorCode >= 2 && iErrorCode < 1000) { //数据库错误
                    Object obj = errMsgs.get(ErrorMsgConfig.
                                             ERROR_SYSTEM_INTERNAL);
                    if (obj != null)
                        msg = errorCode + ":" + (String) obj;
                    else
                        msg = ErrorMsgConfig.MSG_SYSTEM_INTERNAL;
                } else if (iErrorCode >= 1000 && iErrorCode < 5000) { //表级错误
                    if (iErrorCode % 5 == 0) { //记录不存在
                        Object obj = errMsgs.get(ErrorMsgConfig.ERROR_RECORD_NOT_EXIST);
                        if (obj != null)
                            msg = errorCode + ":" + (String) obj;
                        else
                            msg = ErrorMsgConfig.MSG_RECORD_NOT_EXIST;
                    } else {
                        Object obj = errMsgs.get(ErrorMsgConfig.ERROR_SYSTEM_INTERNAL);
                        if (obj != null)
                            msg = errorCode + ":" + (String) obj;
                        else
                            msg = ErrorMsgConfig.MSG_SYSTEM_INTERNAL;
                    }
                } else {
                    Object obj = errMsgs.get(errorCode);
                    if (obj != null)
                        msg = (String) obj;
                    else
                    	msg = errorCode;
                }
            } else {
                Object obj = errMsgs.get(errorCode);
                if (obj != null)
                    msg = (String) obj;
                else
                	msg = errorCode;
            }
        }

        return msg;
    }
}
