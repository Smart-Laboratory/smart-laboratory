package com.smart.model.execute;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="l_laborder")
public class LabOrder {

	private Long laborder; //医嘱号，自增长
	
	private Long laborderorg; //yjsb
	private String sampleno;
	private Integer stayhospitalmode=0;
	private Date requestetime;
	private String requester;
	private Date executetime;
	private String executor;
	
	private Date birthday;
	private String patientid;
	private String patientname;
	private Integer sex=0;
	
	private String diagnostic;
	private Integer currentdepartment=0;
	private String sampletype;
	private Double price;
	private Integer feestatus=0;
	private String examitem;
	private String ylxh;
	private Integer labdepartment=0;
	private String computername;
	private Integer printflag=0;
	private Integer receiveflag=0;
	private String qbgsj;
	private String qbgdt;
	private Integer requestmode=0;
	
	
	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LABORDER")
//	@SequenceGenerator(name = "SEQ_LABORDER", sequenceName = "laborder_sequence", allocationSize = 1)
	public Long getLaborder() {
		return laborder;
	}
	public void setLaborder(Long laborder) {
		this.laborder = laborder;
	}
	
	@Column
	public Long getLaborderorg() {
		return laborderorg;
	}
	public void setLaborderorg(Long laborderorg) {
		this.laborderorg = laborderorg;
	}
	
	@Column
	public String getSampleno() {
		return sampleno;
	}
	public void setSampleno(String sampleno) {
		this.sampleno = sampleno;
	}
	
	@Column
	public Integer getStayhospitalmode() {
		return stayhospitalmode;
	}
	public void setStayhospitalmode(Integer stayhospitalmode) {
		this.stayhospitalmode = stayhospitalmode;
	}
	
	@Column
	public Date getRequestetime() {
		return requestetime;
	}
	public void setRequestetime(Date requestetime) {
		this.requestetime = requestetime;
	}
	
	@Column
	public String getRequester() {
		return requester;
	}
	public void setRequester(String requester) {
		this.requester = requester;
	}
	
	@Column
	public Date getExecutetime() {
		return executetime;
	}
	public void setExecutetime(Date executetime) {
		this.executetime = executetime;
	}
	
	@Column
	public String getExecutor() {
		return executor;
	}
	public void setExecutor(String executor) {
		this.executor = executor;
	}
	
	@Column
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Column
	public String getPatientid() {
		return patientid;
	}
	public void setPatientid(String patientid) {
		this.patientid = patientid;
	}
	
	@Column
	public String getPatientname() {
		return patientname;
	}
	public void setPatientname(String patientname) {
		this.patientname = patientname;
	}
	
	@Column
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
	@Column
	public String getDiagnostic() {
		return diagnostic;
	}
	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}
	
	@Column
	public Integer getCurrentdepartment() {
		return currentdepartment;
	}
	public void setCurrentdepartment(Integer currentdepartment) {
		this.currentdepartment = currentdepartment;
	}
	
	@Column(name="specimen")
	public String getSampletype() {
		return sampletype;
	}
	public void setSampletype(String sampletype) {
		this.sampletype = sampletype;
	}
	
	@Column
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Column
	public Integer getFeestatus() {
		return feestatus;
	}
	public void setFeestatus(Integer feestatus) {
		this.feestatus = feestatus;
	}
	
	@Column
	public String getExamitem() {
		return examitem;
	}
	public void setExamitem(String examitem) {
		this.examitem = examitem;
	}
	
	@Column(name="testdescribe")
	public String getYlxh() {
		return ylxh;
	}
	public void setYlxh(String ylxh) {
		this.ylxh = ylxh;
	}
	
	@Column
	public Integer getLabdepartment() {
		return labdepartment;
	}
	public void setLabdepartment(Integer labdepartment) {
		this.labdepartment = labdepartment;
	}
	
	@Column
	public String getComputername() {
		return computername;
	}
	public void setComputername(String computername) {
		this.computername = computername;
	}
	
	@Column
	public Integer getPrintflag() {
		return printflag;
	}
	public void setPrintflag(Integer printflag) {
		this.printflag = printflag;
	}
	
	@Column
	public Integer getReceiveflag() {
		return receiveflag;
	}
	public void setReceiveflag(Integer receiveflag) {
		this.receiveflag = receiveflag;
	}
	
	@Column
	public String getQbgsj() {
		return qbgsj;
	}
	public void setQbgsj(String qbgsj) {
		this.qbgsj = qbgsj;
	}
	
	@Column
	public String getQbgdt() {
		return qbgdt;
	}
	public void setQbgdt(String qbgdt) {
		this.qbgdt = qbgdt;
	}
	
	@Column
	public Integer getRequestmode() {
		return requestmode;
	}
	public void setRequestmode(Integer requestmode) {
		this.requestmode = requestmode;
	}
	
	
	
}
