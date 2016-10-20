package com.smart.webapp.util;

import com.smart.Constants;

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
        Calendar calendar =  Calendar.getInstance();
        calendar.setTime(executeTime);
        if(qbgsj.equals("2小时")) {
            calendar.add(Calendar.HOUR_OF_DAY, 2);
            return Constants.SDF.format(calendar.getTime());
        }
        if(qbgsj.equals("1小时")) {
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            return Constants.SDF.format(calendar.getTime());
        }
        if(qbgsj.equals("4小时")) {
            calendar.add(Calendar.HOUR_OF_DAY, 4);
            return Constants.SDF.format(calendar.getTime());
        }
        if(qbgsj.equals("次日") || qbgsj.equals("次日16：00之后")) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 16);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 0);
            return Constants.SDF.format(calendar.getTime());
        }
        if(qbgsj.equals("次二日")) {
            calendar.add(Calendar.DAY_OF_MONTH, 2);
            calendar.set(Calendar.HOUR_OF_DAY, 16);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 0);
            return Constants.SDF.format(calendar.getTime());
        }
        if(qbgsj.equals("10个工作日") || qbgsj.equals("十个工作日")) {
            calendar.set(Calendar.HOUR_OF_DAY, 16);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 0);
            return Constants.SDF.format(calendar.getTime());
        }
        if(qbgsj.equals("5个工作日") || qbgsj.equals("五个工作日")) {
            calendar.add(Calendar.WEEK_OF_MONTH, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 16);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 0);
            return Constants.SDF.format(calendar.getTime());
        }
        if(qbgsj.equals("1个工作日")) {
            calendar.add(Calendar.DAY_OF_WEEK, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 16);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.SECOND, 0);
            if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY) {
                calendar.add(Calendar.DAY_OF_WEEK, 2);
            }
            return Constants.SDF.format(calendar.getTime());
        }


        return "该项目取报告时间未维护，请询问相关工作人员";
    }

    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_WEEK, 1);
        System.out.println(new GetReportTimeUtil().getReportTime(c.getTime(), "1个工作日"));
    }

}
