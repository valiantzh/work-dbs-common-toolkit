package com.dcdzsoft.util;

import java.util.*;
import java.text.*;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.regex.*;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.io.IOUtils;
import org.htmlparser.Node;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.filters.NodeClassFilter;

import net.sf.json.JSONObject;

import com.dcdzsoft.EduException;


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
public class StringUtils extends org.apache.commons.lang.StringUtils {
    private StringUtils() {}

    /**
     * Left pad a Integer value with a specified character
     *
     * StringUtils.leftPad(111, 3, '0')  = "111"
     * StringUtils.leftPad(111, 5, '0')  = "00111"
     * StringUtils.leftPad(111, 1, '0')  = "111"
     *
     * @param value int
     * @param size int 补齐后字符串的长度
     * @param padChar char
     * @return String
     */
    public static String leftPad(int value, int size, char padChar) {
        String str = String.valueOf(value);
        str = leftPad(str, size, padChar);
        return str;
    }

    public static String leftPad(long value, int size, char padChar) {
        String str = String.valueOf(value);
        str = leftPad(str, size, padChar);
        return str;
    }
    
    


    /**
     * Right pad a Integer value with a specified character
     *
     * StringUtils.rightPad(111, 3, '0')  = "111"
     * StringUtils.rightPad(111, 5, '0')  = "11100"
     * StringUtils.rightPad(111, 1, '0')  = "111"
     *
     * @param value int
     * @param size int 补齐后字符串的长度
     * @param padChar char
     * @return String
     */
    public static String rightPad(int value, int size, char padChar) {
        String str = String.valueOf(value);
        str = rightPad(str, size, padChar);
        return str;
    }

    /**
     * pad String with quote
     *
     * StringUtils.addQuote(“wangzl”)  = ’wangzl’
     * @param str String
     * @return String
     */
    public static String addQuote(String str) {
        str = "'" + encodeSingleQuotedString(str) + "'";

        return str;
    }

    /**
     * pad String with quote
     *
     * StringUtils.addQuote(“wangzl”)  = ’wangzl’
     * @param date java.sql.Timestamp
     * @return String
     */
    public static String addQuote(java.sql.Timestamp date) {
        String str = DateUtils.timestampToString(date);
        str = "'" + encodeSingleQuotedString(str) + "'";

        return str;
    }

    /**
     * pad String with quote
     *
     * StringUtils.addQuote(“wangzl”)  = ’wangzl’
     * @param date java.sql.Timestamp
     * @return String
     */
    public static String addQuote(java.sql.Date date) {
        String str = DateUtils.dateToString(date);
        str = "'" + encodeSingleQuotedString(str) + "'";

        return str;
    }



    /**
     * left pad String with quote
     *
     * StringUtils.leftQuote(“wangzl”)  = "’wangzl"
     * @param str String
     * @return String
     */
    public static String leftQuote(String str) {
        str = "'" + encodeSingleQuotedString(str) + "";

        return str;
    }

    /**
     * right pad String with quote
     *
     * StringUtils.rightQuote(“wangzl”)  = "wangzl’"
     * @param str String
     * @return String
     */
    public static String rightQuote(String str) {
        str = "" + encodeSingleQuotedString(str) + "'";

        return str;
    }

    /**
     * right pad String with quote
     *
     * @param str String
     * @return String
     */
    public static String encodeSingleQuotedString(String str) {
        StringBuffer sb;
        char c;
        if (isNotEmpty(str)) {
            sb = new StringBuffer(str.length() + 8);
            for (int i = 0; i < str.length(); i++) {
                c = str.charAt(i);
                if (c == '\'')
                    sb.append("\'\'");
                else
                    sb.append((char) c);
            }
            return sb.toString();
        }

        return str;
    }

