package com.smart.dao;

import java.util.List;

import com.smart.Dictionary;

public interface DictionaryDao extends GenericDao<Dictionary, Long> {
	
//	List<Dictionary> saveAll(List<Dictionary> dic);
	
	List<Dictionary> getByType(int type);
	
	List<Dictionary> getPatientInfo(String name);

	List<Dictionary> getSampleType();

}
