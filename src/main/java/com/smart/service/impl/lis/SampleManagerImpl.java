package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.SampleDao;
import com.smart.model.lis.Sample;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.SampleManager;


@Service("sampleManager")
public class SampleManagerImpl extends GenericManagerImpl<Sample, Long> implements SampleManager {
	
	private SampleDao sampleDao;
	
	@Autowired
    public void setSampleDao(SampleDao sampleDao) {
    	this.dao = sampleDao;
		this.sampleDao = sampleDao;
	}

	public List<Sample> getSampleList(String date, String lab, String code, int mark, int status) {
		return sampleDao.getSampleList(date, lab, code, mark, status);
	}

	public List<Sample> getListBySampleNo(String sampleno) {
		return sampleDao.getListBySampleNo(sampleno);
	}

	public List<Sample> getNeedAudit(String day) {
		return sampleDao.getNeedAudit(day);
	}

	public void saveAll(List<Sample> updateSample) {
		sampleDao.saveAll(updateSample);
	}

	public List<Sample> getHistorySample(String patientId, String blh) {
		return sampleDao.getHistorySample(patientId, blh);
	}

	public List<Sample> getDiffCheck(String patientid, String blh, String sampleno) {
		return sampleDao.getDiffCheck(patientid, blh, sampleno);
	}
	
	public Sample getBySampleNo(String sampleNo){
		return sampleDao.getBySampleNo(sampleNo);
	}

	public List<Integer> getAuditInfo(String date, String department, String user) {
		return sampleDao.getAuditInfo(date, department, user);
	}
	
	public List<Sample> getSampleByPatientName(String fromDate, String toDate, String patientName){
		return sampleDao.getSampleByPatientName(fromDate,toDate,patientName);
	}

	public List<Sample> getSampleList(String text, String lab, int mark, int status, String code, int start, int end) {
		return sampleDao.getSampleList(text, lab, mark, status, code, start, end);
	}

	public int getSampleCount(String text, String lab, int mark, int status, String code) {
		return sampleDao.getSampleCount(text, lab, mark, status, code);
	}
}
