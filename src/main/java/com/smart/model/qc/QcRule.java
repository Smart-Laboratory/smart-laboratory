package com.smart.model.qc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 质控规则基本信息
 */
@Entity
@Table(name = "l_qc_rule")
public class QcRule {
	
	private Long id;
	private String name;
	private String describe;
	private int inuse;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_QC_RULE")
	@SequenceGenerator(name="SEQ_QC_RULE", sequenceName = "qc_rule_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
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
	public String getDescribe() {
		return describe;
	}
	
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	@Column
	public int getInuse() {
		return inuse;
	}
	
	public void setInuse(int inuse) {
		this.inuse = inuse;
	}
	
	@Transient
	public String getUseInfo() {
		return inuse == 1 ? "是" : "否";
	}
}
