package com.smart.model.lis;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.smart.model.BaseObject;
import com.smart.model.reagent.Combo;
import com.smart.model.reagent.In;
import com.smart.model.reagent.Out;
import com.smart.model.reagent.Reagent;

/**
 * 科室内部 部门分类
 */
@Entity
@Table(name = "l_depart")
public class Section extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7918375753453175457L;
	
	private Long id;    //主键
	private String code;
	private String name;
	
	private Hospital hospital;
	private Set<Reagent> reagents;
	private Set<In> ins;
	private Set<Out> outs;
	private Set<Combo> combos;
	
	/**
	 * 主键、自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_DEPART")
	@SequenceGenerator(name = "SEQ_DEPART", sequenceName = "depart_sequence", allocationSize=1)	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 部门代码
	 */
	@Column(length=30)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 部门名称
	 */
	@Column(length=50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(optional=false,cascade=CascadeType.MERGE)
	@JoinColumn(name="hospital_id",referencedColumnName="id")
	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
	
	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, targetEntity = Reagent.class, mappedBy = "section")
	@OrderBy("id asc")
	public Set<Reagent> getReagents() {
		return reagents;
	}

	public void setReagents(Set<Reagent> reagents) {
		this.reagents = reagents;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, targetEntity = In.class, mappedBy = "section")
	@OrderBy("id desc")
	public Set<In> getIns() {
		return ins;
	}

	public void setIns(Set<In> ins) {
		this.ins = ins;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, targetEntity = Out.class, mappedBy = "section")
	@OrderBy("id desc")
	public Set<Out> getOuts() {
		return outs;
	}

	public void setOuts(Set<Out> outs) {
		this.outs = outs;
	}

	@OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, targetEntity = Combo.class, mappedBy = "section")
	@OrderBy("id asc")
	public Set<Combo> getCombos() {
		return combos;
	}

	public void setCombos(Set<Combo> combos) {
		this.combos = combos;
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
