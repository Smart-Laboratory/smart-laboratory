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
    private int orderNo;            //序号
    private String sampleType;      //标本类型
    private int ageLow;             //参考年龄低限
    private int ageHigh;            //参考年龄高限
    private String ageLowUnit;      //参考年龄低限单位; 岁、月、周、天
    private String ageHighUnit;      //参考年龄高限单位; 岁、月、周、天
    private String deviceId;        //设备ID
    private int direct;
    private String reference;         //参考值

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
    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
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

	public int getAgeLow() {
		return ageLow;
	}

	public void setAgeLow(int ageLow) {
		this.ageLow = ageLow;
	}

	public int getAgeHigh() {
		return ageHigh;
	}

	public void setAgeHigh(int ageHigh) {
		this.ageHigh = ageHigh;
	}

	public String getAgeLowUnit() {
		return ageLowUnit;
	}

	public void setAgeLowUnit(String ageLowUnit) {
		this.ageLowUnit = ageLowUnit;
	}

	public String getAgeHighUnit() {
		return ageHighUnit;
	}

	public void setAgeHighUnit(String ageHighUnit) {
		this.ageHighUnit = ageHighUnit;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
}
