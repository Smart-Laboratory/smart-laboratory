package com.smart.model.dsf;

import com.smart.model.BaseObject;
import org.hibernate.search.annotations.DocumentId;

import javax.persistence.*;

/**
 * 检验套餐（新）
 */
@Entity
@Table(name = "DSF_L_YLXHDESCRIBE")
public class DSF_ylxh extends BaseObject {
	/**
	 * 
	 */
	// Primary Key
	private Long id;
	private String ylxh; // 医疗序号
	private String profiletest3;	//相关检验项目列表
	private String profiletest2;   //可做可不做的检验项目
	private String profiletest;   //检验项目
	private String ylmc;		//医疗名称
	private String ksdm;		//科室代码
	private String customerid;




	/**
	 * 医疗序号
	 */

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DSF_Ylxh")
	@SequenceGenerator(name = "SEQ_DSF_Ylxh", sequenceName = "dsf_ylxh_sequence", allocationSize = 1)
	@DocumentId
	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getYlxh() {
		return ylxh;
	}

	public void setYlxh(String ylxh) {
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


	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}


}
