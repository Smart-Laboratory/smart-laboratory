package com.smart.model.lis;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.smart.model.BaseObject;
import com.smart.model.lis.TestResult;

//样本信息表
/**
 * 检验单样本信息表
 */
@Entity
@Table(name = "l_sample")
public class Sample extends BaseObject {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;//主键，流水号
	
	private Patient patient; //病人
	private String patientId; // 病人 就诊号
	private String departBed; //病床号
	private String sampleNo;//样本编号， 手动生成
	private int stayHospitalMode; //就诊方式（门诊、住院、急诊）
	private String section; //检验科室
	private String diagnostic; //诊断
	private String inspectionName; //检验项目及套餐名称
	private String sampleType; //样本类型 、来源（血液、粪便）
	private String sampleStatus; //样本所处的状态（申请、采集、测试。。。）
	private String labDepartMent; //检验部门
	private String labDepartMentId; //检验仪器号
	private String printFlag; //是否打印
	private String fee;	//费用
	private String feestatus;	//收费状态
	private String part;	//采集部位
	private String description; //描述
	private String note; //性状
	private String count; //采集数量
	
	
	private CriticalRecord criticalRecord;
	private Hospital hostipal;
	private Set<TestResult> results = new HashSet<TestResult>(); //检验项目的结果集
	private Set<Audit> audits = new HashSet<Audit>(); //检验项目的结果集
	private Set<Process> process = new HashSet<Process>(); //检验项目的结果集

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_SAMPLE")
	@SequenceGenerator(name = "SEQ_SAMPLE", sequenceName = "sample_seq", allocationSize=1)
	public Long getId(){
		return this.id;
	}
	public void setId(Long id){
		this.id = id;
	}
	
	/**
	 * 危机值处理
	 */
	@OneToOne(optional=true, cascade=CascadeType.ALL, mappedBy = "sample")
	public CriticalRecord getCriticalRecord(){
		return criticalRecord;
	}
	public void setCriticalRecord(CriticalRecord cri){
		this.criticalRecord = cri;
	}
	/**
	 * 检验病人
	 */
	@ManyToOne(optional=false,cascade=CascadeType.MERGE)
	@JoinColumn(name="patientblh",referencedColumnName="blh",unique=true)
	public Patient getPatient(){
		return patient;
	}
	public void setPatient(Patient patient){
		this.patient = patient; 
	}
	
	/**
	 * 检验单开单时间
	 */
	@Column(name = "SAMPLENO", length = 20)
	public String getSampleNo() {
		return sampleNo;
	}

	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}

	/**
	 * 就诊类型
	 */
	@Column(name = "STAYHOSPITALMODE", length = 10)
	public int getStayHospitalMode() {
		return stayHospitalMode;
	}

	public void setStayHospitalMode(int stayHospitalMode) {
		this.stayHospitalMode = stayHospitalMode;
	}
	
	/**
	 * 就诊科室
	 */
	@Column(name = "SECTION", length = 20)
	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	/**
	 * 病床号
	 */
	@Column(name = "DEPART_BED", length = 10)
	public String getDepartBed() {
		return departBed;
	}

	public void setDepartBed(String departBed) {
		this.departBed = departBed;
	}
	
	/**
	 * 病例号
	 */
	@Column(name = "PATIENTID", length = 20)
	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientid) {
		this.patientId = patientid;
	}
	
	/**
	 * 诊断
	 */
	@Column(name = "DIAGNOSTIC")
	public String getDiagnostic() {
		return diagnostic;
	}

	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}
	
	/**
	 * 检验仪器号
	 */
	@Column(name = "LABDEPARTMENTID", length = 20)
	public String getLabDepartMentId() {
		return labDepartMentId;
	}

	public void setLabDepartMentId(String labDepartMentId) {
		this.labDepartMentId = labDepartMentId;
	}
	
	/**
	 * 打印标识
	 */
	@Column(name = "ISPRINT", length = 1)
	public String getPrintFlag() {
		return printFlag;
	}

	public void setPrintFlag(String printFlag) {
		this.printFlag = printFlag;
	}
	
	
	/**
	 * 检验目的
	 */
	@Column(name = "INSPECTIONNAME")
	public String getInspectionName() {
		return inspectionName;
	}
	
	public void setInspectionName(String inspectionName) {
		this.inspectionName = inspectionName;
	}
	
	/**
	 * 样本类型
	 */
	@Column(name = "SAMPLETYPE")
	public String getSampleType() {
		return sampleType;
	}
	
	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}
	
	/**
	 * 样本状态
	 */
	@Column(name = "samplestatus")
	public String getSampleStatus() {
		return sampleStatus;
	}
	
	public void setSampleStatus(String sampleStatus) {
		this.sampleStatus = sampleStatus;
	}
	
	/**
	 * 实验室部门
	 */
	@Column(name = "labdepartment")
	public String getLabDepartMent() {
		return labDepartMent;
	}
	
	public void setLabDepartMent(String labDepartMent) {
		this.labDepartMent = labDepartMent;
	}
	
	/**
	 * 实验室部门
	 */
	@Column(name = "fee")
	public String getFee() {
		return fee;
	}
	
	public void setFee(String fee) {
		this.fee = fee;
	}
	
	/**
	 * 实验室部门
	 */
	@Column(name = "feestatus")
	public String getFeestatus() {
		return feestatus;
	}
	
	public void setFeestatus(String feestatus) {
		this.feestatus = feestatus;
	}
	
	/**
	 * 采集部位
	 */
	@Column(name = "part")
	public String getPart() {
		return part;
	}
	
	public void setPart(String part) {
		this.part = part;
	}
	
	/**
	 * 描述
	 */
	@Column(name = "description")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 说明
	 */
	@Column(name = "note")
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * 采集数量
	 */
	@Column(name = "count")
	public String getCount() {
		return count;
	}
	
	public void setCount(String count) {
		this.count = count;
	}
	
	/**
	 * 该样本所做的结果集
	 */
	@OneToMany(targetEntity = TestResult.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE,mappedBy="sample")
	public Set<Audit> getAudits() {
		return audits;
	}
	
	public void setAudits(Set<Audit> audits) {
		this.audits = audits;
	}
	
	/**
	 * 该样本所做的结果集
	 */
	@OneToMany(targetEntity = TestResult.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE,mappedBy="sample")
	public Set<Process> getProcess() {
		return process;
	}
	
	public void setProcess(Set<Process> process) {
		this.process = process;
	}
	
	/**
	 * 该样本所做的结果集
	 */
	@OneToMany(targetEntity = TestResult.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(name = "lab_patient_result", joinColumns = { @JoinColumn(name = "sample_id", referencedColumnName = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "sample_no", referencedColumnName = "sampleNo"),
			@JoinColumn(name = "test_id", referencedColumnName = "testId") })
	public Set<TestResult> getResults() {
		return results;
	}

	public void setResults(Set<TestResult> results) {
		this.results = results;
	}
	
	@ManyToOne(optional=false,cascade=CascadeType.MERGE)
	@JoinColumn(name="hospital_id",referencedColumnName="id")
	public Hospital getHospital(){
		return hostipal;
	}
	public void setHospital(Hospital hospital){
		this.hostipal = hospital; 
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
