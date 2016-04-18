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
	private String MOBILEPHONE;
	private String VIRTUALPHONE;
	private String SECTION;
	
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
	 * 医生长号
	 */
	@Column(name = "MOBILE_PHONE")
	public String getMOBILEPHONE() {
		return MOBILEPHONE;
	}

	public void setMOBILEPHONE(String mOBILEPHONE) {
		MOBILEPHONE = mOBILEPHONE;
	}

	/**
	 * 医生短号
	 */
	@Column(name = "VIRTUAL_PHONE")
	public String getVIRTUALPHONE() {
		return VIRTUALPHONE;
	}

	public void setVIRTUALPHONE(String vIRTUALPHONE) {
		VIRTUALPHONE = vIRTUALPHONE;
	}
	
}
