package com.smart.model.lis;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="l_invalidsample")
public class InvalidSample {

	// Primary Key
	private long id;
	
	private Sample sample;

	private Date rejectTime = new Date();
	private int containerType; //容器类型
	private int labelType;//标签形式
	private int requestionType; //申请单形式
	private int rejectSampleReason; //拒收原因
	private int measureTaken; //采取措施
	private String notes;  //说明
	private String rejectPerson; //拒收人
	
	/**
	 * 主键、自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_INVALIDSAMPLE")
	@SequenceGenerator(name = "SEQ_INVALIDSAMPLE", sequenceName = "invalidsample_sequence", allocationSize=1)	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	@OneToOne(cascade = CascadeType.MERGE,optional=false,fetch=FetchType.LAZY)
	@JoinColumn(name = "sample_id",referencedColumnName="id",unique=true)
	public Sample getSample() {
		return sample;
	}
	public void setSample(Sample sample) {
		this.sample = sample;
	}
	
	public Date getRejectTime() {
		return rejectTime;
	}
	public void setRejectTime(Date rejectTime) {
		this.rejectTime = rejectTime;
	}
	public int getContainerType() {
		return containerType;
	}
	public void setContainerType(int containerType) {
		this.containerType = containerType;
	}
	public int getLabelType() {
		return labelType;
	}
	public void setLabelType(int labelType) {
		this.labelType = labelType;
	}
	public int getRequestionType() {
		return requestionType;
	}
	public void setRequestionType(int requestionType) {
		this.requestionType = requestionType;
	}
	public int getRejectSampleReason() {
		return rejectSampleReason;
	}
	public void setRejectSampleReason(int rejectSampleReason) {
		this.rejectSampleReason = rejectSampleReason;
	}
	public int getMeasureTaken() {
		return measureTaken;
	}
	public void setMeasureTaken(int measureTaken) {
		this.measureTaken = measureTaken;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getRejectPerson() {
		return rejectPerson;
	}
	public void setRejectPerson(String rejectPerson) {
		this.rejectPerson = rejectPerson;
	}
}
