package com.smart.dao;

import java.util.List;

import com.smart.model.pb.WorkCount;

public interface WorkCountDao extends GenericDao<WorkCount, Long> {

	List<WorkCount> getBySection(String section);
	
	WorkCount getPersonByMonth(String name,String month,String section);
	
	double getYearCount(String year,String name);
}
