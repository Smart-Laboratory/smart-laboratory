package com.smart.dao.lis;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Sample;
import com.smart.model.util.NeedWriteCount;

public interface SampleDao extends GenericDao<Sample, Long> {

	@Transactional
	List<Sample> getSampleList(String date, String lab, String code, int mark, int status);

	@Transactional
	List<Sample> getListBySampleNo(String sampleno);

	@Transactional
	List<Sample> getNeedAudit(String day);

	List<Sample> saveAll(List<Sample> updateSample);
	
	@Transactional
	List<Sample> getHistorySample(String patientId, String blh, String lab);

	@Transactional
	List<Sample> getDiffCheck(String patientid, String blh, String sampleno, String lab);
	
	@Transactional
	Sample getBySampleNo(String sampleNo);
	
	@Transactional
	List<Integer> getAuditInfo(String date, String department, String code, String user);
	
	@Transactional
	List<Sample> getSampleByPatientName(String fromDate, String toDate, String patientName);
	
	@Transactional
	List<Sample> getSampleBySearchType(String fromDate, String toDate, String searchType, String text);

	@Transactional
	List<Sample> getSampleList(String text, String lab, int mark, int status, String code, int start, int end);

	@Transactional
	int getSampleCount(String text, String lab, int mark, int status, String code);

	@Transactional
	List<NeedWriteCount> getAllWriteBack(String date);

	@Transactional
	List<Sample> getByIds(String ids);
	
	@Transactional
	List<Sample> getBysampleNos(String sampleNos);

	@Transactional
	List<Sample> getSampleByCode(String code);
	
	@Transactional
	boolean existSampleNo(String sampleno);
	
	@Transactional
	List<Sample> getByPatientId(String patientId,String lab);

	@Transactional
	String getReceiveSampleno(String name, String lab, String today);

	@Transactional
	List<Sample> getReceiveList(String text, String lab);
	
	@Transactional
	List<Sample> getOutList(String sender,Date sendtime);

	@Transactional
	Long getSampleId();

    void removeAll(List<Sample> list);
}
