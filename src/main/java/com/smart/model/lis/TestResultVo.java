package com.smart.model.lis;

/**
 * Created by zcw on 2016/9/12.
 */
public class TestResultVo {
    private String testName;            //项目名称
    private String testResult;          //当前结果
    private String hisTestResult1;      //历史结果1
    private String hisTestResult2;      //历史结果2
    private String resultFlag;          //结果标记
    private String hisResultFlag1;      //历史结果标记1
    private String hisResultFlag2;      //历史结果标记2
    private String reference;           //参考范围
    private String unit;                //单位
    private String description;         //备注

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getHisTestResult1() {
        return hisTestResult1;
    }

    public void setHisTestResult1(String hisTestResult1) {
        this.hisTestResult1 = hisTestResult1;
    }

    public String getHisTestResult2() {
        return hisTestResult2;
    }

    public void setHisTestResult2(String hisTestResult2) {
        this.hisTestResult2 = hisTestResult2;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(String resultFlag) {
        this.resultFlag = resultFlag;
    }

    public String getHisResultFlag1() {
        return hisResultFlag1;
    }

    public void setHisResultFlag1(String hisResultFlag1) {
        this.hisResultFlag1 = hisResultFlag1;
    }

    public String getHisResultFlag2() {
        return hisResultFlag2;
    }

    public void setHisResultFlag2(String hisResultFlag2) {
        this.hisResultFlag2 = hisResultFlag2;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
