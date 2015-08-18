package com.smart.model.lis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.smart.model.BaseObject;

/**
 * 检验套餐（新）
 */
@Entity
@Table(name = "L_YLXHDESCRIBE")
public class Ylxh extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1318480292416322789L;

	// Primary Key
	private Long ylxh; // 医疗序号
	
	private String profiletest3;	//相关检验项目列表 
	private String profiletest2;   //可做可不做的检验项目
	private String profiletest;   //检验项目
	private String ylmc;		//医疗名称
	private String ksdm;		//科室代码
	
	/**
	 * 医疗序号
	 */
	@Id
	@Column(name = "YLXH")
	public Long getYlxh() {
		return ylxh;
	}

	public void setYlxh(Long ylxh) {
		this.ylxh = ylxh;
	}

	/**
	 * 套餐所包含的项目，必须做
	 */
	@Column(name = "PROFILETEST")
	public String getProfiletest() {
		return profiletest;
	}

	public void setProfiletest(String profiletest) {
		this.profiletest = profiletest;
	}
	
	/**
	 * 套餐所包含的项目，可做可不做
	 */
	@Column(name = "PROFILETEST2")
	public String getProfiletest2() {
		return profiletest2;
	}

	public void setProfiletest2(String profiletest2) {
		this.profiletest2 = profiletest2;
	}
	
	/**
	 * 相关检验项目列表
	 */
	@Column(name = "PROFILETEST3")
	public String getProfiletest3() {
		return profiletest3;
	}

	public void setProfiletest3(String profiletest3) {
		this.profiletest3 = profiletest3;
	}

	/**
	 * 医疗名称
	 */
	@Column(name = "YLMC")
	public String getYlmc() {
		return ylmc;
	}

	public void setYlmc(String ylmc) {
		this.ylmc = ylmc;
	}

	/**
	 * 科室代码
	 */
	@Column(name = "KSDM", length=10)
	public String getKsdm() {
		return ksdm;
	}

	public void setKsdm(String ksdm) {
		this.ksdm = ksdm;
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