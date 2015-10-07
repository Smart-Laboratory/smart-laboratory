package com.smart.model.lis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 医生的联系信息
 */
@Entity
@Table(name = "l_contact")
public class ContactInfor {
	
	private String WORKID;
	private String NAME;
	private String SECTION;
	private String PHONE;
	
	/**
	 * 医生工号
	 */
	@Id
	public String getWORKID() {
		return WORKID;
	}
	
	public void setWORKID(String wORKID) {
		WORKID = wORKID;
	}
	
	/**
	 * 医生姓名
	 */
	@Column
	public String getNAME() {
		return NAME;
	}
	
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	
	/**
	 * 医生所在的科室
	 */
	@Column
	public String getSECTION() {
		return SECTION;
	}
	
	public void setSECTION(String sECTION) {
		SECTION = sECTION;
	}
	
	/**
	 * 医生电话
	 */
	@Column
	public String getPHONE() {
		return PHONE;
	}
	
	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}
}
