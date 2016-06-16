package com.smart.model.lis;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smart.model.BaseObject;

/**
 * 检验项目结果
 */
@Entity
@Table(name = "l_testresult_log")
public class TestResultLog extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6914086187533981085L;
	
	private Long id;
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
    
    
    private String logger;
	private Date logtime;
	private String logip;
	private String logoperate;

    @Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_TESTRESULT_LOG")
	@SequenceGenerator(name = "SEQ_TESTRESULT_LOG", sequenceName = "testresult_log_sequence", allocationSize=1)
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column
	public String getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}

	@Column
	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	@Column
	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	@Column
	public String getTestResult() {
		return testResult;
	}

	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}

	@Column
	public String getResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}

	@Column
	public int getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(int testStatus) {
		this.testStatus = testStatus;
	}

	@Column
	public String getCorrectFlag() {
		return correctFlag;
	}

	public void setCorrectFlag(String correctFlag) {
		this.correctFlag = correctFlag;
	}

	@Column
	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	@Column
	public String getRefLo() {
		return refLo;
	}

	public void setRefLo(String refLo) {
		this.refLo = refLo;
	}

	@Column
	public String getRefHi() {
		return refHi;
	}

	public void setRefHi(String refHi) {
		this.refHi = refHi;
	}

	@Column
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@Column
	public Date getMeasureTime() {
		return measureTime;
	}

	public void setMeasureTime(Date measureTime) {
		this.measureTime = measureTime;
	}

	@Column
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column
	public int getIsprint() {
		return isprint;
	}

	public void setIsprint(int isprint) {
		this.isprint = isprint;
	}

	@Column
	public int getEditMark() {
		return editMark;
	}

	public void setEditMark(int editMark) {
		this.editMark = editMark;
	}

	@Column
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	@Column
	public String getLogger() {
		return logger;
	}

	public void setLogger(String logger) {
		this.logger = logger;
	}

	@Column
	public Date getLogtime() {
		return logtime;
	}

	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}

	@Column
	public String getLogip() {
		return logip;
	}

	public void setLogip(String logip) {
		this.logip = logip;
	}
	
	@Column
	public String getLogoperate() {
		return logoperate;
	}

	public void setLogoperate(String logoperate) {
		this.logoperate = logoperate;
	}
	

	@Transient
	public TestResult getTestResultEntity() {
		TestResult tr = new TestResult();
		tr.setSampleNo(this.sampleNo);
		tr.setTestId(this.testId);
		tr.setCorrectFlag(this.correctFlag);
		tr.setDeviceId(this.deviceId);
		tr.setEditMark(this.editMark);
		tr.setIsprint(this.isprint);
		tr.setMeasureTime(this.measureTime);
		tr.setMethod(this.method);
		tr.setOperator(this.operator);
		tr.setRefHi(this.refHi);
		tr.setRefLo(this.refLo);
		tr.setResultFlag(this.resultFlag);
		tr.setSampleType(this.sampleType);
		tr.setTestName(this.testName);
		tr.setTestResult(this.testResult);
		tr.setTestStatus(this.testStatus);
		tr.setUnit(this.unit);
		return tr;
	}
	
	public void setTestResultEntity(TestResult tr) {
		this.sampleNo = tr.getSampleNo();
		this.testId = tr.getTestId();
		this.correctFlag = tr.getCorrectFlag();
		this.deviceId = tr.getDeviceId();
		this.editMark = tr.getEditMark();
		this.isprint = tr.getIsprint();
		this.measureTime = tr.getMeasureTime();
		this.method = tr.getMethod();
		this.operator = tr.getOperator();
		this.refHi = tr.getRefHi();
		this.refLo = tr.getRefLo();
		this.resultFlag = tr.getResultFlag();
		this.sampleType = tr.getSampleType();
		this.testName = tr.getTestName();
		this.testResult = tr.getTestResult();
		this.testStatus = tr.getTestStatus();
		this.unit = tr.getUnit();
	}

	public String toString() {
		return null;
	}

	public boolean equals(Object o) {
		return false;
	}

	public int hashCode() {
		return 0;
	}
}
