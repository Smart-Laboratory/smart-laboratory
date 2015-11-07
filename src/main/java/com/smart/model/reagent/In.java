package com.smart.model.reagent;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.smart.model.BaseObject;
import com.smart.model.lis.Section;


/**
 * 卫生材料入库
 */
@Entity
@Table(name = "rg_in")
public class In extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9211452266065128614L;
	
	private Long id;
	private String batch;
	private int isqualified;
	private String operator;
	private Date indate;
	private String exdate;
	private String notes;
	private int num;
	
	private Reagent reagent;
	private Section section;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HMI")
	@SequenceGenerator(name = "SEQ_HMI", sequenceName = "hmi_sequence", allocationSize = 1)*/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(length=30)
	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	@Column
	public int getIsqualified() {
		return isqualified;
	}

	public void setIsqualified(int isqualified) {
		this.isqualified = isqualified;
	}

	@Column(length=20)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column
	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

	@Column(length=20)
	public String getExdate() {
		return exdate;
	}

	public void setExdate(String exdate) {
		this.exdate = exdate;
	}

	@Column(length=100)
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Column
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "rg_id")
	public Reagent getReagent() {
		return reagent;
	}

	public void setReagent(Reagent reagent) {
		this.reagent = reagent;
	}
	
	@ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "section_id")
	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
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
