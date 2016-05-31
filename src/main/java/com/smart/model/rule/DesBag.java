package com.smart.model.rule;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.smart.model.BaseObject;

@Entity
@Table(name="lab_description_bag")
public class DesBag extends BaseObject implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -1549201763462090327L;

	
	private Long id;
	
	private String name;
	
	private Long parentID;
	
	private long hospitalId;

	public  DesBag() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DES_BAG")
	@SequenceGenerator(name = "SEQ_DES_BAG", sequenceName = "des_bag_sequence", allocationSize = 1)
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

	@Column(name="parent_id")
	public Long getParentID() {
		return parentID;
	}

	public void setParentID(Long parentID) {
		this.parentID = parentID;
	}

	@Column(name="hospital_id")
	public long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(long hospitalId) {
		this.hospitalId = hospitalId;
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
