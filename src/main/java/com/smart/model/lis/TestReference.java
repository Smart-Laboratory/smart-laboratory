package com.smart.model.lis;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Title: TestReference
 * Description:检验项目参考范围
 *
 * 其中序号：用于区分参考范围 0: 参考范围高(低)值0、 1:参考范围高(低)1 、2：参考范围高(低)1
 * @Author:zhou
 * @Date:2016/6/6 22:24
 * @Version:
 */

@Entity
@IdClass(TestReferencePK.class)
@Table(name = "lab_testreference")
public class TestReference implements Serializable {
    private static final long serialVersionUID = -2513150853714387288L;
    private String testId;          //项目ID
    private int sex;                //性别 : 0男、1女
    private int orderno;            //序号
    private String sampleType;      //标本类型
    private int age;                    //年龄
    private String ageUnit;          //年龄单位; 岁、月、周、天
    private String deviceId;        //设备ID
    private int direct;
    private String refHigh;         //参考范围高值
    private String refLower;        //参考范围低值

    @Id
    @Column(name = "testid")
    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    @Id
    @Column(name = "sex")
    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Id
    @Column(name = "orderno")
    public int getOrderno() {
        return orderno;
    }

    public void setOrderno(int orderno) {
        this.orderno = orderno;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public String getRefHigh() {
        return refHigh;
    }

    public void setRefHigh(String refHign) {
        this.refHigh = refHign;
    }

    public String getRefLower() {
        return refLower;
    }

    public void setRefLower(String refLower) {
        this.refLower = refLower;
    }


    public String getAgeUnit() {
        return ageUnit;
    }

    public void setAgeUnit(String ageUnit) {
        this.ageUnit = ageUnit;
    }

}
