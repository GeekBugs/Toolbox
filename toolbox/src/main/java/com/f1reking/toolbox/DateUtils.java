package com.f1reking.toolbox;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by F1ReKing on 2016/5/28.
 */
public class DateUtils {

    public static Calendar today = Calendar.getInstance();

    /*获取日期*/
    public static String getDay(String date) {
        String h;
        String[] day = date.split("-");
        h = day[2];
        return h;
    }

    /*获取月份*/
    public static String getMonth(String date) {
        String m;
        String[] day = date.split("-");
        m = day[1];
        return m;
    }

    /*获取年份*/
    public static String getYear(String date) {
        String y;
        String[] day = date.split("-");
        y = day[0];
        return y;
    }

    /*获取当前系统时间*/
    public static String getSysDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
        return sdf.format(date);
    }

    /*格式化日期时间*/
    public static String formatDatetime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return sdf.format(date);
    }

    public static String formatDatetime(String date) throws ParseException {
        DateFormat fmt = new SimpleDateFormat("yyyy年MM月dd日");
        Date d = fmt.parse(date);
        return d.toString();
    }

    public static String formatDatetime(String date, int forid) {
        if (date == null || "".equals(date.trim())) {
            return "";
        } else {
            String str = "";
            str = date.substring(0, date.indexOf("."));
            String[] array = str.split(" ");
            String[] dates = array[0].split("-");
            switch (forid) {
                case 0:  //yyyy-MM-dd HH:mm:ss
                    str = date.substring(0, date.indexOf("."));
                    break;
                case 1:  //yyyy-MM-dd
                    str = date.substring(0, date.indexOf("."));
                    str = str.substring(0, str.indexOf(" "));
                    break;
                case 2:  //yyyy年MM月dd日 HH:mm:ss
                    str = dates[0] + "年" + dates[1] + "月" + dates[2] + "日 " + array[1];
                    break;
                case 3:  //yyyy年MM月dd日 HH:mm
                    str = dates[0] + "年" + dates[1] + "月" + dates[2] + "日 " + array[1].substring(0,
                        array[1].lastIndexOf(":"));
                    break;
                case 4:  //yyyy年MM月dd日 HH:mm:ss
                    str = dates[0] + "年" + dates[1] + "月" + dates[2] + "日 ";
                    break;
                default:
                    break;
            }
            return str;
        }
    }

    /*获取当前时间的毫秒*/
    public String getSysTimeMillise() {
        long i = System.currentTimeMillis();
        return String.valueOf(i);
    }

    /*获取星期几*/
    public static String getWeek() {
        Calendar cal = Calendar.getInstance();
        int i = cal.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                return "周日";
            case 2:
                return "周一";
            case 3:
                return "周二";
            case 4:
                return "周三";
            case 5:
                return "周四";
            case 6:
                return "周五";
            case 7:
                return "周六";
            default:
                return "";
        }
    }

    public static String formatCommentTime(String str) {

        Date date = parse(str, "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    public static Date parse(String str, String pattern, Locale locale) {
        if (str == null || pattern == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(pattern, locale).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
