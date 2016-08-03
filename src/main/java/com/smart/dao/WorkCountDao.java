package com.smart.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.model.pb.WorkCount;

public interface WorkCountDao extends GenericDao<WorkCount, Long> {

	@Transactional
	List<WorkCount> getBySection(String section);
	
	@Transactional
	WorkCount getPersonByMonth(String name,String month,String section);
	
	@Transactional
	double getYearCount(String year,String name);
	
	@Transactional
	List<WorkCount> getMonthBySection(String section, String month);

	@Transactional
	List<WorkCount> getByWorker(String worker);
}
