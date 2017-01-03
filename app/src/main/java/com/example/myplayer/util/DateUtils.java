package com.example.myplayer.util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/29.
 */
public class DateUtils {


    /**
     * 将日期字符串转换为Date类型
     *
     * @param dateStr 日期字符串
     * @param type    日期字符串格式
     * @return Date对象
     */
    @SuppressLint("SimpleDateFormat")
    public static Date transform(String dateStr, String type) {
        SimpleDateFormat df = new SimpleDateFormat(type);
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }













    /**
     * 将Date类型转换为日期字符串
     *
     * @param date Date对象
     * @param type 需要的日期格式
     * @return 按照需求格式的日期字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDate(Date date, String type) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(type);
            return df.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 将long类型的时间转为01:22:33
     * @param duration
     * @return
     */
    public static String formatVideoDuration(long duration){
        int HOUR = 60*60*1000;//1小时所占的毫秒数
        int MINUTE = 60*1000;//1分钟所占的毫秒数
        int SECOND = 1000;//1秒

        //1.先算出多少小时，然后拿剩余的时间去算分钟
        int hour = (int) (duration/HOUR);//得到多少小时
        long remaintTime = duration%HOUR;//算完小时剩余的时间
        //2.算出多少分钟后，拿剩余的时间去算秒
        int minute = (int) (remaintTime/MINUTE);//得到多少分钟
        remaintTime = remaintTime%MINUTE;//算完分钟得到的时间
        //3.算出多少秒
        int second = (int) (remaintTime/SECOND);//得到多少秒

        //字符串格式的过程
        if(hour==0){
            //转为02:33格式
            return String.format("%02d:%02d", minute,second);
        }else {
            return String.format("%02d:%02d:%02d", hour,minute,second);
        }
    }

    /**
     * 格式化当前时间
     * @return
     */
    public static String formatSystemTime(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 去掉音乐名称的后缀名
     * @param name
     * @return
     */
    public static String formatAudioName(String name){
        int lastDotIndex = name.lastIndexOf(".");
        return name.substring(0,lastDotIndex);
    }
}
