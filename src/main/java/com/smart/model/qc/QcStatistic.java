package com.smart.model.qc;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 质控结果统计信息表
 */
@Entity
@Table(name = "l_qc_statistic")
public class QcStatistic {
	
	private Long id;
	private String qcBatch;
	private int qcLevel;
	private String testid;
	private String average;
	private String std;
	private String cv;
	private String counttime;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_QC_STATISTIC")
	@SequenceGenerator(name="SEQ_QC_STATISTIC", sequenceName = "qc_statistic_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getQcBatch() {
		return qcBatch;
	}
	
	public void setQcBatch(String qcBatch) {
		this.qcBatch = qcBatch;
	}
	
	public int getQcLevel() {
		return qcLevel;
	}
	
	public void setQcLevel(int qcLevel) {
		this.qcLevel = qcLevel;
	}
	
	public String getTestid() {
		return testid;
	}
	
	public void setTestid(String testid) {
		this.testid = testid;
	}
	
	public String getAverage() {
		return average;
	}
	
	public void setAverage(String average) {
		this.average = average;
	}
	
	public String getStd() {
		return std;
	}
	
	public void setStd(String std) {
		this.std = std;
	}
	
	public String getCv() {
		return cv;
	}
	
	public void setCv(String cv) {
		this.cv = cv;
	}
	
	public String getCounttime() {
		return counttime;
	}
	
	public void setCounttime(String counttime) {
		this.counttime = counttime;
	}
}
