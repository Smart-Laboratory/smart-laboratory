package com.smart.model.execute;

import javax.persistence.Table;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name="LAB_AUTO_SAMPLENO")
public class SampleNoBuilder {

	private Long id;
	private String labDepart;
	private String segment;
	private int startNo;
	private int endNo;
	private int nowNo;
	private int orderNo;

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAMPLE_AUTO_TYPE")
	@SequenceGenerator(name = "SAMPLE_AUTO_TYPE", sequenceName = "AUTO_SAMPLENO_SEQUENCE", allocationSize = 1)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@Column
	public String getLabDepart() {
		return labDepart;
	}

	public void setLabDepart(String labDepart) {
		this.labDepart = labDepart;
	}

	@Column
	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	@Column
	public int getStartNo() {
		return startNo;
	}

	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}

	@Column
	public int getEndNo() {
		return endNo;
	}

	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}

	@Column
	public int getNowNo() {
		return nowNo;
	}

	public void setNowNo(int nowNo) {
		this.nowNo = nowNo;
	}

	@Column
	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
}
