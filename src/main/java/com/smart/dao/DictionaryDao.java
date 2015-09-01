package com.smart.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.Dictionary;

public interface DictionaryDao extends GenericDao<Dictionary, Long> {
	
//	List<Dictionary> saveAll(List<Dictionary> dic);
	
	@Transactional
	List<Dictionary> getByType(int type);
	
	@Transactional
	List<Dictionary> getPatientInfo(String name);

	@Transactional
	List<Dictionary> getSampleType();

}
