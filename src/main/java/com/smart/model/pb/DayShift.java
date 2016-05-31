package com.smart.model.pb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "WORKDAYSHIFT")
public class DayShift {
	
	private Long id;
	
	private String week;
	private String section;
	private String shift;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DAY_SHIFT")
	@SequenceGenerator(name = "SEQ_DAY_SHIFT", sequenceName = "day_shift_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column
	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	@Column
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	@Column
	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}
	
	@Transient
	public String getDay() {
		return week.replace("周", "");
	}

}
