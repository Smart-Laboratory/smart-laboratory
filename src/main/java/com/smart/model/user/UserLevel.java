package com.smart.model.user;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.smart.model.BaseObject;

@Entity
@Table(name="lab_user_level")
public class UserLevel extends BaseObject{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6030878710666268515L;

	private Long id;
	
	private int level;
	private int low;
	private int high;
	private String levelname;
	private String color;
	
	/**
	 * 主键、自增
	 */
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_USER_LEVEL")
	@SequenceGenerator(name = "SEQ_USER_LEVEL", sequenceName = "user_level_sequence", allocationSize=1)	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="user_level")
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	@Column
	public int getLow() {
		return low;
	}
	
	public void setLow(int low) {
		this.low = low;
	}
	
	@Column
	public int getHigh() {
		return high;
	}
	
	public void setHigh(int high) {
		this.high = high;
	}
	
	@Column
	public String getLevelname() {
		return levelname;
	}
	
	public void setLevelname(String levelname) {
		this.levelname = levelname;
	}

	@Column
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
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
