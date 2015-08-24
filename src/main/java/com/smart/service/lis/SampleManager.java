package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.Sample;
import com.smart.service.GenericManager;

public interface SampleManager extends GenericManager<Sample, Long> {

	List<Sample> getSampleList(String date, String lab, String code, int mark, int status);

	List<Sample> getListBySampleNo(String sampleno);

	List<Sample> getNeedAudit(String format);

	List<Sample> getDiffCheck(Sample info);

	void saveAll(List<Sample> updateSample);

	List<Sample> getHistorySample(String patientId, String blh);

}
