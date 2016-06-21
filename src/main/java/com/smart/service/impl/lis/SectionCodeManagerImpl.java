package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.SectionCodeDao;
import com.smart.model.lis.SectionCode;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.SectionCodeManager;

@Service("sectionCodeManager")
public class SectionCodeManagerImpl extends GenericManagerImpl<SectionCode, Long> implements SectionCodeManager {

	private SectionCodeDao sectionCodeDao;

	@Autowired
	public void setSectionCodeDao(SectionCodeDao sectionCodeDao) {
		this.dao = sectionCodeDao;
		this.sectionCodeDao = sectionCodeDao;
	}

	public List<SectionCode> getCode(String codeId, int start, int end) {
		return sectionCodeDao.getCode(codeId, start, end);
	}

	public List<SectionCode> searchCode(String name) {
		return sectionCodeDao.searchCode(name);
	}
	
}
