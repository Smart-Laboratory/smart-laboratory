package com.smart.service;

import java.util.List;

import com.smart.Dictionary;

public interface DictionaryManager  extends GenericManager<Dictionary, Long>{
	/**
	 *  获取字典信息（性别，成年等）
	 * @param item
	 * @return
	 */
	List<Dictionary> getPatientInfo(String name);
	
	/**
	 *  样本来源（血液，尿液等）
	 * @param item
	 * @return
	 */
	List<Dictionary> getSampleType();
}
