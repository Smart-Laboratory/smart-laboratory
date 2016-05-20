package com.smart.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.model.Dictionary;

public interface DictionaryDao extends GenericDao<Dictionary, Long> {
	/**
	 * 获取字典分页数据
	 * @param dictionary
	 * @param start
	 * @param end
	 * @param sidx
	 * @param sord
     * @return
     */
	@Transactional
	List<Dictionary> getDictionaryList(Dictionary dictionary,int start,int end,String sidx,String sord);

	@Transactional
	List<Dictionary> getPatientInfo(String name);

	@Transactional
	List<Dictionary> getSampleType();

}
