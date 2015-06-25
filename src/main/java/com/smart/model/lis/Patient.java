package com.smart.model.lis;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//
@Entity
@Table(name = "lab_patient")
public class Patient {

	private Long id;// 主键，自增
	
	private String blh;//病例号
	private String patientName;//病人姓名
	private Date birthday; // 
	private String address;//病人地址
	private String phone;//电话
	private String infantFlag;//婴儿标识
	private String sex;//性别
	private String idCard;//身份证号
	
	private Hospital hospital;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_PATIENT")
	@SequenceGenerator(name = "SEQ_PATIENT", sequenceName = "patient_seq", allocationSize=1)
	public Long getId(){
		return this.id;
	}
	public void setId(Long id){
		this.id = id;
	}
	
	/**
	 * 病例号
	 */
	@Column(name = "BLH", length = 20)
	public String getBlh() {
		return blh;
	}

	public void setBlh(String blh) {
		this.blh = blh;
	}
	
	@Column(name = "PATIENTNAME", length = 50)
	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	
	@Column(name = "BIRTHDAY")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "PHONE", length = 20)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Column(name = "INFANTFLAG", length = 10)
	public String getInfantFlag() {
		return infantFlag;
	}

	public void setInfantFlag(String infantFlag) {
		this.infantFlag = infantFlag;
	}
	
	@Column(name = "SEX", length = 10)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Column(name = "IDCARD", length = 20)
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	@ManyToOne(optional=false,cascade=CascadeType.MERGE)
	@JoinColumn(name="hospital_id",referencedColumnName="id")
	public Hospital getHospital(){
		return hospital;
	}
	public void setHospital(Hospital hospital){
		this.hospital = hospital; 
	}
}
