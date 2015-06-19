package com.smart.model.lis;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.smart.model.BaseObject;

/**
 * 样本流转信息表
 */
@Entity
@Table(name = "l_process")
public class Process extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1430918242317268010L;
	
	private long id;
	private Date time;
	private String operation;
	private String operator;
	private String note;
	
	private Sample sample;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_PROCESS")
	@SequenceGenerator(name = "SEQ_PROCESS", sequenceName = "pro_sequence", allocationSize=1)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Column
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Column(length=20)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "sample_id")
	public Sample getSample() {
		return sample;
	}

	public void setSample(Sample sample) {
		this.sample = sample;
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
