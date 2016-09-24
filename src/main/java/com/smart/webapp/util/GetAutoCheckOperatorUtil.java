package com.smart.webapp.util;

import com.smart.Constants;

import java.util.Date;

/**
 * Created by yuzh on 2016/9/24.
 * 获取各个专业组组长名字
 */
public class GetAutoCheckOperatorUtil {

    public String getName(String lab) {

        if(lab.equals("210800")) {
            return Constants.LEADER_ICU;
        }
        return "";
    }
}
