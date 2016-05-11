package com.smart.model.lis;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smart.model.BaseObject;


@Entity
@Table(name = "SOPINDEX")
public class SOPIndex extends BaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2896353674826560544L;

	private long id;
	
	private String sop;
	private String sopid;
	private String sopname;
	private String indexid;
	private int type;
	private String lab;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SOP")
	@SequenceGenerator(name = "SEQ_SOP", sequenceName = "sop_sequence", allocationSize = 1)
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name = "sop")
	public String getSop() {
		return sop;
	}
	
	public void setSop(String sop) {
		this.sop = sop;
	}
	
	@Column(name = "sopid")
	public String getSopid() {
		return sopid;
	}
	
	public void setSopid(String sopid) {
		this.sopid = sopid;
	}
	
	@Column(name = "sopname")
	public String getSopname() {
		return sopname;
	}
	
	public void setSopname(String sopname) {
		this.sopname = sopname;
	}
	
	@Column(name = "indexid")
	public String getIndexid() {
		return indexid;
	}
	
	public void setIndexid(String indexid) {
		this.indexid = indexid;
	}
	
	@Column(name = "type")
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}

	@Column(name = "lab")
	public String getLab() {
		return lab;
	}

	public void setLab(String lab) {
		this.lab = lab;
	}
	
	@Transient
	public String getTypeStr() {
		String value = "";
		switch (this.type) {
		case 1:
			value = "科室SOP";
			break;
		case 2:
			value = "仪器SOP";
			break;
		case 3:
			value = "项目SOP";
			break;
		default:
			value = "通用SOP";
			break;
		}
		return value;
	}
	
	@Transient
	public String getUrl() {
		return "<a href='http://192.168.75.51/zhishi/doc/document/detail.html?"
				+ this.sopid + "' target='_blank'>" + this.sopname + "</a>";
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