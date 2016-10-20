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
        } else {
            Calendar calendar =  Calendar.getInstance();
            calendar.setTime(executeTime);
            if(qbgsj.equals("30分钟")) {
                calendar.add(Calendar.MINUTE, 30);
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("1小时")) {
                calendar.add(Calendar.HOUR_OF_DAY, 1);
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("2小时")) {
                calendar.add(Calendar.HOUR_OF_DAY, 2);
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("3小时")) {
                calendar.add(Calendar.HOUR_OF_DAY, 3);
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("4小时")) {
                calendar.add(Calendar.HOUR_OF_DAY, 4);
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("次日") || qbgsj.equals("次日16：00之后") || qbgsj.equals("24小时")) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("次二日") || qbgsj.equals("2天")) {
                calendar.add(Calendar.DAY_OF_MONTH, 2);
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("4天")) {
                calendar.add(Calendar.DAY_OF_WEEK, 4);
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("十五个工作日") || qbgsj.equals("15个工作日")) {
                calendar.add(Calendar.WEEK_OF_MONTH, 3);
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("14个工作日后")) {
                calendar.add(Calendar.WEEK_OF_MONTH, 2);
                calendar.add(Calendar.DAY_OF_WEEK, 4);
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
                if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY ||
                        calendar.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY || calendar.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY) {
                    calendar.add(Calendar.DAY_OF_WEEK, 2);
                }
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("10个工作日") || qbgsj.equals("十个工作日") || qbgsj.equals("两周")) {
                calendar.add(Calendar.WEEK_OF_MONTH, 2);
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("5个工作日") || qbgsj.equals("五个工作日") || qbgsj.equals("7天")) {
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
            if(qbgsj.equals("2个工作日") || qbgsj.equals("两个工作日")) {
                calendar.add(Calendar.DAY_OF_WEEK, 2);
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
                if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
                    calendar.add(Calendar.DAY_OF_WEEK, 2);
                }
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("3个工作日") || qbgsj.equals("3个工作日后") || qbgsj.equals("当天抽血，周一至周四15:00点前，3个工作日后报告")) {
                calendar.add(Calendar.DAY_OF_WEEK, 3);
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
                if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY ||
                        calendar.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY) {
                    calendar.add(Calendar.DAY_OF_WEEK, 2);
                }
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("4个工作日")) {
                calendar.add(Calendar.DAY_OF_WEEK, 4);
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
                if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY ||
                        calendar.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY || calendar.get(Calendar.DAY_OF_WEEK)==Calendar.TUESDAY) {
                    calendar.add(Calendar.DAY_OF_WEEK, 2);
                }
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("六个工作日")) {
                calendar.add(Calendar.WEEK_OF_MONTH, 1);
                calendar.add(Calendar.DAY_OF_WEEK, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
                if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY) {
                    calendar.add(Calendar.DAY_OF_WEEK, 2);
                }
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("7个工作日") || qbgsj.equals("7个工作日后") || qbgsj.equals("七个工作日") || qbgsj.equals("当天标本，周一至周四8点前，七个工作日")) {
                calendar.add(Calendar.WEEK_OF_MONTH, 1);
                calendar.add(Calendar.DAY_OF_WEEK, 2);
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
                if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
                    calendar.add(Calendar.DAY_OF_WEEK, 2);
                }
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("八个工作日")) {
                calendar.add(Calendar.WEEK_OF_MONTH, 1);
                calendar.add(Calendar.DAY_OF_WEEK, 3);
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
                if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY ||
                        calendar.get(Calendar.DAY_OF_WEEK)==Calendar.MONDAY) {
                    calendar.add(Calendar.DAY_OF_WEEK, 2);
                }
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("11点半以前的标本当日下午16点半报告，11点半以后的标本次日下午16点半报告。(周六不做）")) {
                //获取当天11:30的时间节点
                Calendar margin = Calendar.getInstance();
                margin.set(Calendar.HOUR_OF_DAY, 11);
                margin.set(Calendar.MINUTE, 30);
                margin.set(Calendar.SECOND, 0);
                Calendar reportTime = compareDate(calendar, margin);
                if(reportTime.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY) {
                    reportTime.add(Calendar.DAY_OF_WEEK, 1);
                }
                return Constants.SDF.format(reportTime.getTime());
            }
            if(qbgsj.equals("12点以前的标本当日下午16半点报告，12点以后的标本次日下午16半点报告。") ||
                    qbgsj.equals("12:00以前的标本当日16:30报告，12:00以后的标本次日16:30报告。") ||
                    qbgsj.equals("12点前的标本，下午4:30报告，12点后的标本，次日4:30报告") ||
                    qbgsj.equals("12点以前的标本当日下午16点半报告，12点以后的标本次日下午16点半报告。")) {
                //获取当天12:00的时间节点
                Calendar margin = Calendar.getInstance();
                margin.set(Calendar.HOUR_OF_DAY, 12);
                margin.set(Calendar.MINUTE, 0);
                margin.set(Calendar.SECOND, 0);
                return Constants.SDF.format(compareDate(calendar, margin).getTime());
            }
            if(qbgsj.equals("12点以前的标本当日下午16半点报告，12点以后的标本次日下午16半点报告。(周六不做）") ||
                    qbgsj.equals("12点以前的标本当日下午16半点报告，12点以后的标本次日下午16半点报告。（周六不做）")) {
                //获取当天12:00的时间节点
                Calendar margin = Calendar.getInstance();
                margin.set(Calendar.HOUR_OF_DAY, 12);
                margin.set(Calendar.MINUTE, 0);
                margin.set(Calendar.SECOND, 0);
                Calendar reportTime = compareDate(calendar, margin);
                if(reportTime.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY) {
                    reportTime.add(Calendar.DAY_OF_WEEK, 1);
                }
                return Constants.SDF.format(reportTime.getTime());
            }
            if(qbgsj.equals("12点以前的标本当日下午16半点报告，12点以后的标本次日下午16半点报告。(周日不做)") ||
                    qbgsj.equals("12点以前的标本当日下午16半点报告，12点以后的标本次日下午16半点报告。(周日不做）")) {
                //获取当天12:00的时间节点
                Calendar margin = Calendar.getInstance();
                margin.set(Calendar.HOUR_OF_DAY, 12);
                margin.set(Calendar.MINUTE, 0);
                margin.set(Calendar.SECOND, 0);
                Calendar reportTime = compareDate(calendar, margin);
                if(reportTime.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY) {
                    reportTime.add(Calendar.DAY_OF_WEEK, 1);
                }
                return Constants.SDF.format(reportTime.getTime());
            }
            if(qbgsj.equals("标本截止至周六，每周日做，当日下午16点半报告。")) {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                calendar.add(Calendar.WEEK_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("周一收标本周五出报告") || qbgsj.equals("周一收标本周五出")
                    || qbgsj.equals("周一8:00之前收标本，周五下午4:30报告")
                    || qbgsj.equals("周一8:00之前收标本周五下午4:30出报告") || qbgsj.equals("标本截至周三，周五报告")) {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("周五8:00之前收标本下周五4:30出结果")) {
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                calendar.add(Calendar.WEEK_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
                return Constants.SDF.format(calendar.getTime());
            }
            if(qbgsj.equals("标本截止至周二，每周三做，当日下午16点半报告。")) {
                Calendar margin = calendar;
                margin.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                if(calendar.compareTo(margin) >= 0) {
                    calendar.add(Calendar.WEEK_OF_MONTH, 1);
                }
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                calendar.set(Calendar.HOUR_OF_DAY, 16);
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 0);
                return Constants.SDF.format(calendar.getTime());
            }

            /*if(qbgsj.equals("3-5天 ") || qbgsj.equals("45-60天 ") || qbgsj.equals("5-7日 ")
                    || qbgsj.equals("3-5天") || qbgsj.equals("3-5个工作日") || qbgsj.equals("3-5个工作日 ") || qbgsj.equals("7-9个工作日")) {
                return qbgsj;
            }*/
            return qbgsj;
        }
    }

    public Calendar compareDate(Calendar calendar, Calendar margin) {
        //比较时间，早的话，当天下午，晚的话，隔天下午
        Calendar reportTime = Calendar.getInstance();
        if(calendar.compareTo(margin) >= 0) {
            reportTime.add(Calendar.DAY_OF_WEEK, 1);
        }
        reportTime.set(Calendar.HOUR_OF_DAY, 16);
        reportTime.set(Calendar.MINUTE, 30);
        reportTime.set(Calendar.SECOND, 0);
        return reportTime;
    }

    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        System.out.println(new GetReportTimeUtil().getReportTime(c.getTime(), "30分钟"));
    }

}
