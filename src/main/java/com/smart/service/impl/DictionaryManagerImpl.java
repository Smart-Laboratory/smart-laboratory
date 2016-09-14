package com.smart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.model.Dictionary;
import com.smart.dao.DictionaryDao;
import com.smart.service.DictionaryManager;

@Service("dictionaryManager")
public class DictionaryManagerImpl extends GenericManagerImpl<Dictionary, Long> implements DictionaryManager{

	private DictionaryDao dictionaryDao;
	
	@Autowired
	public void setDictionaryDao(DictionaryDao dictionary){
		this.dao = dictionary;
		this.dictionaryDao = dictionary;
	}
	public int getDictionaryCount(String query, String type) {
		return dictionaryDao.getDictionaryCount(query,type);
	}
	public List<Dictionary> getDictionaryList(String query,String type, int start, int end, String sidx, String sord) {
		return dictionaryDao.getDictionaryList(query,type,start,end,sidx,sord);
	}
	public List<Dictionary> getPatientInfo(String name) {
		return dictionaryDao.getPatientInfo(name);
	}

	/**
	 * 获取标本类型
	 * @return
     */
	public List<Dictionary> getSampleType() {
		return dictionaryDao.getSampleType();
	}

	/**
	 * 获取仪器类型
	 * @return
     */
	public List<Dictionary> getDeviceType(){
		return dictionaryDao.getDeviceType();
	}

	/**
	 * 搜索标本类型
	 * @return
	 */
	public List<Dictionary> searchSampleType(String name) {
		return dictionaryDao.searchSampleType(name);
	}
}
