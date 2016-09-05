package com.smart.service.impl.lis;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.SampleDao;
import com.smart.model.lis.Sample;
import com.smart.model.util.NeedWriteCount;
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

	public List<Sample> saveAll(List<Sample> updateSample) {
		return sampleDao.saveAll(updateSample);
	}

	public List<Sample> getHistorySample(String patientId, String blh, String lab) {
		return sampleDao.getHistorySample(patientId, blh, lab);
	}

	public List<Sample> getDiffCheck(String patientid, String blh, String sampleno, String lab) {
		return sampleDao.getDiffCheck(patientid, blh, sampleno, lab);
	}
	
	public Sample getBySampleNo(String sampleNo){
		return sampleDao.getBySampleNo(sampleNo);
	}

	public List<Integer> getAuditInfo(String date, String department, String code, String user) {
		return sampleDao.getAuditInfo(date, department, code, user);
	}
	
	public List<Sample> getSampleByPatientName(String fromDate, String toDate, String patientName){
		return sampleDao.getSampleByPatientName(fromDate,toDate,patientName);
	}
	
	public List<Sample> getSampleBySearchType(String fromDate, String toDate, String searchType, String text){
		return sampleDao.getSampleBySearchType(fromDate, toDate, searchType,text);
	}

	public List<Sample> getSampleList(String text, String lab, int mark, int status, String code, int start, int end) {
		return sampleDao.getSampleList(text, lab, mark, status, code, start, end);
	}

	public int getSampleCount(String text, String lab, int mark, int status, String code) {
		return sampleDao.getSampleCount(text, lab, mark, status, code);
	}

	public List<NeedWriteCount> getAllWriteBack(String date) {
		return sampleDao.getAllWriteBack(date);
	}

	public List<Sample> getByIds(String ids) {
		return sampleDao.getByIds(ids);
	}
	
	public List<Sample> getBysampleNos(String sampleNos){
		return sampleDao.getBysampleNos(sampleNos);
	}

	public List<Sample> getSampleByCode(String code) {
		return sampleDao.getSampleByCode(code);
	}
	
	public boolean existSampleNo(String sampleno){
		return sampleDao.existSampleNo(sampleno);
	}

	public List<Sample> getByPatientId(String patientId,String lab){
		return sampleDao.getByPatientId(patientId, lab);
	}

	public String getReceiveSampleno(String name, String lab, String today) {
		return sampleDao.getReceiveSampleno(name, lab, today);
	}

	public List<Sample> getReceiveList(String text, String lab) {
		return sampleDao.getReceiveList(text, lab);
	}
	
	public List<Sample> getOutList(String sender,Date sendtime){
		return sampleDao.getOutList(sender, sendtime);
	}

	public Long getSampleId() {
		return sampleDao.getSampleId();
	}

	public void removeAll(List<Sample> list) {
		sampleDao.removeAll(list);
	}
}
