package com.smart.model.reagent;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.smart.model.BaseObject;

/**
 * 试剂批号和库存信息
 */
@Entity
@Table(name = "rg_batch")
public class Batch extends BaseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1947641894709915495L;
	
	private Long id;			// 主键
	private String batch;		// 批号
	private String expdate;		// 出库日期
	private Integer num;		// 数量
	private Integer subnum;		// 子数量
	private Long rgId;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BAN")
	@SequenceGenerator(name = "SEQ_BAN", sequenceName = "ban_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 批号
	 */
	@Column(length = 30)
	public String getBatch() {
		return batch;
	}
	
	public void setBatch(String batch) {
		this.batch = batch;
	}
	
	@Column(length = 30)
	public String getExpdate() {
		return expdate;
	}
	
	public void setExpdate(String expdate) {
		this.expdate = expdate;
	}
	
	@Column
	public Integer getNum() {
		return num;
	}
	
	public void setNum(Integer num) {
		this.num = num;
	}
	
	@Column
	public Integer getSubnum() {
		return subnum;
	}
	
	public void setSubnum(Integer subnum) {
		this.subnum = subnum;
	}

	@Column(name = "rg_id")
	public Long getRgId() {
		return rgId;
	}

	public void setRgId(Long rgId) {
		this.rgId = rgId;
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
