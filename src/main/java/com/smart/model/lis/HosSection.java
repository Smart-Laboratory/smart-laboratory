package com.smart.model.lis;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 医院内部 科室分类
 */
@Entity
@Table(name="l_section")
public class HosSection {
	
	private long id;    //主键
	private String code;
	private String name;
	
	private Hospital hospital;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_SECTION")
	@SequenceGenerator(name = "SEQ_SECTION", sequenceName = "section_sequence", allocationSize=1)	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(optional=false,cascade=CascadeType.MERGE)
	@JoinColumn(name="hospital_id",referencedColumnName="id")
	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

}
