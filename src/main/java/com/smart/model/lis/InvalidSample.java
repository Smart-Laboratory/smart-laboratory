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


import com.smart.Constants;

/**
 * 不合格标本信息表
 */
@Entity
@Table(name="l_invalidsample")
public class InvalidSample {

	// Primary Key
	private Long id;
	
	private Date rejectTime;
	private int containerType; //容器类型
	private int labelType;//标签形式
	private int requestionType; //申请单形式
	private int rejectSampleReason; //拒收原因
	private int measureTaken; //采取措施
	private String notes;  //说明
	private String rejectPerson; //拒收人
	private String patientid; //就诊卡号
	
	//外来标本信息
	private Long sampleId;
	private String patientName;
	private String sex;
	private String ageunit;
	private int age;
	private String sampleType;
	
	
	private String rejectSampleReasonStr;
	
	/**
	 * 主键、自增
	 */
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_INVALIDSAMPLE")
	@SequenceGenerator(name = "SEQ_INVALIDSAMPLE", sequenceName = "invalidsample_sequence", allocationSize=1)	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	@Column(length=20)
	public Long getSampleId() {
		return sampleId;
	}
	
	public void setSampleId(Long sampleId) {
		this.sampleId = sampleId;
	}
	
	@Column(length=20)
	public String getPatientName() {
		return patientName;
	}
	
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
	@Column(length=10)
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Column(length=10)
	public String getAgeunit() {
		return ageunit;
	}
	public void setAgeunit(String ageunit) {
		this.ageunit = ageunit;
	}
	@Column
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Column(length=10)
	public String getSampleType() {
		return sampleType;
	}
	
	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}
	
	@Column
	public Date getRejectTime() {
		return rejectTime;
	}
	public void setRejectTime(Date rejectTime) {
		this.rejectTime = rejectTime;
	}
	
	@Column(length=10)
	public int getContainerType() {
		return containerType;
	}
	public void setContainerType(int containerType) {
		this.containerType = containerType;
	}
	
	@Column(length=10)
	public int getLabelType() {
		return labelType;
	}
	public void setLabelType(int labelType) {
		this.labelType = labelType;
	}
	
	@Column(length=10)
	public int getRequestionType() {
		return requestionType;
	}
	public void setRequestionType(int requestionType) {
		this.requestionType = requestionType;
	}
	
	@Column
	public int getRejectSampleReason() {
		return rejectSampleReason;
	}
	public void setRejectSampleReason(int rejectSampleReason) {
		this.rejectSampleReason = rejectSampleReason;
	}
	
	@Transient
	public String getRejectSampleReasonStr() {
		rejectSampleReasonStr = Constants.INVALIDSAMPLE_REASON[rejectSampleReason];
		return rejectSampleReasonStr;
	}
	public void setRejectSampleReasonStr(String reject){
		this.rejectSampleReasonStr = reject;
	}
	
	@Column(length=50)
	public int getMeasureTaken() {
		return measureTaken;
	}
	public void setMeasureTaken(int measureTaken) {
		this.measureTaken = measureTaken;
	}
	
	@Column
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	@Column(length=50)
	public String getRejectPerson() {
		return rejectPerson;
	}
	public void setRejectPerson(String rejectPerson) {
		this.rejectPerson = rejectPerson;
	}
	
	@Column
	public String getPatientid() {
		return patientid;
	}
	public void setPatientid(String patientid) {
		this.patientid = patientid;
	}
	
	@Transient
	public String getSexValue() {
		if (sex.equals("1")) {
			return "男";
		} else if (sex.equals("2")) {
			return "女";
		}
		return "未知";
	}
}
