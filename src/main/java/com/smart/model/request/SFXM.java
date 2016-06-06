package com.smart.model.request;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*
 * 检验医疗收费项目
 */
@Entity
@Table(name = "gy_sfxm")
public class SFXM {

	private long id;		//id,自增长
	private String name;	//收费项目名称
	private String english;	//英文
	private String pinyin;	//拼音码
	private String wubi;	//五笔码
	private String unit;	//单位
	private String price;	//价格
	private String section;	//科室代码
	private int mzpb;
	private int zypb;
	private int tjpb;
	private String note;
	
	private String qbgdd;	//取报告地点
	private String qbgsj;	//取报告时间
	private String hyfl;	//化验分类
	private String yblx; 	//样本类型
	private String jyxmfl;	//计费项目分类
	private int tcpb;		//套餐判别
	private int qbbdd;
	private long hospitalid; //医院ID
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SFXM")
	@SequenceGenerator(name = "SEQ_SFXM", sequenceName = "sfxm_sequence", allocationSize = 1)
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column
	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	@Column
	public String getPinyin() {
		return pinyin;
	}
	
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
	@Column
	public String getWubi() {
		return wubi;
	}
	
	public void setWubi(String wubi) {
		this.wubi = wubi;
	}
	
	@Column
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Column
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	@Column
	public String getSection() {
		return section;
	}
	
	public void setSection(String section) {
		this.section = section;
	}
	
	@Column
	public int getMzpb() {
		return mzpb;
	}
	
	public void setMzpb(int mzpb) {
		this.mzpb = mzpb;
	}
	
	@Column
	public String getYblx() {
		return yblx;
	}

	public void setYblx(String yblx) {
		this.yblx = yblx;
	}
	
	@Column
	public int getZypb() {
		return zypb;
	}
	
	public void setZypb(int zypb) {
		this.zypb = zypb;
	}
	
	@Column
	public int getTjpb() {
		return tjpb;
	}
	
	public void setTjpb(int tjpb) {
		this.tjpb = tjpb;
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
	public String getHyfl() {
		return hyfl;
	}

	public void setHyfl(String hyfl) {
		this.hyfl = hyfl;
	}

	@Column
	public String getJyxmfl() {
		return jyxmfl;
	}

	public void setJyxmfl(String jyxmfl) {
		this.jyxmfl = jyxmfl;
	}

	@Column
	public int getTcpb() {
		return tcpb;
	}

	public void setTcpb(int tcpb) {
		this.tcpb = tcpb;
	}

	@Column
	public int getQbbdd() {
		return qbbdd;
	}

	public void setQbbdd(int qbbdd) {
		this.qbbdd = qbbdd;
	}

	@Column
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}

	@Column
	public long getHospitalid() {
		return hospitalid;
	}

	public void setHospitalid(long hospitalid) {
		this.hospitalid = hospitalid;
	}
}
