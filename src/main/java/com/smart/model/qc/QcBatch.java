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
 * 质控品基本信息
 */
@Entity
@Table(name = "l_qc_batch")
public class QcBatch {
	
	private Long id;				//主键
	private String qcBatch;			//质控品批号
	private String sampleType;		//样本类型
	private int qcLevel;			//质控水平
	private String qcCode;			//质控编码
	private String factory;			//厂家
	private String method;			//方法学
	private Date indate;			//入库时间 
	private Date outdate;			//出库时间
	private String outer;			//出库人
	private String deviceid;		//仪器ID
	private String labdepart;		//实验室部门
	private String lotNo;			//批号
	private String qcBatchName;		//质控品名称
	private Date expDate;			//质控品失效日期
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_QC_BATCH")
	@SequenceGenerator(name="SEQ_QC_BATCH", sequenceName = "qc_batch_sequence", allocationSize = 1)
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
	public int getQcLevel() {
		return qcLevel;
	}
	
	public void setQcLevel(int qcLevel) {
		this.qcLevel = qcLevel;
	}
	
	@Column
	public String getQcCode() {
		return qcCode;
	}
	
	public void setQcCode(String qcCode) {
		this.qcCode = qcCode;
	}
	
	@Column
	public String getFactory() {
		return factory;
	}
	
	public void setFactory(String factory) {
		this.factory = factory;
	}
	
	@Column
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	@Column
	public Date getIndate() {
		return indate;
	}
	
	public void setIndate(Date indate) {
		this.indate = indate;
	}
	
	@Column
	public Date getOutdate() {
		return outdate;
	}
	
	public void setOutdate(Date outdate) {
		this.outdate = outdate;
	}
	
	@Column
	public String getOuter() {
		return outer;
	}
	
	public void setOuter(String outer) {
		this.outer = outer;
	}
	
	@Column
	public String getDeviceid() {
		return deviceid;
	}
	
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	
	@Column
	public String getLabdepart() {
		return labdepart;
	}
	
	public void setLabdepart(String labdepart) {
		this.labdepart = labdepart;
	}
	
	@Column
	public String getLotNo() {
		return lotNo;
	}
	
	public void setLotNo(String lotNo) {
		this.lotNo = lotNo;
	}
	
	@Column
	public String getQcBatchName() {
		return qcBatchName;
	}
	
	public void setQcBatchName(String qcBatchName) {
		this.qcBatchName = qcBatchName;
	}
	
	@Column
	public Date getExpDate() {
		return expDate;
	}
	
	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}
}
