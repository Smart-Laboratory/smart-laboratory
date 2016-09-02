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
	private Long ylxh; 				// 医疗序号
	
	private String profiletest3;	//相关检验项目列表 
	private String profiletest2;   //可做可不做的检验项目
	private String profiletest;   //检验项目
	private String ylmc;		//医疗名称
	private String ksdm;		//科室代码
	private String english;

	private int mzpb;
	private int zypb;
	private int sfhb;		//是否允许合并检验目的
	private String price;
	private String qbgdd;	//取报告地点
	private String qbgsj;	//取报告时间
	private String yblx;	//样本类型
	private String bbl;		//标本量
	private String sglx;	//试管类型
	private String cjbw;	//采集部位
	private int sgsl=0;		//试管数量
	
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
	
	@Column
	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	@Column
	public int getMzpb() {
		return mzpb;
	}

	public void setMzpb(int mzpb) {
		this.mzpb = mzpb;
	}

	@Column
	public int getZypb() {
		return zypb;
	}

	public void setZypb(int zypb) {
		this.zypb = zypb;
	}

	@Column
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Column
	public String getQbgdd() {
		return qbgdd;
	}

	public void setQbgdd(String qbgdd) {
		this.qbgdd = qbgdd;
	}

	@Column
	public String getQbgsj() {
		return qbgsj;
	}

	public void setQbgsj(String qbgsj) {
		this.qbgsj = qbgsj;
	}

	@Column
	public String getYblx() {
		return yblx;
	}

	public void setYblx(String yblx) {
		this.yblx = yblx;
	}

	@Column
	public String getBbl() {
		return bbl;
	}

	public void setBbl(String bbl) {
		this.bbl = bbl;
	}

	@Column
	public String getSglx() {
		return sglx;
	}

	public void setSglx(String sglx) {
		this.sglx = sglx;
	}

	@Column
	public String getCjbw() {
		return cjbw;
	}

	public void setCjbw(String cjbw) {
		this.cjbw = cjbw;
	}

	@Column
	public int getSgsl() {
		return sgsl;
	}

	public void setSgsl(int sgsl) {
		this.sgsl = sgsl;
	}

	@Column
	public int getSfhb() {
		return sfhb;
	}

	public void setSfhb(int sfhb) {
		this.sfhb = sfhb;
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
