package com.smart.service.impl.reagent;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.reagent.InDao;
import com.smart.model.reagent.In;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.reagent.InManager;

@Service("inManager")
public class InManagerImpl extends GenericManagerImpl<In, Long> implements InManager {
	
	private InDao inDao;
	
    @Autowired
    public InManagerImpl(InDao inDao) {
        this.dao = inDao;
        this.inDao = inDao;
    }

	public void saveAll(List<In> needSaveIn) {
		inDao.saveAll(needSaveIn);
	}

	public List<In> getByInDate(String indate) {
		return inDao.getByInDate(indate);
	}

	public List<In> getByLab(String lab) {
		return inDao.getByLab(lab);
	}
}
