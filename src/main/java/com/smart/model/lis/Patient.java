package com.smart.model.lis;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smart.model.BaseObject;

//
@Entity
@Table(name = "lab_patient")
public class Patient extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4497319050923705128L;

	private Long id;// 主键，自增
	
	private String blh;//病例号
	private String patientId;
	private String patientName;//病人姓名
	private Date birthday; // 
	private String address;//病人地址
	private String phone;//电话
	private String infantFlag;//婴儿标识
	private String sex;//性别
	private String idCard;//身份证号
	private int age;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_PATIENT")
	@SequenceGenerator(name = "SEQ_PATIENT", sequenceName = "patient_sequence", allocationSize=1)
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
	
	/**
	 * 就诊卡号
	 */
	@Column(name = "PATIENTID")
	public String getPatientId() {
		return patientId;
	}
	
	public void setPatientId(String patientId) {
		this.patientId = patientId;
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
	
	@Transient
	public int getAge() {
		if (birthday != null) {
			Calendar now = Calendar.getInstance();
			Calendar previous = Calendar.getInstance();
			previous.setTime(birthday);
			setAge(now.get(Calendar.YEAR) - previous.get(Calendar.YEAR)+1);
		}
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
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
