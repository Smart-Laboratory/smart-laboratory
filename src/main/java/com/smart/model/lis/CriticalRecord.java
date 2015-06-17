package com.smart.model.lis;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.CascadeType;



/**
 * 危急值记录,包括护士与医生的处理信息
 */
public class CriticalRecord {
	
	private String sampleNo; // 出现危机值的样本编号
	private String criticalValues; //危机值
	private int criticalDealFlag; // 危机值是否被处理的标记
	private String criticalDeal; // 危机值处理信息
	private Date criticalDealTime;
	private String criticalContent;	//危急内容
	private String criticalDealPerson;	//处理人员
	
	private Sample sample;
	private Patient patient;
	
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
	
	
	@OneToOne(optional=false, cascade=CascadeType.REFRESH)
	@JoinColumn(name="sample_no",referencedColumnName="sampleno",unique=true)
	public Sample getSample(){
		return sample;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
