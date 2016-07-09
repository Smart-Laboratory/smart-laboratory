package com.smart.model.pb;

import java.util.Date;

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
	private int state;//1,管理员可见 2,排班人员可见 5,所有人可见
	
	private String operator;
	private Date operatime;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ARRANGE")
	@SequenceGenerator(name = "SEQ_ARRANGE", sequenceName = "arrange_sequence", allocationSize = 1)
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
	
	@Column
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Column
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column
	public Date getOperatime() {
		return operatime;
	}

	public void setOperatime(Date operatime) {
		this.operatime = operatime;
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
	
	@Transient
	public String getKey3() {
		return this.shift + "-" + this.date;
	}
}
