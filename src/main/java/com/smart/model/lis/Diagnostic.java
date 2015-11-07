package com.smart.model.lis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 诊断知识库映射
 */
@Entity
@Table(name = "l_diagnostic")
public class Diagnostic {
	
	private Long id;// 主键，自增
	private String diagnostic;
	private String knowledgename;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_DIAG")
	@SequenceGenerator(name = "SEQ_DIAG", sequenceName = "diagnostic_seq", allocationSize=1)*/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 诊断名称
	 */
	@Column(name = "DIAGNOSTIC", length = 100)
	public String getDiagnostic() {
		return diagnostic;
	}
	
	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}
	
	/**
	 * 知识库对应的诊断名称
	 */
	@Column(name = "KNOWLEDGENAME", length = 100)
	public String getKnowledgename() {
		return knowledgename;
	}
	
	public void setKnowledgename(String knowledgename) {
		this.knowledgename = knowledgename;
	}

}
