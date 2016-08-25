package com.smart.model.lis;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smart.model.BaseObject;

/**
 * 检验单样本日志表
 */
@Entity
@Table(name = "l_sample_log")
public class SampleLog extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4815001118249399259L;

	private Long id;//主键，流水号
	
	private Long sampleId; 	//样本主键
	private String patientId; // 病人 就诊号
	private String patientname;
	private Date birthday;
	private String sex;
	private String age;
	private String departBed; //病床号
	private String sampleNo;//样本编号， 手动生成
	private int stayHospitalMode=0; //就诊方式（门诊、住院、急诊）
	private String hosSection; //申请科室
	private String diagnostic; //诊断
	private String inspectionName; //检验项目及套餐名称
	private String ylxh;//检验项目及套餐序号
	private String sampleType; //样本类型 、来源（血液、粪便）
	private Integer sampleStatus=0; //样本所处的状态（申请、采集、测试。。。）
	private String printFlag; //是否打印
	private String printTime;
	private String chkoper2;
	private int requestMode=0;
	private String fee;	//费用
	private String feestatus;	//收费状态
	private String part;	//采集部位
	private String description; //描述
	private String note; //性状
	private String count; //采集数量
	private int modifyFlag=0;//修改标识
	private int writeback=0;//写回标识
	private int iswriteback=0;//写回标识
	private int hasimages=0;//是否包含图片
	private int cycle=0;

	private int auditStatus=0; //样本审核的状态
	private int auditMark=0; //审核标记
	private String markTests; //出现异常 需要标记的检验项目
	private String notes; //自动审核的结果记录
	private String ruleIds; //规则库生成的为题规则集，用“，”隔开
	private String checkerOpinion;
	private String passReason;
	private String sectionId;
	private String patientblh;
	private String charttest;
	private String ageunit;
	private int symstatus;
	
	private String logger;
	private Date logtime;
	private String logip;
	private String logoperate;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_SAMPLE_LOG")
	@SequenceGenerator(name = "SEQ_SAMPLE_LOG", sequenceName = "sample_log_sequence", allocationSize=1)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "sample_id")
	public Long getSampleId() {
		return sampleId;
	}
	
	public void setSampleId(Long sampleId) {
		this.sampleId = sampleId;
	}
	
	@Column
	public String getPatientId() {
		return patientId;
	}
	
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	
	@Column
	public String getPatientname() {
		return patientname;
	}
	
	public void setPatientname(String patientname) {
		this.patientname = patientname;
	}
	
	@Column
	public Date getBirthday() {
		return birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Column
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Column
	public String getAge() {
		if (birthday != null) {
			Calendar now = Calendar.getInstance();
			Calendar previous = Calendar.getInstance();
			previous.setTime(birthday);
			setAge((now.get(Calendar.YEAR) - previous.get(Calendar.YEAR)+1) + "");
		}
		return age;
	}
	
	public void setAge(String age) {
		this.age = age;
	}
	
	@Column(name = "depart_bed", length = 10)
	public String getDepartBed() {
		return departBed;
	}
	
	public void setDepartBed(String departBed) {
		this.departBed = departBed;
	}
	
	@Column
	public String getSampleNo() {
		return sampleNo;
	}
	
	public void setSampleNo(String sampleNo) {
		this.sampleNo = sampleNo;
	}
	
	@Column
	public int getStayHospitalMode() {
		return stayHospitalMode;
	}
	
	public void setStayHospitalMode(Integer stayHospitalMode) {
		this.stayHospitalMode = stayHospitalMode;
	}
	
	@Column
	public String getHosSection() {
		return hosSection;
	}
	
	public void setHosSection(String hosSection) {
		this.hosSection = hosSection;
	}
	
	@Column
	public String getDiagnostic() {
		return diagnostic;
	}
	
	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}
	
	@Column
	public String getInspectionName() {
		return inspectionName;
	}
	
	public void setInspectionName(String inspectionName) {
		this.inspectionName = inspectionName;
	}
	
	@Column
	public String getYlxh() {
		return ylxh;
	}
	
	public void setYlxh(String ylxh) {
		this.ylxh = ylxh;
	}
	
	@Column
	public String getSampleType() {
		return sampleType;
	}
	
	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}
	
	@Column
	public Integer getSampleStatus() {
		return sampleStatus;
	}
	
	public void setSampleStatus(Integer sampleStatus) {
		this.sampleStatus = sampleStatus;
	}
	
	@Column(name = "isprint")
	public String getPrintFlag() {
		return printFlag;
	}
	
	public void setPrintFlag(String printFlag) {
		this.printFlag = printFlag;
	}
	
	@Column
	public String getPrintTime() {
		return printTime;
	}
	
	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}
	
	@Column
	public String getChkoper2() {
		return chkoper2;
	}
	
	public void setChkoper2(String chkoper2) {
		this.chkoper2 = chkoper2;
	}
	
	@Column
	public int getRequestMode() {
		return requestMode;
	}
	
	public void setRequestMode(int requestMode) {
		this.requestMode = requestMode;
	}
	
	@Column
	public String getFee() {
		return fee;
	}
	
	public void setFee(String fee) {
		this.fee = fee;
	}
	
	@Column
	public String getFeestatus() {
		return feestatus;
	}
	
	public void setFeestatus(String feestatus) {
		this.feestatus = feestatus;
	}
	
	@Column
	public String getPart() {
		return part;
	}
	
	public void setPart(String part) {
		this.part = part;
	}
	
	@Column
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	@Column
	public String getCount() {
		return count;
	}
	
	public void setCount(String count) {
		this.count = count;
	}
	
	@Column
	public Integer getModifyFlag() {
		return modifyFlag;
	}
	
	public void setModifyFlag(Integer modifyFlag) {
		this.modifyFlag = modifyFlag;
	}
	
	@Column
	public Integer getWriteback() {
		return writeback;
	}
	
	public void setWriteback(Integer writeback) {
		this.writeback = writeback;
	}
	
	@Column
	public Integer getIswriteback() {
		return iswriteback;
	}
	
	public void setIswriteback(Integer iswriteback) {
		this.iswriteback = iswriteback;
	}
	
	@Column
	public Integer getHasimages() {
		return hasimages;
	}
	
	public void setHasimages(Integer hasimages) {
		this.hasimages = hasimages;
	}
	
	@Column
	public int getCycle() {
		return cycle;
	}
	
	public void setCycle(int cycle) {
		this.cycle = cycle;
	}
	
	@Column
	public int getAuditStatus() {
		return auditStatus;
	}
	
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	@Column
	public int getAuditMark() {
		return auditMark;
	}
	
	public void setAuditMark(int auditMark) {
		this.auditMark = auditMark;
	}
	
	@Column
	public String getMarkTests() {
		return markTests;
	}
	
	public void setMarkTests(String markTests) {
		this.markTests = markTests;
	}
	
	@Column
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	@Column
	public String getRuleIds() {
		return ruleIds;
	}
	
	public void setRuleIds(String ruleIds) {
		this.ruleIds = ruleIds;
	}
	
	@Column
	public String getCheckerOpinion() {
		return checkerOpinion;
	}
	
	public void setCheckerOpinion(String checkerOpinion) {
		this.checkerOpinion = checkerOpinion;
	}
	
	@Column
	public String getPassReason() {
		return passReason;
	}
	
	public void setPassReason(String passReason) {
		this.passReason = passReason;
	}
	
	@Column(name = "section_id", length = 10)
	public String getSectionId() {
		return sectionId;
	}
	
	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	
	@Column
	public String getPatientblh() {
		return patientblh;
	}
	
	public void setPatientblh(String patientblh) {
		this.patientblh = patientblh;
	}
	
	@Column
	public String getCharttest() {
		return charttest;
	}
	
	public void setCharttest(String charttest) {
		this.charttest = charttest;
	}
	
	@Column
	public String getAgeunit() {
		return ageunit;
	}
	
	public void setAgeunit(String ageunit) {
		this.ageunit = ageunit;
	}
	
	@Column
	public String getLogger() {
		return logger;
	}

	public void setLogger(String logger) {
		this.logger = logger;
	}

	@Column
	public Date getLogtime() {
		return logtime;
	}

	public void setLogtime(Date logtime) {
		this.logtime = logtime;
	}

	@Column
	public String getLogip() {
		return logip;
	}

	public void setLogip(String logip) {
		this.logip = logip;
	}
	
	@Column
	public String getLogoperate() {
		return logoperate;
	}

	public void setLogoperate(String logoperate) {
		this.logoperate = logoperate;
	}
	
	@Column(name="symstatus")
	public int getSymstatus() {
		return symstatus;
	}

	public void setSymstatus(int symstatus) {
		this.symstatus = symstatus;
	}

	@Transient
	public Sample getSampleEntity() {
		Sample s = new Sample();
		s.setAge(this.age);
		s.setAgeunit(this.ageunit);
		s.setAuditMark(this.auditMark);
		s.setAuditStatus(this.auditStatus);
		s.setBirthday(this.birthday);
		s.setCharttest(this.charttest);
		s.setCheckerOpinion(this.checkerOpinion);
		s.setChkoper2(this.chkoper2);
		s.setCount(this.count);
		s.setCycle(this.cycle);
		s.setDepartBed(this.departBed);
		s.setDescription(this.description);
		s.setDiagnostic(this.diagnostic);
		s.setFee(this.fee);
		s.setFeestatus(this.feestatus);
		s.setHasimages(this.hasimages);
		s.setHosSection(this.hosSection);
		s.setId(this.sampleId);
		s.setInspectionName(this.inspectionName);
		s.setIswriteback(this.iswriteback);
		s.setMarkTests(this.markTests);
		s.setModifyFlag(this.modifyFlag);
		s.setNote(this.note);
		s.setNotes(this.notes);
		s.setPart(this.part);
		s.setPassReason(this.passReason);
		s.setPatientblh(this.patientblh);
		s.setPatientId(this.patientId);
		s.setPatientname(this.patientname);
		s.setPrintFlag(this.printFlag);
		s.setPrintTime(this.printTime);
		s.setRequestMode(this.requestMode);
		s.setRuleIds(this.ruleIds);
		s.setSampleNo(this.sampleNo);
		s.setSampleStatus(this.sampleStatus);
		s.setSampleType(this.sampleType);
		s.setSectionId(this.sectionId);
		s.setSex(this.sex);
		s.setStayHospitalMode(this.stayHospitalMode);
		s.setWriteback(this.writeback);
		s.setYlxh(this.ylxh);
		return s;
	}
	
	public void setSampleEntity(Sample s) {
		this.age = s.getAge();
		this.ageunit = s.getAgeunit();
		this.auditMark = s.getAuditMark();
		this.auditStatus = s.getAuditStatus();
		this.birthday = s.getBirthday();
		this.charttest = s.getCharttest();
		this.checkerOpinion = s.getCheckerOpinion();
		this.chkoper2 = s.getChkoper2();
		this.count = s.getCount();
		this.cycle = s.getCycle();		
		this.departBed = s.getDepartBed();
		this.description = s.getDescription();
		this.diagnostic = s.getDiagnostic();
		this.fee = s.getFee();
		this.feestatus = s.getFeestatus();
		this.hasimages = s.getHasimages();
		this.hosSection = s.getHosSection();
		this.sampleId = s.getId();
		this.inspectionName = s.getInspectionName();
		this.iswriteback = s.getIswriteback();
		this.markTests = s.getMarkTests();
		this.modifyFlag = s.getModifyFlag();
		this.note = s.getNote();
		this.notes = s.getNotes();
		this.part = s.getPart();
		this.passReason = s.getPassReason();
		this.patientblh = s.getPatientblh();
		this.patientId = s.getPatientId();
		this.patientname = s.getPatientname();
		this.printFlag = s.getPrintFlag();
		this.printTime = s.getPrintTime();
		this.requestMode = s.getRequestMode();
		this.ruleIds = s.getRuleIds();
		this.sampleNo = s.getSampleNo();
		this.sampleStatus = s.getSampleStatus();
		this.sampleType = s.getSampleType();
		this.sectionId = s.getSectionId();
		this.sex = s.getSex();
		this.stayHospitalMode = s.getStayHospitalMode();
		this.writeback = s.getWriteback();
		this.ylxh = s.getYlxh();
	}
	
	@Transient
	public String getStayHospitalModelValue(){
		String value = "";
		switch (getStayHospitalMode()) {
		case 1:
			value = "门诊";
			break;
		case 2:
			value = "病房";
			break;
		case 3:
			value = "急诊";
			break;
			
		default:
			value = "";
			break;
		}
		return value;
	}
	
	@Transient
	public String getSexValue() {
		if (sex.equals("1")) {
			return "男";
		} else if (sex.equals("2")) {
			return "女";
		}
		return "未知";
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
