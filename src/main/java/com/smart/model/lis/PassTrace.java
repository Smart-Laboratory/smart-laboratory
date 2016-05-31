package com.smart.model.lis;

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

@Entity
@Table(name="l_passtrace")
public class PassTrace extends BaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6339119347984963002L;
	
	private Long id;
	private String checker;
	private Date checktime;
	private String diagnostic;
	private String checkerOpinion;
	private String type;
	private String sampleNo;
	private String sex;
	private int age;
	private String information;
	
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_PASS_TRACE")
	@SequenceGenerator(name = "SEQ_PASS_TRACE", sequenceName = "pass_trace_sequence", allocationSize=1)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getChecker() {
		return checker;
	}
	public void setChecker(String checker) {
		this.checker = checker;
	}
	
	@Column
	public Date getChecktime() {
		return checktime;
	}
	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}
	
	@Column
	public String getDiagnostic() {
		return diagnostic;
	}
	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}
	
	@Column(name="checkerOpinion")
	public String getCheckerOpinion() {
		return checkerOpinion;
	}
	public void setCheckerOpinion(String checkerOpinion) {
		this.checkerOpinion = checkerOpinion;
	}
	
	@Column
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column
	public String getSampleNo() {
		return sampleNo;
	}
	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}
	
	@Column
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Column
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Column
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
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
