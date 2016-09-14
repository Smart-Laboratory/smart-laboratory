package com.smart.service;

import java.util.List;

import com.smart.model.Dictionary;
import com.smart.model.DictionaryType;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional
	List<Dictionary> getDictionaryList(String query,String type,int start,int end,String sidx,String sord);

	@Transactional
	List<Dictionary> getPatientInfo(String name);

	@Transactional
	List<Dictionary> getSampleType();

	@Transactional
	List<Dictionary> getDeviceType();

	@Transactional
	int getDictionaryCount(String query, String type);

	@Transactional
    List<Dictionary> searchSampleType(String name);
}
