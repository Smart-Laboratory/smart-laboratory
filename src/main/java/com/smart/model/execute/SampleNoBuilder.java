package com.smart.model.execute;

import javax.persistence.Table;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
@Table(name="l_sampleno_auto")
public class SampleNoBuilder {

	private Long id;
	
	private String ksdm;
	private String groupId1;
	private int sampleNo1;
	private String groupId2;
	private int sampleNo2;
	private String groupId3;
	private int sampleNo3;
	private String groupId4;
	private int sampleNo4;
	private String groupId11;
	private int sampleNo11;
	private String groupId12;
	private int sampleNo12;
	private String groupId13;
	private int sampleNo13;
	private String groupId14;
	private int sampleNo14;
	
	private Date now;
	private Date nextday;
	private boolean isuse;
	private String notes;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SAMPLE_AUTO_TYPE")
	@SequenceGenerator(name = "SAMPLE_AUTO_TYPE", sequenceName = "sample_auto_sequence", allocationSize = 1)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getKsdm() {
		return ksdm;
	}
	public void setKsdm(String ksdm) {
		this.ksdm = ksdm;
	}
	
	@Column
	public String getGroupId1() {
		return groupId1;
	}
	public void setGroupId1(String groupId1) {
		this.groupId1 = groupId1;
	}

	@Column
	public int getSampleNo1() {
		return sampleNo1;
	}
	public void setSampleNo1(int sampleNo1) {
		this.sampleNo1 = sampleNo1;
	}

	@Column
	public String getGroupId2() {
		return groupId2;
	}
	public void setGroupId2(String groupId2) {
		this.groupId2 = groupId2;
	}

	@Column
	public int getSampleNo2() {
		return sampleNo2;
	}
	public void setSampleNo2(int sampleNo2) {
		this.sampleNo2 = sampleNo2;
	}

	@Column
	public String getGroupId3() {
		return groupId3;
	}
	public void setGroupId3(String groupId3) {
		this.groupId3 = groupId3;
	}

	@Column
	public int getSampleNo3() {
		return sampleNo3;
	}
	public void setSampleNo3(int sampleNo3) {
		this.sampleNo3 = sampleNo3;
	}

	@Column
	public String getGroupId4() {
		return groupId4;
	}
	public void setGroupId4(String groupId4) {
		this.groupId4 = groupId4;
	}

	@Column
	public int getSampleNo4() {
		return sampleNo4;
	}
	public void setSampleNo4(int sampleNo4) {
		this.sampleNo4 = sampleNo4;
	}

	@Column
	public String getGroupId11() {
		return groupId11;
	}
	public void setGroupId11(String groupId11) {
		this.groupId11 = groupId11;
	}

	@Column
	public int getSampleNo11() {
		return sampleNo11;
	}
	public void setSampleNo11(int sampleNo11) {
		this.sampleNo11 = sampleNo11;
	}

	@Column
	public String getGroupId12() {
		return groupId12;
	}
	public void setGroupId12(String groupId12) {
		this.groupId12 = groupId12;
	}

	@Column
	public int getSampleNo12() {
		return sampleNo12;
	}
	public void setSampleNo12(int sampleNo12) {
		this.sampleNo12 = sampleNo12;
	}

	@Column
	public String getGroupId13() {
		return groupId13;
	}
	public void setGroupId13(String groupId13) {
		this.groupId13 = groupId13;
	}

	@Column
	public int getSampleNo13() {
		return sampleNo13;
	}
	public void setSampleNo13(int sampleNo13) {
		this.sampleNo13 = sampleNo13;
	}

	@Column
	public String getGroupId14() {
		return groupId14;
	}
	public void setGroupId14(String groupId14) {
		this.groupId14 = groupId14;
	}

	@Column
	public int getSampleNo14() {
		return sampleNo14;
	}
	public void setSampleNo14(int sampleNo14) {
		this.sampleNo14 = sampleNo14;
	}

	@Column
	public Date getNow() {
		return now;
	}
	public void setNow(Date now) {
		this.now = now;
	}

	@Column
	public Date getNextday() {
		return nextday;
	}
	public void setNextday(Date nextday) {
		this.nextday = nextday;
	}

	@Column
	public boolean isIsuse() {
		return isuse;
	}
	public void setIsuse(boolean isuse) {
		this.isuse = isuse;
	}

	@Column
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
}
