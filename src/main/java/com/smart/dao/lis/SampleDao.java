package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Sample;

public interface SampleDao extends GenericDao<Sample, Long> {

	@Transactional
	List<Sample> getSampleList(String date, String lab, String code, int mark, int status);

	@Transactional
	List<Sample> getListBySampleNo(String sampleno);

	@Transactional
	List<Sample> getNeedAudit(String day);

	@Transactional
	void saveAll(List<Sample> updateSample);
	
	@Transactional
	List<Sample> getHistorySample(String patientId, String blh);

	@Transactional
	List<Sample> getDiffCheck(String patientid, String blh, String sampleno);
	
	@Transactional
	Sample getBySampleNo(String sampleNo);
	
	@Transactional
	List<Integer> getAuditInfo(String date, String department, String user);
	
	@Transactional
	List<Sample> getSampleByPatientName(String fromDate, String toDate, String patientName);

	@Transactional
	List<Sample> getSampleList(String text, String lab, int mark, int status, String code, int start, int end);

	@Transactional
	int getSampleCount(String text, String lab, int mark, int status, String code);
}
