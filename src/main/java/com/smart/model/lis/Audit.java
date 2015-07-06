package com.smart.model.lis;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 智能审核结果表
 */
@Entity
@Table(name = "l_audit")
public class Audit {
	
	private long id;    //主键
	
	private Sample sample;  //审核的样本
	
	private Date checkTime; //审核时间
	private Date printTime; //打印时间?
	private int auditStatus; //样本审核的状态
	private int auditMark; //审核标记
	private String markTests; //出现异常 需要标记的检验项目
	private String notes; //自动审核的结果记录
	private String ruleIds; //规则库生成的为题规则集，用“，”隔开
	private int hasimages;  
	
	/**
	 * 主键、自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_AUDIT")
	@SequenceGenerator(name = "SEQ_AUDIT", sequenceName = "audit_sequence", allocationSize=1)	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * 审核的样本
	 */
	@ManyToOne(optional=false, fetch = FetchType.LAZY, cascade=CascadeType.MERGE)
	@JoinColumn(name = "sample_id")
	@OrderBy(value = "checkTime des")
	public Sample getSample(){
		return this.sample;
	}
	
	public void setSample(Sample sample){
		this.sample = sample;
	}

	/**
	 * 审核时间
	 */
	@Column
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	/**
	 * 打印时间
	 */
	@Column
	public Date getPrintTime() {
		return printTime;
	}

	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	/**
	 * 审核状态
	 */
	@Column
	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * 审核标记
	 */
	@Column
	public int getAuditMark() {
		return auditMark;
	}

	public void setAuditMark(int auditMark) {
		this.auditMark = auditMark;
	}

	/**
	 * 需要标记的检验项目（检验结果不符合规则）
	 */
	@Column
	public String getMarkTests() {
		return markTests;
	}

	public void setMarkTests(String markTests) {
		this.markTests = markTests;
	}

	/**
	 * 检验结果 错误信息
	 */
	@Column
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * 不符合的规则
	 */
	@Column
	public String getRuleIds() {
		return ruleIds;
	}

	public void setRuleIds(String ruleIds) {
		this.ruleIds = ruleIds;
	}

	/**
	 * 
	 */
	@Column
	public int getHasimages() {
		return hasimages;
	}

	public void setHasimages(int hasimages) {
		this.hasimages = hasimages;
	}
	
	
	@Transient
	public String getAuditMarkValue() {
		String value = "";
		switch (getAuditMark()) {
		case 0:
			break;
		case 1:
			value = "自动";
			break;
		case 2:
			value = "差值";
			break;
		case 3:
			value = "比值";
			break;
		case 4:
			value = "少做";
			break;
		case 5:
			value = "复检";
			break;
		case 6:
			value = "危急";
			break;
		case 7:
			value = "警戒1";
			break;
		case 8:
			value = "警戒2";
			break;
		case 9:
			value = "极值";
			break;
		case 10:
			value = "自动b";
			break;
		}
		return value;
	}

	@Transient
	public String getAuditStatusValue() {
		String value = "";
		switch (getAuditStatus()) {
		case -1:
			value = "无结果";
			break;
		case 0:
			value = "未审核";
			break;
		case 1:
			value = "已通过";
			break;
		case 2:
			value = "未通过";
			break;
		default:
			value = "未审核";
			break;
		}
		return value;
	}

}
