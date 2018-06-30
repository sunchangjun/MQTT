package framework.core.utils;

import java.util.Calendar;

/**
 * 工作日、休息日统计
 */
public class WorkDayUtils {

    // ================================================================
    // Constants
    // ================================================================

    /** 星期中文 */
    private static final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    // ================================================================
    // Fields
    // ================================================================

    // ================================================================
    // Constructors
    // ================================================================

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    // ================================================================
    // Public or Protected Methods
    // ================================================================

    /**
     * 获取日期之间的天数
     */
    public int getDaysBetween(Calendar start, Calendar end) {
        if (start.after(end)) { // swap dates so that start and end
            Calendar swap = start;
            start = end;
            end = swap;
        }
        int days = end.get(Calendar.DAY_OF_YEAR) - start.get(Calendar.DAY_OF_YEAR);
        int y2 = end.get(Calendar.YEAR);
        if (start.get(Calendar.YEAR) != y2) {
            start = (Calendar) start.clone();
            do {
                days += start.getActualMaximum(Calendar.DAY_OF_YEAR);
                start.add(Calendar.YEAR, 1);
            } while (start.get(Calendar.YEAR) != y2);
        }
        return days;
    }

    /**
     * 获取工作日
     * @return 工作日
     */
    public int getWorkingDay(Calendar start, Calendar end) {
        int result;
        if (start.after(end)) { // swap dates so that start and end
            Calendar swap = start;
            start = end;
            end = swap;
        }
        int charge_start_date = 0;// 开始日期的日期偏移量
        int charge_end_date = 0;// 结束日期的日期偏移量
        // 日期不在同一个日期内
        int stmp;
        int etmp;
        stmp = 7 - start.get(Calendar.DAY_OF_WEEK);
        etmp = 7 - end.get(Calendar.DAY_OF_WEEK);
        if (stmp != 0 && stmp != 6) {// 开始日期为星期六和星期日时偏移量为0
            charge_start_date = stmp - 1;
        }
        if (etmp != 0 && etmp != 6) {// 结束日期为星期六和星期日时偏移量为0
            charge_end_date = etmp - 1;
        }
        // }
        result = (getDaysBetween(this.getNextMonday(start), this.getNextMonday(end)) / 7) * 5 + charge_start_date - charge_end_date;
        // System.out.println("charge_start_date>" + charge_start_date);
        // System.out.println("charge_end_date>" + charge_end_date);
        return result;
    }

    /**
     * 获取中文日期
     *
     * @return 中文日期
     */
    public String getChineseWeek(Calendar date) {

        int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
        // System.out.println(dayNames[dayOfWeek - 1]);
        return dayNames[dayOfWeek - 1];
    }

    /**
     * 获得日期的下一个星期一的日期
     */
    public Calendar getNextMonday(Calendar date) {
        Calendar result;
        result = date;
        do {
            result = (Calendar) result.clone();
            result.add(Calendar.DATE, 1);
        } while (result.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY);
        return result;
    }

    /**
     * 获取休息日
     */
    public int getHolidays(Calendar start, Calendar end) {
        return this.getDaysBetween(start, end) - this.getWorkingDay(start, end);
    }

    // ================================================================
    // Getter & Setter
    // ================================================================

    // ================================================================
    // Private Methods
    // ================================================================

    // ================================================================
    // Inner or Anonymous Class
    // ================================================================

    // ================================================================
    // Test Methods
    // ================================================================
}
