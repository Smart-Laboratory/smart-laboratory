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
@Table(name = "WORKARRANGE")
public class Arrange {

	private Long id;
	
	private String date;
	private String shift;
	private String worker;
	private String section;
	private int type;//员工、实习生
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ARR")
//	@SequenceGenerator(name = "SEQ_ARR", sequenceName = "arr_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "riqi")
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Column
	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	@Column
	public String getWorker() {
		return worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	@Column
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	@Column
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Transient
	public Integer getKey() {
		return Integer.parseInt(this.date.split("-")[2]);
	}
	
	@Transient
	public String getKey2() {
		if(type == 0) {
			return this.worker + "-" + Integer.parseInt(this.date.split("-")[2]);
		} else {
			return this.worker + "-" + this.date;
		}
	}
}
