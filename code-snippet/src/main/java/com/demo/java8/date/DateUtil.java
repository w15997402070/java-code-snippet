package com.demo.java8.date;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 *  getToday()  获取今天的日期和时间
 *  caculateDateTime() 时间计算
 *  clockTest() 时钟类Clock
 *  getDayByParam() 获取指定日期
 *  dateCompare() 日期比较
 *  birthdayCompare() 判断生日
 *  formatter() 日期格式化
 */
@Slf4j
public class DateUtil {

    public static void main(String [] args){
//        getDayByParam();
    }

    /**
     * 获取今天的日期
     * 年/月/日
     */
    @Test
    public  void getToday(){
        LocalDate now = LocalDate.now();
        log.info("today : "+now);

        //年
        int year = now.getYear();
        log.info("year : "+year);

        //月
        int month = now.getMonthValue();
        log.info("month : "+month);

        //日
        int dayOfMonth = now.getDayOfMonth();
        int dayOfYear = now.getDayOfYear();
        log.info("dayOfYear : "+dayOfYear);
        log.info("dayOfMonth : "+dayOfMonth);
        //周
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        log.info("dayOfWeek : "+dayOfWeek.getValue());

        //当前时间
        LocalTime localTime = LocalTime.now();
        log.info("当前时间 : "+localTime);
        //当前日期加时间
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("当前日期和时间 : "+localDateTime);

        //当前时间戳
        Instant instant = Instant.now();
        log.info("当前时间戳 : "+instant.toEpochMilli());
        //YearMonth
        YearMonth yearMonth = YearMonth.now();
        log.info("YearMonth : "+yearMonth);
        log.info("本月有多少天 : "+yearMonth.lengthOfMonth());
        log.info("今年有多少天 : "+yearMonth.lengthOfYear());
        log.info("是否是闰年 : "+yearMonth.isLeapYear());

    }

    /**
     * 时间计算
     */
    @Test
    public void caculateDateTime(){
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("当前日期和时间 : "+localDateTime);
        LocalDateTime localDateTime1 = localDateTime.plusDays(2);
        log.info("当前日期和时间 2 天后的时间 : "+localDateTime1);
        LocalDateTime localDateTime2 = localDateTime.plusMonths(1);
        log.info("当前日期和时间 1 月后的时间 : "+localDateTime2);

        LocalDate localDate = LocalDate.of(2020,1,31);
        LocalDate localDate2 = localDate.plusMonths(1);
        log.info("指定日期和时间 1 月后的时间 : "+localDate2);

        //日期在之前还是之后
        //isBefore() 之前
        //isAfter() 之后
        //isEqual() 相等
        if (localDateTime1.isBefore(localDateTime2)){
            log.info("localDateTime1在前面 ");
        }else {
            log.info("localDateTime2在前面 ");
        }

        //计算日期间隔
        Period between = Period.between(localDateTime1.toLocalDate(), localDateTime2.toLocalDate());

        log.info("时间间隔-年 : "+between.getYears());
        log.info("时间间隔-月 : "+between.getMonths());
        log.info("时间间隔-天 : "+between.getDays());

    }

    /**
     * 时钟类Clock
     * 获取当前的时间戳
     */
    @Test
    public void clockTest(){
        Clock clock = Clock.systemUTC();
        log.info("当前时间戳 : "+ clock.millis());

        Clock clock1 = Clock.systemDefaultZone();
        log.info("当前时间戳 : "+ clock1.millis());
    }

    /**
     * 获取指定日期
     */
    public static void getDayByParam(){
        LocalDate paramDate = LocalDate.of(2020, 1, 17);
        log.info("paramDate : "+paramDate);
    }

    /**
     * 判断日期是否相等
     */
    @Test
    public  void dateCompare(){
        LocalDate now = LocalDate.now();
        LocalDate paramDate = LocalDate.of(2020, 1, 16);

        if (now.equals(paramDate)){
            log.info("日期相等");
        }else {
            log.info("日期不相等");
        }
    }

    @Test
    public void birthdayCompare(){
        LocalDate now = LocalDate.now();
        LocalDate paramDate = LocalDate.of(2020, 1, 17);
        MonthDay monthDay = MonthDay.of(paramDate.getMonth(), paramDate.getDayOfMonth());
        MonthDay from = MonthDay.from(now);
        if (from.equals(monthDay)){
            log.info("今天生日");
        }else {
            log.info("今天不是生日");
        }
    }


    /**
     * 日期格式化
     */
    @Test
    public void formatter(){
        //日期解析
        String date="20200117";
        LocalDate parse = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
        log.info("日期为 : "+parse);

        //日期格式化
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String format = localDateTime.format(dateTimeFormatter);
        log.info("格式化后 ; "+format);
        //字符串转日期
        LocalDate parse1 = LocalDate.parse(format, dateTimeFormatter);
        log.info("字符串格式化后 ; "+parse1);

    }
}
