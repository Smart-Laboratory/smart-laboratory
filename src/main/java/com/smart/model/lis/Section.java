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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.smart.model.reagent.Batch;
import com.smart.model.reagent.Combo;
import com.smart.model.reagent.In;
import com.smart.model.reagent.Out;

/**
 * 检验科室分类
 */
@Entity
@Table(name = "l_deparrt")
public class Section {
	
	private long id;    //主键
	private String code;
	private String name;
	
	private Hospital hospital;
	private Set<Batch> batchs;
	private Set<In> ins;
	private Set<Out> outs;
	private Set<Combo> combos;
	
	/**
	 * 主键、自增
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_DEPART")
	@SequenceGenerator(name = "SEQ_DEPART", sequenceName = "depart_sequence", allocationSize=1)	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 科室代码
	 */
	@Column(length=30)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 科室名称
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
	
	@ManyToMany(targetEntity = Batch.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "lab_batch_section", 
			joinColumns = { @JoinColumn(name = "section_id", referencedColumnName = "id") }, 
			inverseJoinColumns = @JoinColumn(name = "batch_id", referencedColumnName = "id"))
	public Set<Batch> getBatchs() {
		return batchs;
	}

	public void setBatchs(Set<Batch> batchs) {
		this.batchs = batchs;
	}

	@ManyToMany(targetEntity = In.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "lab_in_section", 
			joinColumns = { @JoinColumn(name = "section_id", referencedColumnName = "id") }, 
			inverseJoinColumns = @JoinColumn(name = "in_id", referencedColumnName = "id"))
	public Set<In> getIns() {
		return ins;
	}

	public void setIns(Set<In> ins) {
		this.ins = ins;
	}

	@ManyToMany(targetEntity = Out.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "lab_out_section", 
			joinColumns = { @JoinColumn(name = "section_id", referencedColumnName = "id") }, 
			inverseJoinColumns = @JoinColumn(name = "out_id", referencedColumnName = "id"))
	public Set<Out> getOuts() {
		return outs;
	}

	public void setOuts(Set<Out> outs) {
		this.outs = outs;
	}

	@ManyToMany(targetEntity = Combo.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(
			name = "lab_combo_section", 
			joinColumns = { @JoinColumn(name = "section_id", referencedColumnName = "id") }, 
			inverseJoinColumns = @JoinColumn(name = "combo_id", referencedColumnName = "id"))
	public Set<Combo> getCombos() {
		return combos;
	}

	public void setCombos(Set<Combo> combos) {
		this.combos = combos;
	}

}
