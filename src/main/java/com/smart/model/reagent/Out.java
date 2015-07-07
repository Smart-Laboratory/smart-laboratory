package com.smart.model.reagent;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.smart.model.BaseObject;
import com.smart.model.lis.Section;

/**
 * 卫生材料出库
 */
@Entity
@Table(name = "rg_out")
public class Out extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 611214706096090744L;
	
	private Long id;
	private String operator;
	private Date outdate;
	private int num;
	private int testnum;				// 试剂能用多少次检验
	
	private Reagent reagent;
	private Set<Section> sections;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HMO")
	@SequenceGenerator(name = "SEQ_HMO", sequenceName = "hmo_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(length=20)
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column
	public Date getOutdate() {
		return outdate;
	}

	public void setOutdate(Date outdate) {
		this.outdate = outdate;
	}

	@Column
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	@Column
	public int getTestnum() {
		return testnum;
	}

	public void setTestnum(int testnum) {
		this.testnum = testnum;
	}

	@ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "rg_id")
	public Reagent getReagent() {
		return reagent;
	}

	public void setReagent(Reagent reagent) {
		this.reagent = reagent;
	}
	
	@ManyToMany(targetEntity = Section.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "lab_out_section", 
			joinColumns = { @JoinColumn(name = "out_id", referencedColumnName = "id") }, 
			inverseJoinColumns = @JoinColumn(name = "section_id", referencedColumnName = "id"))
	public Set<Section> getSections() {
		return sections;
	}

	public void setSections(Set<Section> sections) {
		this.sections = sections;
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
