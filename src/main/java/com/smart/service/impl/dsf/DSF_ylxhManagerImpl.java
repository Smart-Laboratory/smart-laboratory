package com.smart.service.impl.dsf;

import com.smart.dao.dsf.DSF_YlxhDao;
import com.smart.model.dsf.DSF_ylxh;
import com.smart.service.dsf.DSF_ylxhManager;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.lis.YlxhManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dsf_ylxhManager")
public class DSF_ylxhManagerImpl extends GenericManagerImpl<DSF_ylxh, Long> implements DSF_ylxhManager {

	private DSF_YlxhDao dsf_ylxhDao;

	@Autowired
	public void setDsf_ylxhDao(DSF_YlxhDao dsf_ylxhDao) {
		this.dao = dsf_ylxhDao;
		this.dsf_ylxhDao = dsf_ylxhDao;
	}

	public List<DSF_ylxh> getYlxh() {
		return dsf_ylxhDao.getYlxh();
	}
	
	public List<DSF_ylxh> getTest(String lab){
		return dsf_ylxhDao.getTest(lab);
	}
	
	public List<DSF_ylxh> getLabofYlmcBylike(String lab ,String ylmc){
		return dsf_ylxhDao.getLabofYlmcBylike(lab,ylmc);
	}
	
	public List<DSF_ylxh> getSearchData(String text){
		return dsf_ylxhDao.getSearchData(text);
	}

	public String getRelativeTest(String ylxh) {
		return dsf_ylxhDao.getRelativeTest(ylxh);
	}

	public int getSizeByLab(String query, String lab) {
		return dsf_ylxhDao.getSizeByLab(query, lab);
	}

	public List<DSF_ylxh> getYlxhByLab(String query, String lab, int start, int end, String sidx, String sord,String customerid) {
		return dsf_ylxhDao.getYlxhByLab(query, lab, start, end, sidx, sord,customerid);
	}
	
}
