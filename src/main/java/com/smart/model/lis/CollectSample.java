package com.smart.model.lis;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.smart.model.BaseObject;

/**
 * 样本收藏
 */
@Entity
@Table(name = "l_collectSample")
public class CollectSample extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4728572724535443262L;

	private Long id;
	
	private String username;
	private String name;
	private String sampleno;
	private Date collecttime;
	private String type;
	private String bamc;
	
	/**
	 * 主键、自增
	 */
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_COLLECTSAMPLE")
	@SequenceGenerator(name = "SEQ_COLLECTSAMPLE", sequenceName = "collectsample_sequence", allocationSize=1)	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 收藏者id
	 */
	@Column(length=10)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * 收藏者姓名
	 */
	@Column(length=20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 收藏的样本id
	 */
	@Column(length=20)
	public String getSampleno() {
		return sampleno;
	}
	
	public void setSampleno(String sampleno) {
		this.sampleno = sampleno;
	}

	/**
	 * 收藏者收藏该样本的时间
	 */
	@Column
	public Date getCollecttime() {
		return collecttime;
	}
	
	public void setCollecttime(Date collecttime) {
		this.collecttime = collecttime;
	}

	/**
	 * 收藏的样本的分类
	 */
	@Column(length=20)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 病案名称
	 */
	@Column
	public String getBamc() {
		return bamc;
	}

	public void setBamc(String bamc) {
		this.bamc = bamc;
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