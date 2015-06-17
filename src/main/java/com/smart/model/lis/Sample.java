package com.smart.model.lis;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.OneToOne;
import javax.persistence.CascadeType;

import com.smart.model.lis.TestResult;

//样本信息表
public class Sample {
	
	private String id;//主键，流水号
	private String blh; // 病人blh
	private String departBed; //病床号
	
	private String sampleNo;//样本编号， 手动生成
	private Date samplingTime;//样本采样时间
	private String sampler; //采样人员
	private Date receiveTime; //接受样本时间
	private String receivetor; //样本接受人员
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
	private String checkTime; //审核时间
	private String labDepartment; //检验部门
	private String labDepartmentId; //检验仪器号
	private String printFlag; //是否打印
	private String printTimeString; //打印时间
	private String auditStatus; //样本审核的状态
	private String auditMarkString; //审核标记
	private String markTests; //出现异常 需要标记的检验项目
	private String notes; // 自动审核的结果记录
	
	private CriticalRecord criticalRecord;
	
	
	
	
	@OneToOne(optional=true, cascade=CascadeType.ALL, mappedBy = "sample")
	public CriticalRecord getCriticalRecord(){
		return criticalRecord;
	}
	
	
	

}
