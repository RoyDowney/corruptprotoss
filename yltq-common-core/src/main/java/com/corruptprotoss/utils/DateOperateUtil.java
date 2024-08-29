package com.corruptprotoss.utils;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author RoyDowney
 * @date 2024/6/20
 */
public class DateOperateUtil {

    //DateContinuityChecker
    public static boolean hasConsecutive12Months(LocalDate[] dates) {
        if (dates == null || dates.length < 12) {
            return false;
        }
        Arrays.sort(dates);
        int maxConsecutive = 0;
        int currentConsecutive = 1;
        for (int i = 1; i < dates.length; i++) {
            long monthsBetween = ChronoUnit.MONTHS.between(dates[i - 1], dates[i]);
            if (monthsBetween == 1) {
                currentConsecutive++;
                maxConsecutive = Math.max(maxConsecutive, currentConsecutive);
            } else {
                currentConsecutive = 1;
            }
        }
        return maxConsecutive >= 12;
    }

    public static void main(String[] args) {
//        LocalDate[] dates = {
//                LocalDate.of(2021, 1, 1),
//                LocalDate.of(2021, 2, 1),
//                LocalDate.of(2021, 3, 1),
//                LocalDate.of(2021, 4, 1),
//                LocalDate.of(2021, 5, 1),
//                LocalDate.of(2021, 6, 1),
//                LocalDate.of(2021, 7, 1),
//                LocalDate.of(2021, 8, 1),
//                LocalDate.of(2021, 9, 1),
//                LocalDate.of(2021, 10, 1),
//                LocalDate.of(2021, 11, 1),
//                LocalDate.of(2021, 12, 1),
//                LocalDate.of(2022, 12, 1)
//        };
//
//        if (hasConsecutive12Months(dates)) {
//            System.out.println("连续12个月");
//        } else {
//            System.out.println("不连续");
//        }

//        List<String> nextDayList = nextDayList(3, null);
//        System.out.println("nextDayList:"+nextDayList);
        //nextDayList:[2024-08-11 00:00:00, 2024-08-12 00:00:00, 2024-08-13 00:00:00]

        LocalDate startDate = LocalDate.of(2024, 8, 1);
        LocalDate endDate = LocalDate.of(2024, 8, 5);
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate date = startDate.plusDays(1); date.isEqual(endDate) || date.isBefore(endDate); date = date.plusDays(1)) {
            dates.add(date);
        }
        for (LocalDate date : dates) {
            System.out.println(date);
        }
    }

    public static boolean isMonthsContinuous(LocalDate[] dates) {
        if (dates.length < 12) {
            return false;
        }
        Arrays.sort(dates);
        int monthsBetween = 0;
        for (int i = 1; i < dates.length; i++) {
            monthsBetween += dates[i].isBefore(dates[i - 1]) ? -1 : 1;
        }
        if (monthsBetween == 11) {
            return true;
        }
        int lastMonthIndex = dates.length - 1;
        while (lastMonthIndex > 0 && dates[lastMonthIndex].isBefore(dates[lastMonthIndex - 1])) {
            lastMonthIndex--;
        }
        LocalDate[] trimmedDates = Arrays.copyOfRange(dates, 0, lastMonthIndex + 1);
        monthsBetween = 0;
        for (int i = 1; i < trimmedDates.length; i++) {
            monthsBetween += trimmedDates[i].isBefore(trimmedDates[i - 1]) ? -1 : 1;
        }
        return monthsBetween == 11;
    }


    public static List<String> nextDayList(Integer generateDays, LocalDate lastDate) {
        //通过当天日期 加上 预生成天数 返回未来的日期集合
        int days = generateDays;
        LocalDate currentDate = LocalDate.now();
        if (lastDate != null) {
            currentDate = lastDate;
        }
        LocalDateTime startOfDay = currentDate.atStartOfDay();
        List<LocalDateTime> nextDays = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            LocalDateTime nextDay = startOfDay.plusDays(i);
            nextDays.add(nextDay);
        }
        List<String> nextDayList = new ArrayList<>();
        for (LocalDateTime dateTime : nextDays) {
            nextDayList.add(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        return nextDayList;
    }

    public static List<LocalDate> periodLocalDateList(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate date = startDate.plusDays(1); date.isEqual(endDate) || date.isBefore(endDate); date = date.plusDays(1)) {
            dates.add(date);
        }
        return dates;
    }

    public static Date dateNowAddDays(int days) {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(days);
        return Date.from(futureDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date localDateToDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDate localDateNowAddDays(int days) {
        LocalDate currentDate = LocalDate.now();
        return currentDate.plusDays(days);
    }

    // 两个 date日期 类型 相减 返回 天数差值
    public static long getDaysBetween(Date date1, Date date2) {
        LocalDate localDate1 = dateToLocalDate(date1);
        LocalDate localDate2 = dateToLocalDate(date2);
        return getDaysBetween(localDate1, localDate2);
    }

    // 两个 date日期 类型 相减 返回 天数差值
    public static long getDaysBetween(LocalDate date1, LocalDate date2) {
        return ChronoUnit.DAYS.between(date1, date2);
    }

}

