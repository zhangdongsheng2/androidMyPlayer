package com.example.myplayer.util;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @date 2016年3月21日 下午1:52:37
 * @description 格式化的工具类
 */
public class FormatHelper {

    /**
     * 2015-11-19 pangli
     * 提供精确的小数位四舍五入处理。
     *
     * @param input 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */

    public static double round(Double input, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = null == input ? new BigDecimal("0.0") : new BigDecimal(Double.toString(input));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * double格式化1位小数，转为string
     *
     * @param d
     * @return
     */
    public static String formatDouble1(Double d) {
        if (d == null) {
            return "";
        }
        return new DecimalFormat("#0.0").format(d);
    }

    public static String formatDoubleZeroOrTwo(Double d) {
        if (d == null) {
            return "";
        }
        return new DecimalFormat("#0.##").format(d);
    }

    /**
     * 2015-11-19 pangli
     * 提供精确的小数位四舍五入处理。
     *
     * @param input 需要四舍五入的数字
     * @return 四舍五入后的结果
     */
    public static double round(Double input) {
        BigDecimal b = new BigDecimal(input);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 去除Double 显示科学计数法
     *
     * @param value
     * @return
     */
    public static String formatNotScientific(Double value) {
        if (value != null) {
            if (value.doubleValue() != 0.00) {
                DecimalFormat df = new DecimalFormat("########0.00");
                return df.format(value.doubleValue());
            } else {
                return "0.00";
            }
        }
        return "";
    }

    /**
     * 自带捕获异常
     *
     * @param string
     * @return
     */
    public static Double parseDouble(String string) {
        double d = 0;
        if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string.trim())) {
            return d;
        }
        try {
            d = Double.parseDouble(string.trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return d;
    }

    /**
     * 自带捕获异常
     *
     * @param string
     * @return
     */
    public static Integer parseInteger(String string) {
        int d = 0;
        if (TextUtils.isEmpty(string) || TextUtils.isEmpty(string.trim())) {
            return d;
        }
        try {
            d = Integer.parseInt(string.trim());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return d;
    }


    /**
     * int格式化2位，转为string
     *
     * @param d
     * @return
     */
    public static String formatIntTwo(Integer d) {
        if (d == null) {
            return "";
        }
        return new DecimalFormat("00").format(d);
    }

    /**
     * 防止Integer 空指针
     *
     * @param integer
     * @return
     */
    public static int getInt(Integer integer) {
        if (integer == null) return 0;
        return integer;
    }
}
