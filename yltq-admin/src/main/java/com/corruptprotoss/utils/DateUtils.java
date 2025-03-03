package com.corruptprotoss.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 * 时间工具类
 *
 * @version 1.0.0
 * @anthor gongyankai
 * @date 2023-10-13 16:23
 */
public class DateUtils {

    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final String SHORT_PATTERN = "yyyy-MM-dd";


    public static String dateToString(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } else {
            return "";
        }
    }

    /**
     * 获取当前年龄
     * @param startDate
     * @return
     */
    public static Integer getAge(Date startDate){
        LocalDate localDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(localDate, LocalDate.now());

        // 获取年数差异
        int years = period.getYears();

        return years;
    }

    /**
     * 字符串转Date
     * @param time
     * @return
     */
    public static Date stringToDate(String time,String dateFormat) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            Date date = simpleDateFormat.parse(time);
            return date;
        }catch (Exception e){
            return null;
        }
    }
}
