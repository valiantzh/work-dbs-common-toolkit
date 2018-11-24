package com.dcdzsoft.sda.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

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
public class SecurityUtils
{
    private SecurityUtils()
    {
    }

    /**
     * 生成32位的消息摘要
     * @param str String
     * @return String
     */
    public static String md5(String str)
    {
        MD5Digest m = new MD5Digest();

        byte[] in = str.getBytes();
        m.update(in, 0, in.length);

        byte[] out = new byte[16];
        m.doFinal(out, 0);

        //BigInteger bi = new BigInteger(out);

        //return bi.abs().toString(16);
        return toHexString(out);
    }

    /*
     * Converts a byte to hex digit and writes to the supplied buffer
     */
    protected static void byte2hex(byte b, StringBuffer buf)
    {
        char[] hexChars =
            {
            '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        int high = ( (b & 0xf0) >> 4);
        int low = (b & 0x0f);

        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }

    /*
     * Converts a byte array to hex string
     */
    protected static String toHexString(byte[] block)
    {
        StringBuffer buf = new StringBuffer(64);

        int len = block.length;

        for (int i = 0; i < len; i++)
        {
            byte2hex(block[i], buf);
            /*if (i < len - 1)
                         {
                buf.append(":");
                         }*/
        }

        return buf.toString();
    }
    public static String SHA1(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA-1");
            digest.update(decript.getBytes("utf-8"));
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static String SHA(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA");
            digest.update(decript.getBytes("utf-8"));
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args)
        throws Exception
    {
        String pwd = "wangzl";
        System.out.println("pwd is:" + md5(pwd));

        String pwd1 = "888888";
        System.out.println("pwd1 is:" + md5(pwd1));

        String pwd2 = "12345678";
        System.out.println("pwd2 is:" + md5(pwd2));
    }

}
