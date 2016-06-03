package com.smart.model.lis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="l_diagnostic")
public class Diagnosis {

	private long id;
	private long descriptionId;
	private String diagnosisName;
	private String knowledgeName;
	
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DIAGNOSTIC")
	@SequenceGenerator(name="SEQ_DIAGNOSTIC", sequenceName = "diagnostic_sequence", allocationSize = 1)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="description_id")
	public long getDescriptionId() {
		return descriptionId;
	}
	public void setDescriptionId(long descriptionId) {
		this.descriptionId = descriptionId;
	}
	
	@Column(name="diagnostic")
	public String getDiagnosisName() {
		return diagnosisName;
	}
	public void setDiagnosisName(String diagnosisName) {
		this.diagnosisName = diagnosisName;
	}
	
	@Column
	public String getKnowledgeName() {
		return knowledgeName;
	}
	public void setKnowledgeName(String knowledgeName) {
		this.knowledgeName = knowledgeName;
	}
	
	
}
