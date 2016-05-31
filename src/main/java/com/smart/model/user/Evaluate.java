package com.smart.model.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.smart.model.BaseObject;

/**
 * 评价，用户对病案的评价
 */
@Entity
@Table(name = "l_evaluate")
public class Evaluate extends BaseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String sampleno;
	private String collector;
	private String evaluator;
	private String content;
	private Date evaluatetime;
	
	/**
	 * 主键id，自增
	 */
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EVALUTEA")
	@SequenceGenerator(name = "SEQ_EVALUTEA", sequenceName = "evaluate_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column
	public String getSampleno() {
		return sampleno;
	}

	public void setSampleno(String sampleno) {
		this.sampleno = sampleno;
	}

	@Column
	public String getCollector() {
		return collector;
	}

	public void setCollector(String collector) {
		this.collector = collector;
	}

	@Column
	public String getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(String evaluator) {
		this.evaluator = evaluator;
	}

	@Column
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column
	public Date getEvaluatetime() {
		return evaluatetime;
	}

	public void setEvaluatetime(Date evaluatetime) {
		this.evaluatetime = evaluatetime;
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

}
