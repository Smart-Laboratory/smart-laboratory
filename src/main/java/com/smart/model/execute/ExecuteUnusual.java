package com.smart.model.execute;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="l_executeunusual")
public class ExecuteUnusual {

	private Long laborder;
	private String patientId;
	private String part;
	private String executeMode;
	private String reaction;
	private Long time;
	private String note;
	
	@Id
	public Long getLaborder() {
		return laborder;
	}
	public void setLaborder(Long laborder) {
		this.laborder = laborder;
	}
	
	@Column
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	@Column
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}

	@Column
	public String getExecuteMode() {
		return executeMode;
	}
	public void setExecuteMode(String executemode) {
		this.executeMode = executemode;
	}

	@Column
	public String getReaction() {
		return reaction;
	}
	public void setReaction(String reaction) {
		this.reaction = reaction;
	}

	@Column
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}

	@Column
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
}
