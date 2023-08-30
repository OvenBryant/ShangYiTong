package com.bryant.yygh.order.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Copyright (C), 2022-2022, 西南科技大学
 * FileName: DateUtil
 * Author:   swust-liuchuan
 * Date:     2022/9/10 10:04
 * Description: 日期工具类
 * History:
 */

public class DateUtil {
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}
