package com.smart.webapp.util;

/**
 * 计算年龄
 * Created by yuzh on 2016/9/20.
 */
public class AgeUtil {

    public Double getAge(String age, String ageUnit) {
        if(ageUnit.equals("岁")) {
            return Double.parseDouble(age);
        } else if(ageUnit.equals("月")) {
            return Double.parseDouble(age)/12;
        } else if(ageUnit.equals("周")) {
            return Double.parseDouble(age)*7/360;
        } else if(ageUnit.equals("天")) {
            return Double.parseDouble(age)/360;
        }
        return 0d;
    }
}
