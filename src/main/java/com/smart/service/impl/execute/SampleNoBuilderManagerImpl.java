package com.smart.service.impl.execute;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.execute.SampleNoBuilderDao;
import com.smart.model.execute.SampleNoBuilder;
import com.smart.service.execute.SampleNoBuilderManager;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.ProcessManager;
import com.smart.service.lis.SampleManager;

@Service("sampleNoBuilderManager")
public class SampleNoBuilderManagerImpl extends GenericManagerImpl<SampleNoBuilder, Long> implements SampleNoBuilderManager{

	private SampleNoBuilderDao sampleNoBuilderDao;
	
	
	@Autowired
	public void setSampleNoBuilderDao(SampleNoBuilderDao sampleNoBuilderDao){
		this.dao = sampleNoBuilderDao;
		this.sampleNoBuilderDao = sampleNoBuilderDao;
	}
	
	public SampleNoBuilder getByLab(String lab){
		return sampleNoBuilderDao.getByLab(lab);
	}
	
	public SampleNoBuilder updateSampleNo(String lab,int type){
		return sampleNoBuilderDao.updateSampleNo(lab,type);
	}

	public List<SampleNoBuilder> getAllByOrder() {
		return sampleNoBuilderDao.getAllByOrder();
	}

	public void clearNo() {
		sampleNoBuilderDao.clearNo();
	}
}
