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
}
