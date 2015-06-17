package com.smart.model.lis;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;




/**
 * 危急值记录,包括护士与医生的处理信息
 */
@Entity
@Table(name = "l_criticalrecord")
public class CriticalRecord {
	
	private Long id; //主键 流水号
//	private String sampleNo; // 出现危机值的样本编号
	private String criticalValues; //危机值
	private int criticalDealFlag; // 危机值是否被处理的标记
	private String criticalDeal; // 危机值处理信息
	private Date criticalDealTime;
	private String criticalContent;	//危急内容
	private String criticalDealPerson;	//处理人员
	
	private Sample sample;
//	private Patient patient;
	
	private String doctorName;
	private Date doctorDealTime;
	private String doctorDealContent;
	private long doctorId;
	private int isDoctorDealed;
	private String nurseName;
	private Date nurseDealTime;
	private String nurseDealContent;
	private long nurseId;
	private int isNurseDealed;
	private String testerName;
	private Date testerDealTime;
	private String testerDealContent;
	private long testerId;
	private int isTesterDealed;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SEQ_CRITICAL")
	@SequenceGenerator(name="SEQ_CRITICAL",sequenceName="critical_seq",allocationSize=1)
	public Long getId(){
		return this.id;
	}
	public void setId(Long id){
		this.id = id;
	}
	
	
	@OneToOne(optional=false, cascade=CascadeType.REFRESH)
	@JoinColumn(name="sampleno",referencedColumnName="sampleno",unique=true)
	public Sample getSample(){
		return sample;
	}
	public void setSample(Sample sample){
		this.sample = sample;
	}
	
	
//	@ManyToOne(optional=false,cascade=CascadeType.ALL)
//	@JoinColumn(name="sample_no",referencedColumnName="sampleNo",unique=true)
//	public Sample getSample(){
//		return sample;
//	}
//	public void setSample(Sample sample){
//		this.sample = sample; 
//	}
	
	/**
	 * 危急值
	 */
	@Column(name = "CRITICALVALUES")
	public String getCriticalValues() {
		return criticalValues;
	}

	public void setCriticalValues(String criticalValues) {
		this.criticalValues = criticalValues;
	}
	
	/**
	 * 危急值标识
	 */
	@Column(name = "DANGERFLAG")
	public int getCriticalDealFlag() {
		return criticalDealFlag;
	}

	public void setCriticalDealFlag(int criticalDealFlag) {
		this.criticalDealFlag = criticalDealFlag;
	}

	/**
	 * 危急值处理结果
	 */
	@Column(name = "DANGERDEAL")
	public String getCriticalDeal() {
		return criticalDeal;
	}

	public void setCriticalDeal(String criticalDeal) {
		this.criticalDeal = criticalDeal;
	}
	
	/**
	 * 危急值处理时间
	 */
	@Column(name = "DANGERTIME")
	public Date getCriticalDealTime() {
		return criticalDealTime;
	}

	public void setCriticalDealTime(Date criticalDealTime) {
		this.criticalDealTime = criticalDealTime;
	}
	
	/**
	 * 危急值内容
	 */
	@Column(name = "DANGERCONTENT")
	public String getCriticalContent() {
		return criticalContent;
	}

	public void setCriticalContent(String criticalContent) {
		this.criticalContent = criticalContent;
	}
	
	/**
	 * 危急值处理者
	 */
	@Column(name = "DEALPERSON")
	public String getCriticalDealPerson() {
		return criticalDealPerson;
	}

	public void setCriticalDealPerson(String criticalDealPerson) {
		this.criticalDealPerson = criticalDealPerson;
	}
	
	@Column(name = "DOCTORNAME")
	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	@Column(name = "DOCTORDEALTIME")
	public Date getDoctorDealTime() {
		return doctorDealTime;
	}

	public void setDoctorDealTime(Date doctorDealTime) {
		this.doctorDealTime = doctorDealTime;
	}

	@Column(name = "DOCTORDEALCONTENT")
	public String getDoctorDealContent() {
		return doctorDealContent;
	}

	public void setDoctorDealContent(String doctorDealContent) {
		this.doctorDealContent = doctorDealContent;
	}

	@Column(name = "DOCTORID")
	public long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(long doctorId) {
		this.doctorId = doctorId;
	}

	@Column(name = "NURSENAME")
	public String getNurseName() {
		return nurseName;
	}

	public void setNurseName(String nurseName) {
		this.nurseName = nurseName;
	}

	@Column(name = "NURSEDEALTIME")
	public Date getNurseDealTime() {
		return nurseDealTime;
	}

	public void setNurseDealTime(Date nurseDealTime) {
		this.nurseDealTime = nurseDealTime;
	}

	@Column(name = "NURSEDEALCONTENT")
	public String getNurseDealContent() {
		return nurseDealContent;
	}

	public void setNurseDealContent(String nurseDealContent) {
		this.nurseDealContent = nurseDealContent;
	}

	@Column(name = "NURSEID")
	public long getNurseId() {
		return nurseId;
	}

	public void setNurseId(long nurseId) {
		this.nurseId = nurseId;
	}

	@Column(name = "TESTERNAME")
	public String getTesterName() {
		return testerName;
	}

	public void setTesterName(String testerName) {
		this.testerName = testerName;
	}

	@Column(name = "TESTERDEALTIME")
	public Date getTesterDealTime() {
		return testerDealTime;
	}

	public void setTesterDealTime(Date testerDealTime) {
		this.testerDealTime = testerDealTime;
	}

	@Column(name = "TESTERDEALCONTENT")
	public String getTesterDealContent() {
		return testerDealContent;
	}

	public void setTesterDealContent(String testerDealContent) {
		this.testerDealContent = testerDealContent;
	}

	@Column(name = "TESTERID")
	public long getTesterId() {
		return testerId;
	}

	public void setTesterId(long testerId) {
		this.testerId = testerId;
	}
	
	@Column(name = "ISDOCTORDEALED")
	public int getIsDoctorDealed() {
		return isDoctorDealed;
	}

	public void setIsDoctorDealed(int isDoctorDealed) {
		this.isDoctorDealed = isDoctorDealed;
	}

	@Column(name = "ISNURSEDEALED")
	public int getIsNurseDealed() {
		return isNurseDealed;
	}

	public void setIsNurseDealed(int isNurseDealed) {
		this.isNurseDealed = isNurseDealed;
	}

	@Column(name = "ISTESTERDEALED")
	public int getIsTesterDealed() {
		return isTesterDealed;
	}

	public void setIsTesterDealed(int isTesterDealed) {
		this.isTesterDealed = isTesterDealed;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
