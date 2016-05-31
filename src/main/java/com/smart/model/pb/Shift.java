package com.smart.model.pb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "WORKSHIFT")
public class Shift {
	

	private Long id;
	
	private String name;
	private String ab;
	private String section;
	private String worktime;
	private int order;
	
	private double days;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SHIFT")
	@SequenceGenerator(name = "SEQ_SHIFT", sequenceName = "shift_sequence", allocationSize = 1)
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
	public String getAb() {
		return ab;
	}

	public void setAb(String ab) {
		this.ab = ab;
	}

	@Column
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	@Column
	public String getWorktime() {
		return worktime;
	}

	public void setWorktime(String worktime) {
		this.worktime = worktime;
	}

	@Column(name = "showord")
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Column
	public double getDays() {
		return days;
	}

	public void setDays(double days) {
		this.days = days;
	}
	
	
}
