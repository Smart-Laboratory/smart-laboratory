package com.smart.model.lis;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 样本物流时间表
 * @author LZH
 *
 */
@Entity
@Table(name = "L_SAMPLELOGISTIC")
public class SampleLogistic {

	private Long id;
	private Long doctadviseno;
	
	private String location;
	private String operator;
	private Date operatetime;
	private String operatetype;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_SAMPLELOGISTIC")
	@SequenceGenerator(name = "SEQ_SAMPLELOGISTIC", sequenceName = "sampleLogistic_sequence", allocationSize=1)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getDoctadviseno() {
		return doctadviseno;
	}
	public void setDoctadviseno(Long doctadviseno) {
		this.doctadviseno = doctadviseno;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getOperatetime() {
		return operatetime;
	}
	public void setOperatetime(Date operatetime) {
		this.operatetime = operatetime;
	}
	public String getOperatetype() {
		return operatetype;
	}
	public void setOperatetype(String operatetype) {
		this.operatetype = operatetype;
	}
	
	


	
}
