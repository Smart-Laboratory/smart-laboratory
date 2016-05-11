package com.smart.model.request;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * 检验医疗收费套餐项目对照
 */
@Entity
@Table(name = "gy_sfxmtc")
public class SFXMTC {
	
	private long id;			//自增id
	private long tcid;		//套餐id
	private long sfxmid;	//收费项目id
	private int num;		//数量
	private int order;		//排序
	
	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public long getTcid() {
		return tcid;
	}
	
	public void setTcid(long tcid) {
		this.tcid = tcid;
	}
	
	@Column
	public long getSfxmid() {
		return sfxmid;
	}
	
	public void setSfxmid(long sfxmid) {
		this.sfxmid = sfxmid;
	}
	
	@Column
	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
	@Column
	public int getOrder() {
		return order;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
}
