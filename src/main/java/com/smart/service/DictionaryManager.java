package com.smart.service;

import java.util.List;

import com.smart.model.Dictionary;
import com.smart.model.DictionaryType;

public interface DictionaryManager  extends GenericManager<Dictionary, Long>{
	/**
	 * 分页查询
	 * @param dictionary
	 * @param start
	 * @param end
	 * @param sidx
	 * @param sord
     * @return
     */
	List<Dictionary> getDictionaryList(Dictionary dictionary,int start,int end,String sidx,String sord);
	List<Dictionary> getPatientInfo(String name);
	List<Dictionary> getSampleType();
}
