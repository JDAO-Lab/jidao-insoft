package com.inSoft;

import com.inSoft.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Map;

@SpringBootTest
public class DateUtilsTests {

    @Test
    public void testGetCurrentDate() {
        //获取指定当前格式日期
        System.out.println("当前日期（按格式输出）：");
        System.out.println(DateUtils.getCurrentDate("yyyyMMddHHmmss"));
        System.out.println(DateUtils.getCurrentDate("yyyyMMdd"));
        System.out.println(DateUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateUtils.getCurrentDate("yyyy-MM-dd"));
        //getCurrentTimestampInSeconds
        System.out.println("秒级时间戳："+DateUtils.getCurrentTimestampInSeconds());
        //getCurrentTimestampInMilliseconds
        System.out.println("毫秒级时间戳："+DateUtils.getCurrentTimestampInMilliseconds());
        //getTodayEndAsString
        System.out.println("今天结束时间："+DateUtils.getTodayEndAsString("yyyy-MM-dd HH:mm:ss"));
        //getStartDateAndEndTimeForDaysBefore
        Map<String, String> startDateAndEndTimeForDaysBefore = DateUtils.getStartDateAndEndTimeForDaysBefore(7, "yyyy-MM-dd HH:mm:ss");
        System.out.println("前7天时间："+startDateAndEndTimeForDaysBefore);
        //getStartDateAndEndTimeForDaysAfter
        Map<String, String> startDateAndEndTimeForDaysAfter = DateUtils.getStartDateAndEndTimeForDaysAfter(7, "yyyy-MM-dd HH:mm:ss");
        System.out.println("后7天时间："+startDateAndEndTimeForDaysAfter);
        //getDateBySeconds
        System.out.println("秒级时间戳转日期："+DateUtils.getDateBySeconds(-60L,"yyyy-MM-dd HH:mm:ss"));
        //获取指定天数后的日期
        System.out.println("获取指定天数后的日期："+DateUtils.getDateAfterDays(70));
        //getDateAfterDays
        System.out.println("获取指定天数后的日期："+DateUtils.getDateAfterDays(new Date(),71));
    }
}
