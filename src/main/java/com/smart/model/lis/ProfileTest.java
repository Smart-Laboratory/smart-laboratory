package com.smart.model.lis;

import javax.persistence.*;
import java.util.Date;

/**
 * Title: ProfileTest
 * Description: 检验项目组合套餐设置
 *
 * @Author:zhou
 * @Date:2016/6/7 14:47
 * @Version:
 */
@Entity
@Table(name = "l_profiletest")
public class ProfileTest {
    private Long Id ;
    private String profileCode;             //编号
    private String profileName;             //缩写
    private String profileDescribe="";         //试验组合名称
    private String deviceId="";                //设备ID
    private String profileTest="";             //A项目ID
    private int emergency =0 ;
    private int frequencyTime =0;              //时间频率
    private String dataWindowName="";
    private Date inuredate;                  //录入日期
    private String operator="";                //操作人
    private String section="";                 //科室
    private int useNow =1;
    private String sampleType="";              //标本类型
    private String bjmc;
    private String wb="";
    private String py="";
    private int zdm =0;
    private String fee="";
    private int ylxh =0;
    private String zmwz="";
    private String jyz="";
    private String jyzTime="";

    public ProfileTest(){
        this.profileCode = "";             //编号
        this.profileName = "";
        this.profileDescribe = "";
        this.deviceId = "";
        this.emergency = 0;
        this.frequencyTime = 0;
        this.dataWindowName = "0";
        this.inuredate =  new Date();
        this.operator = "";
        this.section = "";
        this.useNow = 1 ;
        this.sampleType = "";
        this.bjmc = "";
        this.wb = "";
        this.py = "";
        this.zdm = 0;
        this.fee = "";
        this.ylxh = 0;
        this.jyz = "";
        this.zmwz = "";
        this.jyzTime ="";
    }
    //ROFILETEST_SEQUENCE
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_PROFILETEST")
    @SequenceGenerator(name = "SEQ_PROFILETEST", sequenceName = "profiletest_sequence", allocationSize=1)
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getProfileCode() {
        return profileCode;
    }

    public void setProfileCode(String profileCode) {
        this.profileCode = profileCode;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileDescribe() {
        return profileDescribe;
    }

    public void setProfileDescribe(String profileDescribe) {
        this.profileDescribe = profileDescribe;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getProfileTest() {
        return profileTest;
    }

    public void setProfileTest(String profileTest) {
        this.profileTest = profileTest;
    }

    public int getEmergency() {
        return emergency;
    }

    public void setEmergency(int emergency) {
        this.emergency = emergency;
    }

    public int getFrequencyTime() {
        return frequencyTime;
    }

    public void setFrequencyTime(int frequencyTime) {
        this.frequencyTime = frequencyTime;
    }

    public String getDataWindowName() {
        return dataWindowName;
    }

    public void setDataWindowName(String dataWindowName) {
        this.dataWindowName = dataWindowName;
    }

    public Date getInuredate() {
        return inuredate;
    }

    public void setInuredate(Date inuredate) {
        this.inuredate = inuredate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getUseNow() {
        return useNow;
    }

    public void setUseNow(int useNow) {
        this.useNow = useNow;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getBjmc() {
        return bjmc;
    }

    public void setBjmc(String bjmc) {
        this.bjmc = bjmc;
    }

    public String getWb() {
        return wb;
    }

    public void setWb(String wb) {
        this.wb = wb;
    }

    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }

    public int getZdm() {
        return zdm;
    }

    public void setZdm(int zdm) {
        this.zdm = zdm;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public int getYlxh() {
        return ylxh;
    }

    public void setYlxh(int ylxh) {
        this.ylxh = ylxh;
    }

    public String getZmwz() {
        return zmwz;
    }

    public void setZmwz(String zmwz) {
        this.zmwz = zmwz;
    }

    public String getJyz() {
        return jyz;
    }

    public void setJyz(String jyz) {
        this.jyz = jyz;
    }

    public String getJyzTime() {
        return jyzTime;
    }

    public void setJyzTime(String jyzTime) {
        this.jyzTime = jyzTime;
    }

}
