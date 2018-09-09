package com.example.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SatTimeConvert {

    private static final long initTime = 1483200000;//星上时间以2017年1月1日0时0分0秒为初始，initTime是这个时刻与历元时刻的比较，以秒为单位

    public static void main(String[] args) {
        try {
            String sDt = "2017-01-01 00:00:00";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            Date dt2 = format.parse(sDt);
            long lTime = dt2.getTime() / 1000;
            System.out.println("2017年历元时刻：" + lTime);
            System.out.println("当前时间转换为卫星时间：" + dateToSatTime(new Date()));
            System.out.println("卫星初始时间：" + satTimeToDate(0));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String dateStr = "2013-1-31 22:17:14";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Date dateTmp = dateFormat.parse(dateStr);
            System.out.println(dateTmp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 星上时间转换为时间类型，星上时间是与2017年1月1日0时0分0秒比较得到的
     *
     * @param satTime
     * @return
     */
    public static Date satTimeToDate(long satTime) throws ParseException {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = format.format((satTime + initTime) * 1000);
        date = format.parse(d);
        return date;
    }

    /**
     * 时间类型转换为星上时间，星上时间是与2017年1月1日0时0分0秒比较得到的
     *
     * @param date
     * @return
     */
    public static long dateToSatTime(Date date) {
        long time = date.getTime() / 1000;
        return time - initTime;
    }
}
