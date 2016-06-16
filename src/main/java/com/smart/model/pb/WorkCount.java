package com.smart.model.pb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.smart.model.BaseObject;

@Entity
@Table(name="WORKCOUNT")
public class WorkCount extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1723282620886334950L;
	
	private Long id;
	private String workMonth;//年月
	private String worker;// 员工
	private double workTime;//月工作班时
	private double monthOff;//月休息 时间
	private double yjx; //月积休
	private String section;//科室
	private double holiday;//年休
	private double defeholiday;//历休使用
	
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WORK_COUNT")
	@SequenceGenerator(name = "SEQ_WORK_COUNT", sequenceName = "work_count_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="workmonth")
	public String getWorkMonth() {
		return workMonth;
	}
	public void setWorkMonth(String workMonth) {
		this.workMonth = workMonth;
	}
	
	@Column
	public String getWorker() {
		return worker;
	}
	public void setWorker(String worker) {
		this.worker = worker;
	}
	
	@Column(name="worktime")
	public double getWorkTime() {
		return workTime;
	}
	public void setWorkTime(double workTime) {
		this.workTime = workTime;
	}
	
	@Column(name="monthoff")
	public double getMonthOff() {
		return monthOff;
	}
	public void setMonthOff(double monthOff) {
		this.monthOff = monthOff;
	}
	
	@Column
	public double getYjx() {
		return yjx;
	}
	public void setYjx(double yjx) {
		this.yjx = yjx;
	}
	
	@Column
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	
	@Column
	public double getHoliday() {
		return holiday;
	}
	public void setHoliday(double holiday) {
		this.holiday = holiday;
	}
	
	@Column
	public double getDefeholiday() {
		return defeholiday;
	}
	public void setDefeholiday(double defeholiday) {
		this.defeholiday = defeholiday;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	

	
}
