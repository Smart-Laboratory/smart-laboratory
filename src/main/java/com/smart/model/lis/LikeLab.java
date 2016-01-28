package com.smart.model.lis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "l_likelab")
public class LikeLab {
	
	private String lab;
	private String likeLab;
	
	@Id
	public String getLab() {
		return lab;
	}
	
	public void setLab(String lab) {
		this.lab = lab;
	}
	
	@Column
	public String getLikeLab() {
		return likeLab;
	}
	
	public void setLikeLab(String likeLab) {
		this.likeLab = likeLab;
	}
	
}
