package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Sample;
import com.smart.model.lis.TestResult;
import com.smart.model.util.NeedWriteCount;

public interface SampleDao extends GenericDao<Sample, Long> {

	List<Sample> getSampleList(String date, String lab, String code, int mark, int status);

	List<Sample> getListBySampleNo(String sampleno);

	List<Sample> getNeedAudit(String day);

	void saveAll(List<Sample> updateSample);
	
	List<Sample> getHistorySample(String patientId, String blh, String lab);

	List<Sample> getDiffCheck(String patientid, String blh, String sampleno, String lab);
	
	Sample getBySampleNo(String sampleNo);
	
	List<Integer> getAuditInfo(String date, String department, String code, String user);
	
	List<Sample> getSampleByPatientName(String fromDate, String toDate, String patientName);

	List<Sample> getSampleList(String text, String lab, int mark, int status, String code, int start, int end);

	int getSampleCount(String text, String lab, int mark, int status, String code);

	List<NeedWriteCount> getAllWriteBack(String date);
<<<<<<< HEAD
	
	
=======

	List<Sample> getByIds(String ids);
>>>>>>> origin/master
}
