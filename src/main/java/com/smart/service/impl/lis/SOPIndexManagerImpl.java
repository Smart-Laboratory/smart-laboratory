package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.service.lis.SOPIndexManager;
import com.smart.model.lis.SOPIndex;
import com.smart.dao.lis.SOPIndexDao;
import com.smart.service.impl.GenericManagerImpl;

@Service("SOPIndexManager")
public class SOPIndexManagerImpl extends GenericManagerImpl<SOPIndex, Long> implements SOPIndexManager {


	private SOPIndexDao sopIndexDao;

	@Autowired
	public void setSOPIndexDao(SOPIndexDao sopIndexDao) {
		this.dao = sopIndexDao;
		this.sopIndexDao = sopIndexDao;
	}

	public List<SOPIndex> getByLab(String lab) {
		return sopIndexDao.getByLab(lab);
	}

	public List<SOPIndex> getByType(String lab, int type) {
		return sopIndexDao.getByType(lab, type);
	}
}
