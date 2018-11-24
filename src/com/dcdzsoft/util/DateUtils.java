package com.dcdzsoft.util;

import java.util.*;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.text.ParseException;

/**
 * <p>
 * Title: 智能自助包裹柜系统
 * </p>
 *
 * <p>
 * Description:
 * </p>
 *
 * <p>
 * Copyright: Copyright (c) 2014
 * </p>
 *
 * <p>
 * Company: 杭州东城电子有限公司
 * </p>
 *
 * @author wangzl
 * @version 1.0
 */
public class DateUtils {

    /*
     * public static java.util.Calendar utcCal = null; static{ utcCal =
     * Calendar.getInstance();
     * 
     * // get the TimeZone for "America/Los_Angeles" TimeZone tz =
     * TimeZone.getTimeZone("UTC"); utcCal.setTimeZone(tz); }
     */
    public static java.sql.Date minDate = null;

    static {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1900);
        c.set(Calendar.DATE, 1);
        // c.set(Calendar.MONTH, 1);
        c.set(Calendar.MONTH, 0);

        minDate = new java.sql.Date(c.getTime().getTime());
    }

    private DateUtils() {
    }

    public static String nowDate() {
        return nowDate("-");
    }

    public static String nowDate(String sper) {
        GregorianCalendar Time = new GregorianCalendar();

        String s_nowD = "", s_nowM = "";
        if (sper == null) {
            sper = "-";
        } else {
            sper = sper.trim();
        }

        int nowY = Time.get(Calendar.YEAR);
        int nowM = Time.get(Calendar.MONTH) + 1;

        if (nowM < 10) {
            s_nowM = "0" + nowM;
        } else {
            s_nowM = "" + nowM;
        }

        int nowD = Time.get(Calendar.DATE);
        if (nowD < 10) {
            s_nowD = "0" + nowD;
        } else {
            s_nowD = "" + nowD;
        }

        String result = nowY + sper + s_nowM + sper + s_nowD;

        return result;
    }

    public static String nowTime() {
        return nowTime(":");
    }

    public static String nowTime(String sperate) {
        GregorianCalendar Time = new GregorianCalendar();

        int i_hour = Time.get(Calendar.HOUR); // Hours() ;
        int i_tag = Time.get(Calendar.AM_PM);
        if (i_tag == 1 && i_hour < 12)
            i_hour = i_hour + 12;

        int i_minutes = Time.get(Calendar.MINUTE); // Minutes() ;
        int i_seconds = Time.get(Calendar.SECOND); // Seconds() ;
        int i_mill = Time.get(Calendar.MILLISECOND);

        String s_time = "";
        if (i_hour >= 0 && i_hour < 10)
            s_time = "0";
        s_time += "" + i_hour + sperate;
        if (i_minutes >= 0 && i_minutes < 10)
            s_time += "0";
        s_time += "" + i_minutes + sperate;
        if (i_seconds >= 0 && i_seconds < 10)
            s_time += "0";
        s_time += "" + i_seconds + "." + i_mill;

        return s_time;
    }

    public static int getDayCountFromYearMonth(int nYear, int nMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, nYear);
        c.set(Calendar.DATE, 1);
        c.set(Calendar.MONTH, nMonth - 1);

        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static java.sql.Date changeMonth(java.sql.Date date, int month) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(cal.MONTH, month);

        // SimpleDateFormat sFmt = new SimpleDateFormat("YYYYMMDD");

        // return sFmt.format(cal.getTime());
        java.sql.Date newDate = new java.sql.Date(cal.getTime().getTime());

        return newDate;
    }

    public static java.util.Calendar getUTCCal() {
        java.util.Calendar utcCal = null;
        utcCal = Calendar.getInstance();

        // get the TimeZone for "America/Los_Angeles"
        TimeZone tz = TimeZone.getTimeZone("UTC");
        utcCal.setTimeZone(tz);

        return utcCal;
    }

    public static java.sql.Timestamp convert2UTCTimestamp(java.sql.Timestamp value) {
        // create a default calendar
        Calendar defaultCal = Calendar.getInstance();
        // set the time in the default calendar
        defaultCal.setTime(value);

        Calendar cal = getUTCCal();
        /*
         * Now we can pull the pieces of the date out of the default calendar
         * and put them into the user provided calendar
         */
        cal.set(Calendar.YEAR, defaultCal.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, defaultCal.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, defaultCal.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, defaultCal.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, defaultCal.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, defaultCal.get(Calendar.SECOND));

        return new java.sql.Timestamp(cal.getTime().getTime());
    }

    public static java.sql.Date convert2UTCDate(java.sql.Date value) {
        // create a default calendar
        Calendar defaultCal = Calendar.getInstance();
        // set the time in the default calendar
        defaultCal.setTime(value);

        Calendar cal = getUTCCal();
        /*
         * Now we can pull the pieces of the date out of the default calendar
         * and put them into the user provided calendar
         */
        cal.set(Calendar.YEAR, defaultCal.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, defaultCal.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, defaultCal.get(Calendar.DAY_OF_MONTH));

        /*
         * This looks a little odd but it is correct - Calendar.getTime()
         * returns a Date...
         */

        return new java.sql.Date(cal.getTime().getTime());
    }

    public static java.sql.Date toSQLDate(java.sql.Timestamp timestamp) {
        long milliseconds = timestamp.getTime() + (timestamp.getNanos() / 1000000);
        return new java.sql.Date(milliseconds);
    }

    public static java.sql.Date toSQLDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * timeZoneOffset表示时区，如中国一般使用东八区，因此timeZoneOffset就是8
     * 
     * @param timeZoneOffset
     *            int
     * @return String
     */
    public String getFormatedDateString(int timeZoneOffset) {
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }
        TimeZone timeZone;
        String[] ids = TimeZone.getAvailableIDs(timeZoneOffset * 60 * 60 * 1000);
        if (ids.length == 0) {
            // if no ids were returned, something is wrong. use default TimeZone
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(timeZoneOffset * 60 * 60 * 1000, ids[0]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(timeZone);
        return sdf.format(new java.util.Date());
    }

    public static String dateToString(java.util.Date date, String _timeZone) {
        if (date == null)
            return "";

        TimeZone timeZone = null;
        if (StringUtils.isEmpty(_timeZone)) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = TimeZone.getTimeZone(_timeZone);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(timeZone);
        // TimeZone.setDefault(timeZone);

        return sdf.format(date);
    }

    public static String dateToString(java.util.Date date) {
        return DateUtils.dateToString(date, null);
    }

    public static String dateToString1(java.util.Date date) {
        if (date == null)
            return "";

        TimeZone timeZone = null;
        timeZone = TimeZone.getDefault();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        sdf.setTimeZone(timeZone);
        // TimeZone.setDefault(timeZone);

        return sdf.format(date);

    }

    public static String dateToString2(java.util.Date date) {
        if (date == null)
            return "";

        TimeZone timeZone = null;
        timeZone = TimeZone.getDefault();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setTimeZone(timeZone);
        // TimeZone.setDefault(timeZone);

        return sdf.format(date);

    }

    public static String dateToString(java.sql.Date date, String _timeZone) {
        return DateUtils.dateToString(new java.util.Date(date.getTime()), _timeZone);
    }

    public static String dateToString(java.sql.Date date) {
        return DateUtils.dateToString(new java.util.Date(date.getTime()));
    }

    public static String timestampToString(java.util.Date date, String _timeZone) {
        if (date == null)
            return "";

        TimeZone timeZone = null;
        if (StringUtils.isEmpty(_timeZone)) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = TimeZone.getTimeZone(_timeZone);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        // TimeZone.setDefault(timeZone);

        return sdf.format(date);
    }

    public static String timestampToString4SSS(java.util.Date date) {
        if (date == null)
            return "";

        TimeZone timeZone = TimeZone.getDefault();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        sdf.setTimeZone(timeZone);
        // TimeZone.setDefault(timeZone);

        return sdf.format(date);
    }

    public static String timestampToDateString(java.util.Date date) {
        if (date == null)
            return "";

        TimeZone timeZone = timeZone = TimeZone.getDefault();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(timeZone);
        // TimeZone.setDefault(timeZone);

        return sdf.format(date);
    }

    public static String timestampToString(java.util.Date date) {
        return DateUtils.timestampToString(date, null);
    }

    public static String timestampToString(java.sql.Timestamp timestamp, String _timeZone) {
        long milliseconds = timestamp.getTime() + (timestamp.getNanos() / 1000000);

        return DateUtils.timestampToString(new java.util.Date(milliseconds), _timeZone);
    }

    public static String timestampToString(java.sql.Timestamp timestamp) {
        long milliseconds = timestamp.getTime() + (timestamp.getNanos() / 1000000);

        return DateUtils.timestampToString(new java.util.Date(milliseconds));
    }

    public static String timestampToGMTString(java.sql.Timestamp timestamp) {
        if (timestamp == null)
            return "";

        TimeZone timeZone = null;
        timeZone = TimeZone.getTimeZone("GMT:00");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(timeZone);
        // TimeZone.setDefault(timeZone);

        long milliseconds = timestamp.getTime() + (timestamp.getNanos() / 1000000);
        return sdf.format(new java.util.Date(milliseconds)) + " GMT";
    }

    public static java.sql.Date getMinDate() {
        return minDate;
    }

    public static java.util.Date addDate(java.util.Date date, int day) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return c.getTime();
    }

    public static java.sql.Date addDate(java.sql.Date date, int day) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
        return new java.sql.Date(c.getTime().getTime());
    }

    public static java.sql.Timestamp addTimeStamp(java.sql.Timestamp date, int day) {
        java.util.Calendar cal = java.util.Calendar.getInstance();

        cal.setTimeInMillis(getMillis(new java.util.Date(date.getTime())) + ((long) day) * 24 * 3600 * 1000);

        return new java.sql.Timestamp(cal.getTime().getTime());
    }

    public static java.sql.Timestamp decreaseSysDateTime(java.sql.Timestamp date, int day) {
        java.util.Calendar cal = java.util.Calendar.getInstance();

        cal.setTimeInMillis(getMillis(new java.util.Date(date.getTime())) + ((long) day) * 24 * 3600 * 1000);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        java.sql.Timestamp sysDateTime = new java.sql.Timestamp(cal.getTime().getTime());

        return sysDateTime;
    }

    public static java.sql.Timestamp addTimeStamp(java.sql.Timestamp date, double day) {
        java.util.Calendar cal = java.util.Calendar.getInstance();

        cal.setTimeInMillis(getMillis(new java.util.Date(date.getTime())) + (long) (day * 24 * 3600 * 1000));

        return new java.sql.Timestamp(cal.getTime().getTime());
    }

    public static java.sql.Timestamp addTimeStampBySecond(java.sql.Timestamp date, int second) {
        java.util.Calendar cal = java.util.Calendar.getInstance();

        cal.setTimeInMillis(getMillis(new java.util.Date(date.getTime())) + ((long) second) * 1000);

        return new java.sql.Timestamp(cal.getTime().getTime());
    }

    public static java.sql.Timestamp addTimeStampByHour(java.sql.Timestamp date, int hour) {
        java.util.Calendar cal = java.util.Calendar.getInstance();

        cal.setTimeInMillis(getMillis(new java.util.Date(date.getTime())) + ((long) hour) * 3600 * 1000);

        return new java.sql.Timestamp(cal.getTime().getTime());
    }

    public static int diffDate(java.util.Date date, java.util.Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    public static int diffHour(java.util.Date start, java.util.Date end) {
        return (int) ((getMillis(end) - getMillis(start)) / (3600 * 1000));
    }

    public static int diffHour(java.sql.Timestamp start, java.sql.Timestamp end) {
        return (int) ((getMillis(end) - getMillis(start)) / (3600 * 1000));
    }

    public static int diffMinute(java.util.Date start, java.util.Date end) {
        return (int) ((getMillis(end) - getMillis(start)) / (60 * 1000));
    }

    public static long getMillis(java.util.Date date) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    public static java.sql.Date stringToDate(String value, String fmt) {
        SimpleDateFormat sd = new SimpleDateFormat(fmt);

        java.util.Date date = null;

        try {
            date = sd.parse(value);

            return new java.sql.Date(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static java.sql.Date stringToDate(String value) {
        java.sql.Date date = stringToDate(value, "yyyy-MM-dd");
        if(date==null){
            return stringToDate(value, "yyyyMMdd");
        }else{
            return date;
        }
    }

    public static java.sql.Date getUTCBeginDate() {
        return stringToDate("19700101");
    }

    public static java.sql.Timestamp stringToTimestamp(String value, String fmt) {
        SimpleDateFormat sd = new SimpleDateFormat(fmt);

        java.util.Date date = null;

        try {
            date = sd.parse(value);

            return new java.sql.Timestamp(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static java.sql.Timestamp stringToTimestamp(String value) {
        return stringToTimestamp(value, "yyyy-MM-dd HH:mm:ss");
    }

    public static java.sql.Timestamp stringToTimestamp4SSS(String value) {
        return stringToTimestamp(value, "yyyy-MM-dd HH:mm:ss:SSS");
    }

    public static int currentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int currentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getHour(java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static void main(String[] args) throws Exception {
        // TimeZone timeZone = TimeZone.getDefault();

        // System.out.println(timeZone.getDisplayName());

        /*
         * String[] ids = timeZone.getAvailableIDs(); for(int
         * i=0;i<ids.length;i++){ System.out.println(ids[i]); }
         */

        // System.out.println(TimeZone.getTimeZone("GMT+08:00").getID());
        java.util.Date nowDate = new java.util.Date();
        System.out.println(DateUtils.getHour(nowDate));

    }

}
