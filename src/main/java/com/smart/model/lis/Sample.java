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

	private String barcode;	//样本条码，客户号+主键
	private String patientId; // 病人 就诊号
	private String patientname;
	private Date birthday;
	private String sex;
	private String age;
	private String departBed; //病床号
	private String sampleNo;//样本编号， 手动生成
	private Integer stayHospitalMode=0; //就诊方式（门诊、住院、急诊）
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
	private Integer modifyFlag=0;//修改标识
	private Integer writeback=0;//写回标识
	private Integer iswriteback=0;//写回标识
	private Integer hasimages=0;//是否包含图片
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
	private int symstatus=0;
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
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
	@Column(length = 50)
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
	
	@Column(name = "PATIENTNAME")
	public String getPatientname() {
		return patientname;
	}

	public void setPatientname(String patientname) {
		this.patientname = patientname;
	}

	@Column(name = "BIRTHDAY")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "SEX")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Column(name = "AGEUNIT")
	public String getAgeunit() {
		return ageunit;
	}

	public void setAgeunit(String ageunit) {
		this.ageunit = ageunit;
	}

	@Column(name = "AGE")
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
	
	/**
	 * 审核意见
	 */
	@Column
	public String getCheckerOpinion() {
		return checkerOpinion;
	}

	public void setCheckerOpinion(String checkerOpinion) {
		this.checkerOpinion = checkerOpinion;
	}
	
	/**
	 * 通过原因
	 */
	@Column
	public String getPassReason() {
		return passReason;
	}

	public void setPassReason(String passReason) {
		this.passReason = passReason;
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
	
	@Column(name = "section_id")
	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}
	
	@Column(name = "patientblh")
	public String getPatientblh() {
		return patientblh;
	}

	public void setPatientblh(String patientblh) {
		this.patientblh = patientblh;
	}
	
	@Column(name = "charttest")
	public String getCharttest() {
		return charttest;
	}

	public void setCharttest(String charttest) {
		this.charttest = charttest;
	}
	
	@Column(name="symstatus")
	public int getSymstatus() {
		return symstatus;
	}

	public void setSymstatus(int symstatus) {
		this.symstatus = symstatus;
	}

	@Column
	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
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

	@Transient
	public String getSampleStatusValue() {
		String value = "";
		switch (getSampleStatus()) {
			case 0:
				value = "已开单";
				break;
			case 1:
				value = "条码打印";
				break;
			case 2:
				value = "已采样";
				break;
			case 3:
				value = "已送达";
				break;
			case 4:
				value = "已接收";
				break;
			case 5:
				value = "已审核";
				break;
			case 6:
				value = "已打印";
				break;
		}
		return value;
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
