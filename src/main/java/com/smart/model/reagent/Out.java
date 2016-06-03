package com.smart.model.reagent;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.smart.model.BaseObject;

/**
 * 卫生材料出库
 */
@Entity
@Table(name = "rg_out")
public class Out extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 611214706096090744L;
	
	private Long id;
	private String operator;
	private Date outdate;
	private int num;
	private String batch;
	private int testnum;				// 试剂能用多少次检验
	private Long rgId;
	private String lab;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HMO")
	@SequenceGenerator(name = "SEQ_HMO", sequenceName = "hmo_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length=20)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column
	public Date getOutdate() {
		return outdate;
	}

	public void setOutdate(Date outdate) {
		this.outdate = outdate;
	}
	
	@Column(length=20)
	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	@Column
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Column
	public int getTestnum() {
		return testnum;
	}

	public void setTestnum(int testnum) {
		this.testnum = testnum;
	}

	@Column(name = "rg_id")
	public Long getRgId() {
		return rgId;
	}

	public void setRgId(Long rgId) {
		this.rgId = rgId;
	}
	
	@Column
	public String getLab() {
		return lab;
	}

	public void setLab(String lab) {
		this.lab = lab;
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
