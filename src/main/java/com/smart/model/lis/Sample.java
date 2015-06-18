package com.smart.model.lis;

import java.util.Date;
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
public class Sample extends BaseObject{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;//主键，流水号
	
	private Patient patient; //病人
	private String patientid; // 病人 就诊号
	private String departBed; //病床号
	
	private Date createTime;// 医生开检验单的时间
	private String creater; //开检验单的医生
	private String createMode; //检验单类型
	private String sampleNo;//样本编号， 手动生成
	private Date samplingTime;//样本采样时间
	private String sampler; //采样人员
	private Date receiveTime; //接受样本时间
	private String receiver; //样本接受人员
	private int stayHospitalMode; //就诊方式（门诊、住院、急诊）
	private String section; //检验科室
	private String diagnostic; //诊断
	private String inspectionName; //检验项目及套餐名称
	private String sampleType; //样本类型 、来源（血液、粪便）
	private String sampleStatus; //样本所处的状态（申请、采集、测试。。。）
	private Set<TestResult> results = new HashSet<TestResult>(); //检验项目的结果集
	private int hasimages;
	private int iswriteback;
	
	private String checker; //样本审核者
	private String checkerOpinion; //审核意见
	private Date checkTime; //审核时间
	private String labDepartMent; //检验部门
	private String labDepartMentId; //检验仪器号
	private String printFlag; //是否打印
	private Date printTime; //打印时间
	private int auditStatus; //样本审核的状态
	private int auditMark; //审核标记
	private String markTests; //出现异常 需要标记的检验项目
	private String notes; // 自动审核的结果记录
	private String ruleIds; //规则库生成的为题规则集，用“，”隔开
	
	private CriticalRecord criticalRecord;
	private Hospital hostipal;

	
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
	 * 检验单开单时间
	 */
	@Column(name = "CREATETIME")
	public Date getRequesttime() {
		return createTime;
	}

	public void setRequesttime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 检验单开单者
	 */
	@Column(name = "CREATER", length = 50)
	public String getRequester() {
		return creater;
	}

	public void setRequester(String creater) {
		this.creater = creater;
	}
	
	/**
	 * 检验单类型
	 */
	@Column(name = "CREATEMODE", length = 10)
	public String getRequestMode() {
		return createMode;
	}

	public void setRequestMode(String createMode) {
		this.createMode = createMode;
	}

	/**
	 * 样本采样时间
	 */
	@Column(name = "SAMPLINGTIME")
	public Date getExecutetime() {
		return samplingTime;
	}

	public void setExecutetime(Date samplingTime) {
		this.samplingTime = samplingTime;
	}

	/**
	 * 采样者
	 */
	@Column(name = "SAMPLER", length = 20)
	public String getExecutor() {
		return sampler;
	}

	public void setExecutor(String sampler) {
		this.sampler = sampler;
	}

	/**
	 * 样本接收时间
	 */
	@Column(name = "RECEIVETIME")
	public Date getReceivetime() {
		return receiveTime;
	}

	public void setReceivetime(Date receivetime) {
		this.receiveTime = receivetime;
	}

	/**
	 * 接收人
	 */
	@Column(name = "RECEIVER", length = 20)
	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
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
		return patientid;
	}

	public void setPatientId(String patientid) {
		this.patientid = patientid;
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
	 * 检验名称
	 */
	@Column(name = "INSPECTIONNAME")
	public String getExaminaim() {
		return inspectionName;
	}

	public void setExaminaim(String inspectionName) {
		this.inspectionName = inspectionName;
	}
	
	/**
	 * 审核者
	 */
	@Column(name = "CHECKER", length = 20)
	public String getCheckOperator() {
		return checker;
	}

	public void setCheckOperator(String checker) {
		this.checker = checker;
	}

	/**
	 * 审核意见
	 */
	@Column(name = "CHECKEROPINION")
	public String getCheckerOpinion() {
		return checkerOpinion;
	}

	public void setCheckerOpinion(String checkerOpinion) {
		this.checkerOpinion = checkerOpinion;
	}

	/**
	 * 审核时间
	 */
	@Column(name = "CHECKTIME")
	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	
	/**
	 * 检验科室
	 */
	@Column(name = "LABDEPARTMENT", length = 20)
	public String getLabdepartMent() {
		return labDepartMent;
	}

	public void setLabdepartMent(String labdepartMent) {
		this.labDepartMent = labdepartMent;
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
	 * 打印时间
	 */
	@Column(name = "PRINTTIME")
	public Date getPrintTime() {
		return printTime;
	}

	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
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
	 * 结果状态
	 */
	@Column(name = "SAMPLESTATUS")
	public String getResultStatus() {
		return sampleStatus;
	}

	public void setResultStatus(String sampleStatus) {
		this.sampleStatus = sampleStatus;
	}
	
	/**
	 * 自动审核所推出的规则id
	 */
	@Column(name = "RULEIDS")
	public String getRuleIds() {
		return ruleIds;
	}

	public void setRuleIds(String ruleIds) {
		this.ruleIds = ruleIds;
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
	
	
	/**
	 * 审核标记
	 */
	@Column(name = "AUDITMARK", length = 10)
	public int getAuditMark() {
		return auditMark;
	}

	public void setAuditMark(int auditMark) {
		this.auditMark = auditMark;
	}

	/**
	 * 审核状态
	 */
	@Column(name = "AUDITSTATUS", length = 10)
	public int getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * 需要标记的检验项目id
	 */
	@Column(name = "MARKTESTS")
	public String getMarkTests() {
		return markTests == null ? "" : markTests;
	}

	public void setMarkTests(String markTests) {
		this.markTests = markTests;
	}
	
	/**
	 * 智能检验的审核结果
	 */
	@Column(name = "NOTES")
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	
	
	/**
	 * 样本是否包含上传的图片
	 */
	@Column(name = "HASIMAGES", length = 10)
	public int getHasimages() {
		return hasimages;
	}

	public void setHasimages(int hasimages) {
		this.hasimages = hasimages;
	}
	
	/**
	 * 样本是否写回
	 */
	@Column(name = "ISWRITEBACK")
	public int getIswriteback() {
		return iswriteback;
	}

	public void setIswriteback(int iswriteback) {
		this.iswriteback = iswriteback;
	}
	
	@ManyToOne(optional=false,cascade=CascadeType.MERGE)
	@JoinColumn(name="hospital_id",referencedColumnName="id",unique=true)
	public Hospital getHospital(){
		return hostipal;
	}
	public void setHospital(Hospital hospital){
		this.hostipal = hospital; 
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
