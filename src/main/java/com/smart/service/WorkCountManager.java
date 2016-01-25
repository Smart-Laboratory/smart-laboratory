package com.smart.service;

import java.util.List;

import com.smart.model.pb.WorkCount;

public interface WorkCountManager extends GenericManager<WorkCount, Long> {

	List<WorkCount> getBySection(String section);
	
	WorkCount getPersonByMonth(String name,String month,String section);
	
	double getYearCount(String year,String name);
}
