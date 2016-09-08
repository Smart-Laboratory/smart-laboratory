package com.smart.webapp.util;

import com.smart.Constants;
import sun.util.resources.cldr.ne.CalendarData_ne_NP;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yuzh on 2016/9/7.
 */
public class GetReportTimeUtil {

    public String getReportTime(Date executeTime, String qbgsj) {
        if(qbgsj == null || qbgsj.isEmpty()) {
            return "该项目取报告时间未维护，请询问相关工作人员";
        }
        if(qbgsj.equals("2小时")) {
            Calendar calendar =  Calendar.getInstance();
            calendar.setTime(executeTime);
            calendar.add(Calendar.HOUR_OF_DAY, 2);
            return Constants.SDF.format(calendar.getTime());
        }
        if(qbgsj.equals("1小时")) {
            Calendar calendar =  Calendar.getInstance();
            calendar.setTime(executeTime);
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            return Constants.SDF.format(calendar.getTime());
        }
        if(qbgsj.equals("4小时")) {
            Calendar calendar =  Calendar.getInstance();
            calendar.setTime(executeTime);
            calendar.add(Calendar.HOUR_OF_DAY, 4);
            return Constants.SDF.format(calendar.getTime());
        }
        if(qbgsj.equals("次日")) {
            Calendar calendar =  Calendar.getInstance();
            calendar.setTime(executeTime);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 16);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 0);
            return Constants.SDF.format(calendar.getTime());
        }if(qbgsj.equals("次二日")) {
            Calendar calendar =  Calendar.getInstance();
            calendar.setTime(executeTime);
            calendar.add(Calendar.DAY_OF_MONTH, 2);
            calendar.set(Calendar.HOUR_OF_DAY, 16);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 0);
            return Constants.SDF.format(calendar.getTime());
        }

        return "该项目取报告时间未维护，请询问相关工作人员";
    }

}
