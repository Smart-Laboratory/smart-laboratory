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

	public List<Dictionary> getDictionaryList(Dictionary dictionary, int start, int end, String sidx, String sord) {
		return dictionaryDao.getDictionaryList(dictionary,start,end,sidx,sord);
	}

	public List<Dictionary> getPatientInfo(String name) {
		return dictionaryDao.getPatientInfo(name);
	}

	public List<Dictionary> getSampleType() {
		return dictionaryDao.getSampleType();
	}
}
