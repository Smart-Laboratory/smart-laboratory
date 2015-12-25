package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.Sample;
import com.smart.service.GenericManager;

public interface SampleManager extends GenericManager<Sample, Long> {

	List<Sample> getSampleList(String date, String lab, String code, int mark, int status);

	List<Sample> getListBySampleNo(String sampleno);

	List<Sample> getNeedAudit(String format);

	void saveAll(List<Sample> updateSample);

	List<Sample> getHistorySample(String patientId, String blh);

	List<Sample> getDiffCheck(String patientId, String blh, String sampleNo);

	Sample getBySampleNo(String sampleNo);
	
	/**
	 * 	 样本数统计
	 * @param date	日期，样本号前缀，如20130813
	 * @param department	部门
	 * @param string 
	 * @return	返回今日未审核数，今日未通过数、未处理的危急样本数，需写回数
	 */
	List<Integer> getAuditInfo(String date, String department, String code, String user);
	
	/**
	 *   获取某一时间段中某患者的样本
	 * @param fromDate	起始日期 如：2013-08-13
	 * @param toDate	结束日期
	 * @param patientName	患者姓名
	 * @return
	 */
	List<Sample> getSampleByPatientName(String fromDate, String toDate, String patientName);

	List<Sample> getSampleList(String text, String lab, int mark, int status, String code, int start, int end);

	int getSampleCount(String text, String lab, int mark, int status, String labCode);

}
