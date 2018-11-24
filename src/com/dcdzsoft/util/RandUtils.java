package com.dcdzsoft.util;

import java.util.Random;

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
public class RandUtils {
    protected RandUtils() {
    }

    //public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String allChar = "0123456789abcdefghijklmnpqrstuvwxyz";
    public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String numberChar = "0123456789";

    public static String generateString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(allChar.charAt(random.nextInt(allChar.length())));
        }
        return sb.toString();
    }

    public static String generateNumber(int length) {
       StringBuffer sb = new StringBuffer();
       Random random = new Random();

       for (int i = 0; i < length; i++) {
           sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
       }
       return sb.toString();
   }

   public static String generateCharacter(int length) {
       StringBuffer sb = new StringBuffer();
       Random random = new Random();

       for (int i = 0; i < length; i++) {
           sb.append(letterChar.charAt(random.nextInt(letterChar.length())));
       }
       return sb.toString();
   }



}
