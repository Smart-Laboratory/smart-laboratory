package com.smart.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.model.Dictionary;

public interface DictionaryDao extends GenericDao<Dictionary, Long> {

	List<Dictionary> getPatientInfo(String name);

	List<Dictionary> getSampleType();

	List<Dictionary> getDeviceType();

	/**
	 * 获取记录数
	 * @param type 对照类别
	 * @return
	 */
	int getDictionaryCount(String query,String type);

	/**
	 * 查询字典列表
	 * @param query
	 * @param type 对照类别
	 * @param start
	 * @param end
	 * @return
	 */
	List<Dictionary> getDictionaryList(String query,String type, int start,int end,String sidx,String sord);

    List<Dictionary> searchSampleType(String name);
}
