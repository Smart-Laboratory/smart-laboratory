package com.smart.model.request;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*
 * 住院计费详细记录表
 */
@Entity
@Table(name = "gy_jfmx")
public class JFMX {
	
	private long id;
	private long jfid;				//发票ID
	private String brid;			//病人ID
	private int infantFlag;			//婴儿标识
	private long ylxh;				//计费项目ID
	private String ylmc;			//计费项目名称
	private double je;				//总金额
	private double zlje;			//自理金额
	private double zfje;			//自负金额
	private int num;				//数量
	private int type;				//收费项目类型
	private String twicecode;		//计费项目二级编码
	private String twiceid;			//计费项目二级ID
	private int status;				//计费状态
	private Date createtime;		//收费产生时间
	private Date chargetime;		//计费时间
	private double selfpercent;		//自费比率
	private String currentsection;	//当前科室
	private String currentbed;		//当前床号
	private String chargesection;	//执行科室
	private String charger;			//执行人
	private long hospitalid;		//医院ID
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_JFMX")
	@SequenceGenerator(name = "SEQ_JFMX", sequenceName = "jfmx_sequence", allocationSize = 1)
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column
	public long getJfid() {
		return jfid;
	}

	public void setJfid(long jfid) {
		this.jfid = jfid;
	}

	@Column
	public String getBrid() {
		return brid;
	}
	
	public void setBrid(String brid) {
		this.brid = brid;
	}
	
	@Column
	public int getInfantFlag() {
		return infantFlag;
	}

	public void setInfantFlag(int infantFlag) {
		this.infantFlag = infantFlag;
	}

	@Column
	public long getYlxh() {
		return ylxh;
	}
	
	public void setYlxh(long ylxh) {
		this.ylxh = ylxh;
	}
	
	@Column
	public String getYlmc() {
		return ylmc;
	}
	
	public void setYlmc(String ylmc) {
		this.ylmc = ylmc;
	}
	
	@Column
	public double getJe() {
		return je;
	}
	
	public void setJe(double je) {
		this.je = je;
	}
	
	@Column
	public double getZlje() {
		return zlje;
	}
	
	public void setZlje(double zlje) {
		this.zlje = zlje;
	}
	
	@Column
	public double getZfje() {
		return zfje;
	}
	
	public void setZfje(double zfje) {
		this.zfje = zfje;
	}
	
	@Column
	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
	@Column
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	@Column	
	public String getTwicecode() {
		return twicecode;
	}

	public void setTwicecode(String twicecode) {
		this.twicecode = twicecode;
	}

	@Column	
	public String getTwiceid() {
		return twiceid;
	}

	public void setTwiceid(String twiceid) {
		this.twiceid = twiceid;
	}

	@Column
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column
	public Date getChargetime() {
		return chargetime;
	}

	public void setChargetime(Date chargetime) {
		this.chargetime = chargetime;
	}

	@Column
	public double getSelfpercent() {
		return selfpercent;
	}

	public void setSelfpercent(double selfpercent) {
		this.selfpercent = selfpercent;
	}

	@Column
	public String getCurrentsection() {
		return currentsection;
	}

	public void setCurrentsection(String currentsection) {
		this.currentsection = currentsection;
	}

	@Column
	public String getCurrentbed() {
		return currentbed;
	}

	public void setCurrentbed(String currentbed) {
		this.currentbed = currentbed;
	}

	@Column
	public String getChargesection() {
		return chargesection;
	}

	public void setChargesection(String chargesection) {
		this.chargesection = chargesection;
	}

	@Column
	public String getCharger() {
		return charger;
	}

	public void setCharger(String charger) {
		this.charger = charger;
	}

	@Column
	public long getHospitalid() {
		return hospitalid;
	}

	public void setHospitalid(long hospitalid) {
		this.hospitalid = hospitalid;
	}
}
