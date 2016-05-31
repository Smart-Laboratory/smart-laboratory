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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.smart.model.BaseObject;

/**
 * 套餐
 */
@Entity
@Table(name = "rg_combo")
public class Combo extends BaseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2344651787432377947L;
	
	private Long id;
	private String name;
	private String creator;
	private Date createtime;
	
	private Set<Reagent> reagents;
	private String lab;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HMC")
	@SequenceGenerator(name = "SEQ_HMC", sequenceName = "hmc_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Column(length=50)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length=20)
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@ManyToMany(targetEntity = Reagent.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "lab_combo_reagent", 
			joinColumns = { @JoinColumn(name = "combo_id", referencedColumnName = "id") }, 
			inverseJoinColumns = @JoinColumn(name = "rg_id", referencedColumnName = "id"))
	public Set<Reagent> getReagents() {
		return reagents;
	}

	public void setReagents(Set<Reagent> reagents) {
		this.reagents = reagents;
	}
	
	@Column
	public String getLab() {
		return lab;
	}

	public void setLab(String lab) {
		this.lab = lab;
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
