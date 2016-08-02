package com.smart.model.qc;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 质控结果
 */
@Entity
@Table(name = "l_qc_data")
public class QcData {
	
	private Long id;
	private String qcBatch;
	private int qcLevel;
	private String testid;
	private String testresult;
	private Date measuretime;
	private String deviceid;
	private String fee;
	private String sampleType;
	private String labDepart;
	private String analyser;
	private String inquality;
	
	private String outrule;
	private String reason;
	private String deal;
	private String prevent;
	private String dealperson;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_QC_DATA")
	@SequenceGenerator(name="SEQ_QC_DATA", sequenceName = "qc_data_sequence", allocationSize = 1)
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
	public int getQcLevel() {
		return qcLevel;
	}
	
	public void setQcLevel(int qcLevel) {
		this.qcLevel = qcLevel;
	}
	
	@Column
	public String getTestid() {
		return testid;
	}
	
	public void setTestid(String testid) {
		this.testid = testid;
	}
	
	@Column
	public String getTestresult() {
		return testresult;
	}
	
	public void setTestresult(String testresult) {
		this.testresult = testresult;
	}
	
	@Column
	public Date getMeasuretime() {
		return measuretime;
	}
	
	public void setMeasuretime(Date measuretime) {
		this.measuretime = measuretime;
	}
	
	@Column
	public String getDeviceid() {
		return deviceid;
	}
	
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	
	@Column
	public String getFee() {
		return fee;
	}
	
	public void setFee(String fee) {
		this.fee = fee;
	}
	
	@Column
	public String getSampleType() {
		return sampleType;
	}
	
	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}
	
	@Column
	public String getLabDepart() {
		return labDepart;
	}
	
	public void setLabDepart(String labDepart) {
		this.labDepart = labDepart;
	}
	
	@Column
	public String getAnalyser() {
		return analyser;
	}
	
	public void setAnalyser(String analyser) {
		this.analyser = analyser;
	}
	
	@Column
	public String getInquality() {
		return inquality;
	}

	public void setInquality(String inquality) {
		this.inquality = inquality;
	}

	@Column
	public String getOutrule() {
		return outrule;
	}

	public void setOutrule(String outrule) {
		this.outrule = outrule;
	}

	@Column
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column
	public String getDeal() {
		return deal;
	}

	public void setDeal(String deal) {
		this.deal = deal;
	}

	@Column
	public String getPrevent() {
		return prevent;
	}

	public void setPrevent(String prevent) {
		this.prevent = prevent;
	}

	@Column
	public String getDealperson() {
		return dealperson;
	}

	public void setDealperson(String dealperson) {
		this.dealperson = dealperson;
	}
}
