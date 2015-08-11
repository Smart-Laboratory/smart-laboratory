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
import javax.persistence.Transient;

import com.smart.model.BaseObject;
import com.smart.model.lis.TestResult;

//样本信息表
/**
 * 检验单样本信息表
 */
@Entity
@Table(name = "l_sample")
public class Sample extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -583231534825801731L;

	private Long id;//主键，流水号
	
	private String patientId; // 病人 就诊号
	private String departBed; //病床号
	private String sampleNo;//样本编号， 手动生成
	private Integer stayHospitalMode; //就诊方式（门诊、住院、急诊）
	private String hosSection; //申请科室
	private String diagnostic; //诊断
	private String inspectionName; //检验项目及套餐名称
	private String ylxh;//检验项目及套餐序号
	private String sampleType; //样本类型 、来源（血液、粪便）
	private Integer sampleStatus; //样本所处的状态（申请、采集、测试。。。）
	private String printFlag; //是否打印
	private String fee;	//费用
	private String feestatus;	//收费状态
	private String part;	//采集部位
	private String description; //描述
	private String note; //性状
	private String count; //采集数量
	private Integer modifyFlag;//修改标识
	private Integer writeback;//写回标识
	private Integer iswriteback;//写回标识
	private Integer hasimages;//是否包含图片
	private int cycle;
	
	private int auditStatus; //样本审核的状态
	private int auditMark; //审核标记
	private String markTests; //出现异常 需要标记的检验项目
	private String notes; //自动审核的结果记录
	private String ruleIds; //规则库生成的为题规则集，用“，”隔开
	private String checkerOpinion;
	
	private Patient patient; //病人
	private CriticalRecord criticalRecord;
	private Section section;
	private Set<TestResult> results = new HashSet<TestResult>(); //检验项目的结果集
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
	 * 检验单开单时间
	 */
	@Column(name = "sampleno", length = 20)
	public String getSampleNo() {
		return sampleNo;
	}
	
	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}

	/**
	 * 就诊类型
	 */
	@Column(name = "stayhospitalmode", length = 10)
	public int getStayHospitalMode() {
		return stayHospitalMode;
	}

	public void setStayHospitalMode(int stayHospitalMode) {
		this.stayHospitalMode = stayHospitalMode;
	}
	
	/**
	 * 就诊科室
	 */
	@Column(name = "hossection", length = 20)
	public String getHosSection() {
		return hosSection;
	}

	public void setHosSection(String hosSection) {
		this.hosSection = hosSection;
	}

	/**
	 * 病床号
	 */
	@Column(name = "depart_bed", length = 10)
	public String getDepartBed() {
		return departBed;
	}

	public void setDepartBed(String departBed) {
		this.departBed = departBed;
	}
	
	/**
	 * 病例号
	 */
	@Column(name = "patientid", length = 20)
	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientid) {
		this.patientId = patientid;
	}
	
	/**
	 * 诊断
	 */
	@Column(name = "diagnostic")
	public String getDiagnostic() {
		return diagnostic;
	}

	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}
	
	/**
	 * 检验项目及套餐序号
	 */
	@Column(length = 20)
	public String getYlxh() {
		return ylxh;
	}

	public void setYlxh(String ylxh) {
		this.ylxh = ylxh;
	}
	
	/**
	 * 打印标识
	 */
	@Column(name = "isprint", length = 1)
	public String getPrintFlag() {
		return printFlag;
	}

	public void setPrintFlag(String printFlag) {
		this.printFlag = printFlag;
	}
	
	
	/**
	 * 检验目的
	 */
	@Column(name = "inspectionname")
	public String getInspectionName() {
		return inspectionName;
	}
	
	public void setInspectionName(String inspectionName) {
		this.inspectionName = inspectionName;
	}
	
	/**
	 * 样本类型
	 */
	@Column(name = "sampletype")
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
	public int getSampleStatus() {
		return sampleStatus;
	}
	
	public void setSampleStatus(int sampleStatus) {
		this.sampleStatus = sampleStatus;
	}
	
	/**
	 * 收费
	 */
	@Column(name = "fee")
	public String getFee() {
		return fee;
	}
	
	public void setFee(String fee) {
		this.fee = fee;
	}
	
	/**
	 * 收费状态
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
	
	@Column(name = "modifyflag")
	public int getModifyFlag() {
		return modifyFlag;
	}
	
	public void setModifyFlag(int modifyFlag) {
		this.modifyFlag = modifyFlag;
	}
	
	@Column(name = "writeback")
	public int getWriteback() {
		return writeback;
	}
	
	public void setWriteback(int writeback) {
		this.writeback = writeback;
	}
	
	@Column(name = "iswriteback")
	public int getIswriteback() {
		return iswriteback;
	}
	
	public void setIswriteback(int iswriteback) {
		this.iswriteback = iswriteback;
	}
	
	/**
	 * 
	 */
	@Column(name = "hasimages")
	public int getHasimages() {
		return hasimages;
	}

	public void setHasimages(int hasimages) {
		this.hasimages = hasimages;
	}

	@Column(name = "CYCLE")
	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	
	/**
	 * 审核状态
	 */
	@Column
	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * 审核标记
	 */
	@Column
	public int getAuditMark() {
		return auditMark;
	}

	public void setAuditMark(int auditMark) {
		this.auditMark = auditMark;
	}

	/**
	 * 需要标记的检验项目（检验结果不符合规则）
	 */
	@Column
	public String getMarkTests() {
		return markTests;
	}

	public void setMarkTests(String markTests) {
		this.markTests = markTests;
	}

	/**
	 * 检验结果 错误信息
	 */
	@Column
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	@Column
	public String getCheckerOpinion() {
		return checkerOpinion;
	}

	public void setCheckerOpinion(String checkerOpinion) {
		this.checkerOpinion = checkerOpinion;
	}

	/**
	 * 不符合的规则
	 */
	@Column
	public String getRuleIds() {
		return ruleIds;
	}

	public void setRuleIds(String ruleIds) {
		this.ruleIds = ruleIds;
	}
	
	@Transient
	public String getAuditMarkValue() {
		String value = "";
		switch (getAuditMark()) {
		case 0:
			break;
		case 1:
			value = "自动";
			break;
		case 2:
			value = "差值";
			break;
		case 3:
			value = "比值";
			break;
		case 4:
			value = "少做";
			break;
		case 5:
			value = "复检";
			break;
		case 6:
			value = "危急";
			break;
		case 7:
			value = "警戒1";
			break;
		case 8:
			value = "警戒2";
			break;
		case 9:
			value = "极值";
			break;
		case 10:
			value = "自动b";
			break;
		}
		return value;
	}

	@Transient
	public String getAuditStatusValue() {
		String value = "";
		switch (getAuditStatus()) {
		case -1:
			value = "无结果";
			break;
		case 0:
			value = "未审核";
			break;
		case 1:
			value = "已通过";
			break;
		case 2:
			value = "未通过";
			break;
		default:
			value = "未审核";
			break;
		}
		return value;
	}
	
	/**
	 * 该样本所做的结果集
	 */
	@OneToMany(targetEntity = Process.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE,mappedBy="sample")
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
	@JoinColumn(name="section_id",referencedColumnName="id")
	public Section getSection(){
		return section;
	}
	
	public void setSection(Section section){
		this.section = section; 
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
	@JoinColumn(name="patientblh",referencedColumnName="blh")
	public Patient getPatient(){
		return patient;
	}
	
	public void setPatient(Patient patient){
		this.patient = patient; 
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
