package com.smart.service;

import java.util.List;

import com.smart.model.Dictionary;
import com.smart.model.DictionaryType;

public interface DictionaryManager  extends GenericManager<Dictionary, Long>{
	/**
	 * 分页查询字典信息
	 * @param query
	 * @param type		对照类别
	 * @param start
	 * @param end
	 * @param sidx
     * @param sord
     * @return
     */
	List<Dictionary> getDictionaryList(String query,String type,int start,int end,String sidx,String sord);
	List<Dictionary> getPatientInfo(String name);
	List<Dictionary> getSampleType();
	List<Dictionary> getDeviceType();
	int getDictionaryCount(String query, String type);
}
