package com.smart.model.doctor;

/**
 * Title: LeftVo
 * Description:医生工作站左侧列表对象
 *
 * @Author:zhou
 * @Date:2016/6/22 17:47
 * @Version:
 */
public class LeftVo {
    private String dateTime;        //日期
    private String patientBlh;      //病历号
    private String reportNote;      //报告单份数
    private String sampleNos;       //样本号集
    private int microNum;           //微生物报告数

    public int getMicroNum() {
        return microNum;
    }

    public void setMicroNum(int microNum) {
        this.microNum = microNum;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getPatientBlh() {
        return patientBlh;
    }

    public void setPatientBlh(String patientBlh) {
        this.patientBlh = patientBlh;
    }

    public String getReportNote() {
        return reportNote;
    }

    public void setReportNote(String reportNote) {
        this.reportNote = reportNote;
    }

    public String getSampleNos() {
        return sampleNos;
    }

    public void setSampleNos(String sampleNos) {
        this.sampleNos = sampleNos;
    }


}
