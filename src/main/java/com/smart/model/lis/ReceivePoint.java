package com.smart.model.lis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "l_receive_point")
public class ReceivePoint {
	
	private long id;
	private String name;
	private String code;
	private int type;
	private String lab;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_RECEIVE_POINT")
	@SequenceGenerator(name = "SEQ_RECEIVE_POINT", sequenceName = "receive_point_sequence", allocationSize=1)	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
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
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	@Column
	public String getLab() {
		return lab;
	}
	
	public void setLab(String lab) {
		this.lab = lab;
	}
}
