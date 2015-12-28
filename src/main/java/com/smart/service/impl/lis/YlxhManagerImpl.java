package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.YlxhDao;
import com.smart.model.lis.Ylxh;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.YlxhManager;

@Service("ylxhManager")
public class YlxhManagerImpl extends GenericManagerImpl<Ylxh, Long> implements YlxhManager {

	private YlxhDao ylxhDao;

	@Autowired
	public void setYlxhDao(YlxhDao ylxhDao) {
		this.dao = ylxhDao;
		this.ylxhDao = ylxhDao;
	}

	public List<Ylxh> getYlxh() {
		return ylxhDao.getYlxh();
	}
	
	public List<Ylxh> getTest(String lab){
		return ylxhDao.getTest(lab);
	}
	
	public List<Ylxh> getSearchData(String text){
		return ylxhDao.getSearchData(text);
	}

	public String getRelativeTest(String ylxh) {
		return ylxhDao.getRelativeTest(ylxh);
	}
	
}
