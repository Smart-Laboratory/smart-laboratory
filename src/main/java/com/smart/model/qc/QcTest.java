package com.smart.model.qc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 质控项目设置
 */
@Entity
@Table(name = "l_qc_test")
public class QcTest {
	
	private Long id;
	private String qcBatch;
	private String sampleType;
	private String englishab;
	private String targetValue;
	private String stdV;
	private String frequency;
	private int inuse;
	private String labDepart;
	private String deviceid;
	private String ptlow;
	private String pthigh;
	private String ruleSelected;
	
	private String testId;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_QC_TEST")
	@SequenceGenerator(name="SEQ_QC_TEST", sequenceName = "qc_test_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getQcBatch() {
		return qcBatch;
	}
	
	public void setQcBatch(String qcBatch) {
		this.qcBatch = qcBatch;
	}
	
	@Column
	public String getSampleType() {
		return sampleType;
	}
	
	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}
	
	@Column
	public String getEnglishab() {
		return englishab;
	}
	
	public void setEnglishab(String englishab) {
		this.englishab = englishab;
	}
	
	@Column
	public String getTargetValue() {
		return targetValue;
	}
	
	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}
	
	@Column
	public String getStdV() {
		return stdV;
	}
	
	public void setStdV(String stdV) {
		this.stdV = stdV;
	}
	
	@Column
	public String getFrequency() {
		return frequency;
	}
	
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
	@Column
	public int getInuse() {
		return inuse;
	}
	
	public void setInuse(int inuse) {
		this.inuse = inuse;
	}
	
	@Column
	public String getLabDepart() {
		return labDepart;
	}
	
	public void setLabDepart(String labDepart) {
		this.labDepart = labDepart;
	}
	
	@Column
	public String getDeviceid() {
		return deviceid;
	}
	
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	
	@Column
	public String getPtlow() {
		return ptlow;
	}
	
	public void setPtlow(String ptlow) {
		this.ptlow = ptlow;
	}
	
	@Column
	public String getPthigh() {
		return pthigh;
	}
	
	public void setPthigh(String pthigh) {
		this.pthigh = pthigh;
	}
	
	@Column
	public String getRuleSelected() {
		return ruleSelected;
	}
	
	public void setRuleSelected(String ruleSelected) {
		this.ruleSelected = ruleSelected;
	}

	@Column
	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}
	
	
}
