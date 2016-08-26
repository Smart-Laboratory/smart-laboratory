package com.smart.model.execute;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Transient;
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
	private Long laborder; //同医嘱号

	private String requestId;
	private String bed;
	private Long laborderorg; //yjsb
	private String sampleno;
	private Integer stayhospitalmode=0;
	private Date requesttime;
	private String requester;
	private Date executetime;
	private String executor;
	private Integer zxbz=0;//采样执行标志

	private Date birthday;
	private String patientid;
	private String patientname;
	private Integer sex=0;
	private String blh;
	private String age;

	private String diagnostic;
	private String hossection; //申请科室
	private String sampletype;
	private String price;				//单价
	private Integer feestatus=0;
	private String examitem;			//检验项目名称
	private String ylxh;				//检验项目ID
	private String labdepartment;  		//检验科室
	private String computername;
	private Integer printflag=0;
	private Integer receiveflag=0;
	private String qbgsj;
	private String qbgdt;
	private Integer requestmode=0; //急诊标识
	private Integer requestNum = 1; //申请数量
	private String selfexecute;
	private String toponymy; //采集部位
	private Integer cycle=0;		//生理周期
	private Integer count=0;		//采集数量

	//add by zcw 20160825
	private String hossectionName;	//科室名称(病区)

	@Id
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
	public Date getRequesttime() {
		return requesttime;
	}
	public void setRequesttime(Date requesttime) {
		this.requesttime = requesttime;
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
	public String getBlh() {
		return blh;
	}
	public void setBlh(String blh) {
		this.blh = blh;
	}
	
	@Column
	public String getDiagnostic() {
		return diagnostic;
	}
	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}

	@Column
	public String getHossection() {
		return hossection;
	}

	public void setHossection(String hossection) {
		this.hossection = hossection;
	}

	@Column(name="specimen")
	public String getSampletype() {
		return sampletype;
	}
	public void setSampletype(String sampletype) {
		this.sampletype = sampletype;
	}
	
	@Column
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
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
	
	@Column(name="labdepartment")
	public String getLabdepartment() {
		return labdepartment;
	}
	public void setLabdepartment(String labdepartment) {
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
	
	@Column
	public Integer getZxbz() {
		return zxbz;
	}
	public void setZxbz(Integer zxbz) {
		this.zxbz = zxbz;
	}
	
	@Column
	public String getSelfexecute() {
		return selfexecute;
	}
	public void setSelfexecute(String selfexecute) {
		this.selfexecute = selfexecute;
	}

	@Column
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Column
	public String getBed() {
		return bed;
	}

	public void setBed(String bed) {
		this.bed = bed;
	}

	@Column
	public String getToponymy() {
		return toponymy;
	}

	public void setToponymy(String toponymy) {
		this.toponymy = toponymy;
	}

	@Column
	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	@Column
	public Integer getRequestNum() {
		return requestNum;
	}

	public void setRequestNum(Integer requestNum) {
		this.requestNum = requestNum;
	}
	@Transient
	public String getHossectionName() {
		return hossectionName;
	}
	@Transient
	public void setHossectionName(String hossectionName) {
		this.hossectionName = hossectionName;
	}

	@Column
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Transient
	public String getAge() {
		if (birthday != null) {
			Calendar now = Calendar.getInstance();
			Calendar previous = Calendar.getInstance();
			previous.setTime(birthday);
			setAge((now.get(Calendar.YEAR) - previous.get(Calendar.YEAR)) + "");
		}
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
}
