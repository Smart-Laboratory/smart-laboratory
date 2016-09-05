package com.smart.model.execute;

import java.io.Serializable;

/**
 * 采集记录显示数据
 * Created by zcw on 2016/9/2.
 */
public class LabOrderVo implements Serializable{
    private static final long serialVersionUID = 8702109238133813770L;
    private Long laborder;              //ID
    private String requestId;           //申请单号
    private String laborderOrg;         //申请明细ID
    private String barcode;             //条码号
    private String ward;                //病区ID
    private String wardName;            //病区名称
    private String bedNo;               //床号
    private String hossection;          //申请部门
    private String patientId;           //病人就诊ID
    private String patientCode;         //病历号
    private String patientName;         //病人姓名
    private String sex;                 //性别
    private String age;                 //年龄
    private String ageUnit;             //年龄单位
    private String examitem;            //检验目的
    private String sampleType;          //样本类型
    private String requester;           //申请人
    private String requestTime;         //申请时间
    private String diagnose;            //临床诊断
    private String patientType;         //病人类型
    private String executeTime;         //采集时间
    private String printTime;           //打印时间
    private String makePrintTime;       //补打时间
    private String requestMode;         //是否急诊
    private String sampleQuantity;      //采集量
    private String testTube;            //试管类型
    private String ylxh;                //检验目的ID

    public String getYlxh() {
        return ylxh;
    }

    public void setYlxh(String ylxh) {
        this.ylxh = ylxh;
    }

    public Long getLaborder() {
        return laborder;
    }

    public void setLaborder(Long laborder) {
        this.laborder = laborder;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getHossection() {
        return hossection;
    }

    public void setHossection(String hossection) {
        this.hossection = hossection;
    }

    public String getPatientCode() {
        return patientCode;
    }

    public void setPatientCode(String patientCode) {
        this.patientCode = patientCode;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getExamitem() {
        return examitem;
    }

    public void setExamitem(String examitem) {
        this.examitem = examitem;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public String getPatientType() {
        return patientType;
    }

    public void setPatientType(String patientType) {
        this.patientType = patientType;
    }

    public String getLaborderOrg() {
        return laborderOrg;
    }

    public void setLaborderOrg(String laborderOrg) {
        this.laborderOrg = laborderOrg;
    }

    public String getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public String getPrintTime() {
        return printTime;
    }

    public void setPrintTime(String printTime) {
        this.printTime = printTime;
    }

    public String getMakePrintTime() {
        return makePrintTime;
    }

    public void setMakePrintTime(String makePrintTime) {
        this.makePrintTime = makePrintTime;
    }

    public String getAgeUnit() {
        return ageUnit;
    }

    public void setAgeUnit(String ageUnit) {
        this.ageUnit = ageUnit;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getRequestMode() {
        return requestMode;
    }

    public void setRequestMode(String requestMode) {
        this.requestMode = requestMode;
    }

    public String getSampleQuantity() {
        return sampleQuantity;
    }

    public void setSampleQuantity(String sampleQuantity) {
        this.sampleQuantity = sampleQuantity;
    }

    public String getTestTube() {
        return testTube;
    }

    public void setTestTube(String testTube) {
        this.testTube = testTube;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }
}
