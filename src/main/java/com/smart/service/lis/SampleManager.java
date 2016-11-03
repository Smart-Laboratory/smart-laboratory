package com.smart.service.lis;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.smart.model.lis.Sample;
import com.smart.model.util.NeedWriteCount;
import com.smart.service.GenericManager;

public interface SampleManager extends GenericManager<Sample, Long> {

	List<Sample> getSampleList(String date, String lab, String code, int mark, int status);

	List<Sample> getListBySampleNo(String sampleno);

	List<Sample> getNeedAudit(String format);

	List<Sample> saveAll(List<Sample> updateSample);

	List<Sample> getHistorySample(String patientId, String blh, String lab);

	List<Sample> getHistorySample(String patientId, String blh, String lab,int sampleStatus);

	List<Sample> getDiffCheck(String patientId, String blh, String sampleNo, String lab);

	Sample getBySampleNo(String sampleNo);

	Sample getSampleByBarcode(String barcode);
	
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
	
	/**
	 *   获取某一时间段中某字段的样本
	 * @param fromDate	起始日期 如：2013-08-13
	 * @param toDate	结束日期
	 * @param searchType  查询类型
	 * @param text   查询条件
	 * @return
	 */
	List<Sample> getSampleBySearchType(String fromDate, String toDate, String searchType, String text);

	List<Sample> getSampleList(String text, String lab, int mark, int status, String code, int start, int end);

	int getSampleCount(String text, String lab, int mark, int status, String labCode);

	List<NeedWriteCount> getAllWriteBack(String date);

	List<Sample> getByIds(String substring);
	
	List<Sample> getBysampleNos(String sampleNos);

	List<Sample> getSampleByCode(String string);
	
	boolean existSampleNo(String sampleno);

	/*
	 * 抽血是根据patientid获取历史检验项目 
	 * lab可以为空
	 */
	List<Sample> getByPatientId(String patientId,String lab);

	/**
	 *   获取d当天某一用户最后接收的样本号
	 * @param lab	用户所在部门
	 * @param today 用户当天时间
	 * @return
	 */
	String getReceiveSampleno(String lab, String today);

	List<Object[]> getReceiveList(String text, String lab);

	List<Object[]> getReceiveList(String sampleNo, String sectionId,String sampleStatus,String fromDate,String toDate);

	int findCountByCriteria(Sample sample,String from,String to);

	List<Sample> getSampleListByCriteria(Sample sample,String from,String to,int start, int end, String sidx, String sord);

	List<Sample> getOutList(String sender,Date sendtime);

	Long getSampleId();

    void removeAll(List<Sample> needSaveSample);

	void updateChkoper2(String text, String chkoper2);

	String generateSampleNo(String segment,int seriano) throws SQLException;
}
