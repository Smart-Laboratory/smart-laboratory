package com.smart.model.lis;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.IndexColumn;

import com.smart.model.BaseObject;

/**
 * 检验项目结果
 */
@Entity
@IdClass(TestResultPK.class)
@Table(name = "l_testresult")
public class TestResult extends BaseObject{

	private static final long serialVersionUID = 1234221L;
	
	private String sampleNo;
    private String testId;  //检验项目id（每个id对应一个确定的检验项目）
    private String testName;
    private String testResult; //检验结果
    private String resultFlag; //标注 检验结果 异常情况
    private int testStatus;
    private String correctFlag;
    private String sampleType; //检验项目类型、来源
    private String refLo; // 参考范围低值
    private String refHi; // 参考范围高值
    private String deviceId; // 设备号
    private Date measureTime; // 检验时间
    private String operator; // 操作者
    private String unit;
    private int isprint;
    private int editMark;
    private String method;
    private String hint;
    
    /**
	 * 主键、检验样本号
	 */
    @Id
    @IndexColumn(name = "sample_no")
    @Column(name = "SAMPLENO", length = 20)
    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }
    
    @Column(name = "TESTNAME")
    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    /**
	 * 主键、检验项目id
	 */
    @Id
    @Column(name = "TESTID", length = 20)
    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    /**
	 * 检验项目结果
	 */
    @Column(name = "TESTRESULT", length = 20)
    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    /**
	 * 检验项目结果标记
	 */
    @Column(name = "RESULTFLAG", length = 10)
    public String getResultFlag() {
        return resultFlag;
    }

    public void setResultFlag(String resultFlag) {
        this.resultFlag = resultFlag;
    }

    /**
	 * 检验项目状态
	 */
    @Column(name = "TESTSTATUS")
    public int getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(int testStatus) {
        this.testStatus = testStatus;
    }

    /**
	 * 检验项目正确标识
	 */
    @Column(name = "CORRECTFLAG", length = 10)
    public String getCorrectFlag() {
        return correctFlag;
    }

    public void setCorrectFlag(String correctFlag) {
        this.correctFlag = correctFlag;
    }

    /**
	 * 检验项目样本类型
	 */
    @Column(name = "SAMPLETYPE", length = 10)
    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }
    
    /**
	 * 检验项目参考低值
	 */
    @Column(name = "REFLO",length=20)
    public String getRefLo() {  
        return refLo;
    }

    public void setRefLo(String refLo) {
        this.refLo = refLo;
    }

    /**
	 * 检验项目参考高值
	 */
    @Column(name = "REFHI",length=20)
    public String getRefHi() {
        return refHi;
    }

    public void setRefHi(String refHi) {
        this.refHi = refHi;
    }

    /**
	 * 检验项目仪器号
	 */
    @Column(name = "DEVICEID", length = 10)
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
	 * 检验项目测量时间
	 */
    @Column(name = "MEASURETIME")
    public Date getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(Date measureTime) {
        this.measureTime = measureTime;
    }

    /**
	 * 检验项目测量者
	 */
    @Column(name = "OPERATOR")
    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
	 * 检验项目单位
	 */
    @Column(name = "UNIT", length = 20)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    /**
	 * 检验项目打印标识
	 */
    @Column(name = "ISPRINT")
    public int getIsprint() {
		return isprint;
	}

	public void setIsprint(int isprint) {
		this.isprint = isprint;
	}
    
	/**
	 * 检验项目编辑标记
	 */
    @Column(name = "EDITMARK")
    public int getEditMark() {
		return editMark;
	}

	public void setEditMark(int editMark) {
		this.editMark = editMark;
	}
	
	@Column(name = "METHOD")
    public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Column(name = "HINT")
	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	/**
     * Returns the reference.
     * 
     * @return refLo +" -- " + refHi
     */
    @Transient
    public String getReference() {
        if (refLo.isEmpty() && refHi.isEmpty()) {
            return "-";
        } else if (refLo.isEmpty() && (!refHi.isEmpty())) {
            return refHi;
        } else if (refHi.isEmpty() && (!refLo.isEmpty())) {
            return refLo;
        } else {
            return refLo + "--" + refHi;
        }
    }

    /**
     * Returns the result view.
     * 
     * @return result + '' + resultFlag
     */
    @Transient
    public String getResultView() {
        if (resultFlag.isEmpty()) {
            return testResult;
        }
        if (resultFlag.charAt(0) == 'B') {
            return "↑ " + testResult;
        } else if (resultFlag.charAt(0) == 'C') {
            return "↓ " + testResult;
        } else {
            return testResult;
        }
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
