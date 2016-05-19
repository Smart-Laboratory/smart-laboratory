package com.smart.model.lis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.smart.model.BaseObject;
import org.springmodules.validation.bean.conf.loader.annotation.handler.NotNull;

/**
 * 科室内部 部门分类
 */
@Entity
@Table(name = "l_depart")
public class Section extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7918375753453175457L;

	private Long id;    //主键
	private String code;
	private String name;
	private long hospitalId;
	
	/**
	 * 主键、自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_DEPART")
	@SequenceGenerator(name = "SEQ_DEPART", sequenceName = "depart_sequence", allocationSize=1)*/	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 部门代码
	 */
	@Column(length=30)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 部门名称
	 */
	@Column(length=50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="hospital_id")
	public long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(long hospitalId) {
		this.hospitalId = hospitalId;
	}
	
	public String toString() {
		return null;
	}

	public boolean equals(Object o) {
		return false;
	}

	public int hashCode() {
		return 0;
	}

}
