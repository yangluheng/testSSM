package com.xy.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    /**
     * 日期转换为字符串
     * @param date
     * @param patton
     * @return
     */
    public static String dateToString(Date date,String patton){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(patton);
        String format=simpleDateFormat.format(date);
        return format;
    }

    /**
     *
     *字符串转换为日期
     * @param s
     * @param patton
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String s,String patton) throws ParseException{
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(patton);
        Date parse=simpleDateFormat.parse(s);
        return parse;
    }
}