    /**
     * 格式化double输出,保留三位小数点
     * @param value double
     * @return String
     */
    public static String formatDouble(double value) {
        String str = "0.0";

        if (value != 0.00D) {
            //不支持国际化;支持国际化用NumberFormat
            //DecimalFormat nf = new DecimalFormat("#.00");
            DecimalFormat nf = new DecimalFormat("#.###");

            nf.setParseIntegerOnly(false);
            nf.setDecimalSeparatorAlwaysShown(false);

            str = nf.format(value);
        }

        return str;
    }

    /**
     * 根据delimiter分割字符串，过滤掉空字符串；如果不想过滤空字符串，用String.split()=>(用正则表达式)
     * @param str String
     * @param delimiter String
     * @return String[]
     */
    public static String[] tokenize(String str, String delimiter) {
        ArrayList<String> v = new ArrayList<String>();
        String pro[];

        StringTokenizer t = new StringTokenizer(str, delimiter);

        while (t.hasMoreTokens()) {
            String s = t.nextToken();
            if (isNotEmpty(s)) {
                v.add(s.trim());
            }
        }

        pro = new String[v.size()];
        for (int i = 0; i < pro.length; i++)
            pro[i] = (String) v.get(i);

        return pro;
    }

    /*
     * Converts a byte to hex digit and writes to the supplied buffer
     */
    public static void byte2hex(byte b, StringBuffer buf) {
        char[] hexChars = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);

        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }

    /*
     * Converts a byte array to hex string
     */
    public static String toHexString(byte[] block) {
        StringBuffer buf = new StringBuffer(64);

        int len = block.length;

        for (int i = 0; i < len; i++) {
            byte2hex(block[i], buf);
            /*if (i < len - 1)
                         {
                buf.append(":");
                         }*/
        }

        return buf.toString();
    }

    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).
                              toUpperCase());
                }
            }
        }
        //	System.out.println("sb.toString()="+sb.toString());
        return sb.toString();
    }

    public static String convertGb2Utf8(String gbk) {
        String utf8 = "";
        try {
            utf8 = new String(gbk2utf8(gbk), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return utf8;
    }

    public static byte[] gbk2utf8(String chinese) {
        char c[] = chinese.toCharArray();
        byte[] fullByte = new byte[3 * c.length];
        for (int i = 0; i < c.length; i++) {
            int m = (int) c[i];
            String word = Integer.toBinaryString(m);

            StringBuffer sb = new StringBuffer();
            int len = 16 - word.length();
            for (int j = 0; j < len; j++) {
                sb.append("0");
            }
            sb.append(word);
            sb.insert(0, "1110");
            sb.insert(8, "10");
            sb.insert(16, "10");

            String s1 = sb.substring(0, 8);
            String s2 = sb.substring(8, 16);
            String s3 = sb.substring(16);

            byte b0 = Integer.valueOf(s1, 2).byteValue();
            byte b1 = Integer.valueOf(s2, 2).byteValue();
            byte b2 = Integer.valueOf(s3, 2).byteValue();
            byte[] bf = new byte[3];
            bf[0] = b0;
            fullByte[i * 3] = bf[0];
            bf[1] = b1;
            fullByte[i * 3 + 1] = bf[1];
            bf[2] = b2;
            fullByte[i * 3 + 2] = bf[2];

        }
        return fullByte;
    }

    public static boolean equalsIgnoreNull(String str1, String str2) {
        if (StringUtils.isEmpty(str1) && StringUtils.isEmpty(str2))
            return true;

        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static String getStringFromReader(BufferedReader reader, String startStr, String endStr) {
        String str = "服务器正在升级，请稍候再试";
        StringBuffer sb = new StringBuffer(512);

        try {
            String s = "";
            while ((s = reader.readLine()) != null) {
                sb.append(StringUtils.trim(s));
            }

            //String htmlContent = IOUtils.toString(inStream,"gb2312");
            //String htmlContent = StringUtils.convertGb2Utf8(sb.toString());
            String htmlContent = sb.toString();

            Pattern p = Pattern.compile(startStr + ".*" + endStr);
            Matcher m = p.matcher(htmlContent);

            int count = 0;
            while (m.find()) {
                str = m.group();
                count++;
                break;
            }

            if (count > 0) {
                int lenStart = startStr.length();
                int lenEnd = endStr.length();

                str = str.substring(lenStart, (str.length() - lenEnd));
            }

            reader.close();
        } catch (java.io.IOException e) {
            System.out.println("提取的内容出错");
            e.printStackTrace();
        }

        return str;
    }

    public static String getStringFromStr(String str, String startStr, String endStr) {
        String result = "";

        Pattern p = Pattern.compile(startStr + ".*" + endStr);
        Matcher m = p.matcher(str);

        int count = 0;
        while (m.find()) {
            str = m.group();
            count++;
            break;
        }

        if (count > 0) {
            int lenStart = startStr.length();
            int lenEnd = endStr.length();

            result = str.substring(lenStart, (str.length() - lenEnd));
        }

        return result;
    }

    public static String getStringFromReader(BufferedReader reader) {
        StringBuffer sb = new StringBuffer(512);

        try {
            String s = "";
            while ((s = reader.readLine()) != null) {
                sb.append(StringUtils.trim(s));
            }

            reader.close();
        } catch (java.io.IOException e) {
            System.out.println("提取的内容出错");
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * XML标记分隔符号的转义序列
     * &amp; 通常用来替换字符&（除了在CDATA部分中—本章稍后将详细介绍）
     * &lt; 通常用来替换字符小于号（ <）（除了在CDATA部分中）
     * &gt; 可能用来替换字符大于号（ >）—在CDATA部分中，如果>紧跟着字符串“] ]”就必须使用该实体
     * &apos; 可用来替换字符串中的单引号（ '）
     * &quot; 可用来替换字符串中的字符双引号（ "）
     * @param str String
     * @return String
     */
    public static String encodeQuotedXMLString(String str) {
        StringBuffer sb = new StringBuffer(64);

        int i;
        int c;
        for (i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c == '\"')
                sb.append("&quot;");
            else if (c == '\'')
                sb.append("&apos;");
            else if (c == '>')
                sb.append("&gt;");
            else if (c == '<')
                sb.append("&lt;");
            else if (c == '&')
                sb.append("&amp;");
            else
                sb.append((char) c);
        }

        return sb.toString();
    }

    public static String reflectionToString(Object obj) {
        return ToStringBuilder.reflectionToString(obj, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static String htmlToPlainText(String content) throws EduException {
        try {
            StringBuffer sb = new StringBuffer(512);

            Parser parser = Parser.createParser(content, "utf-8");
            //Parser parser = Parser.createParser(content, null);
            NodeFilter textFilter = new NodeClassFilter(TextNode.class);
            NodeList list = parser.extractAllNodesThatMatch(textFilter);

            Node[] nodes = list.toNodeArray();

            for (int i = 0; i < nodes.length; i++) {
                String line = "";
                Node node = nodes[i];

                if (node instanceof TextNode) {
                    TextNode textnode = (TextNode) node;
                    line = textnode.getText();
                    //line.replaceAll("(&apos;)","'");
                }

                sb.append(line);
                sb.append("\n");
            }

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new EduException("html parse error.");
        }
    }

    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    public static String normalizeName(String name){
        return name.replaceAll("\\^|\"|'|:|;|<|>|@|\\*|%|!|#|\\$|,|\\?|&","");
    }
    
    public static boolean isPhoneNumber(String input){  
	    String regex="1([\\d]{10})";  
	    Pattern p = Pattern.compile(regex); 
	    Matcher m = p.matcher(input);  
	    
	    return m.find();//boolean 
	}  

    /**
     * Ascii转换为字符串
     * @param value
     * @return
     */
    public static String asciiToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }
    public static void main(String[] args) throws Exception {

    }


}
