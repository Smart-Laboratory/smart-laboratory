package com.smart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.WorkCountDao;
import com.smart.model.pb.WorkCount;
import com.smart.service.WorkCountManager;

@Service("workCountManager")
public class WorkCountManagerImpl extends GenericManagerImpl<WorkCount, Long> implements WorkCountManager {

	private WorkCountDao workCountDao;
	
	@Autowired
	public WorkCountManagerImpl(WorkCountDao workCountDao){
		super(workCountDao);
		this.workCountDao = workCountDao;
	}

	public List<WorkCount> getBySection(String section) {
		List<WorkCount> workCounts = workCountDao.getBySection(section);
		return workCounts;
	}
	
	public List<WorkCount> getMonthBySection(String section, String month){
		return workCountDao.getMonthBySection(section, month);
	}
	
	public WorkCount getPersonByMonth(String name,String month,String section){
		return workCountDao.getPersonByMonth(name, month, section);
	}
	
	public double getYearCount(String year,String name ){
		return workCountDao.getYearCount(year,name);
	}
	
	public List<WorkCount> getByWorker(String worker){
		return workCountDao.getByWorker(worker);
	}
}
