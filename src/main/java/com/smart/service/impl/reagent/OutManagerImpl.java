package com.smart.service.impl.reagent;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.reagent.OutDao;
import com.smart.model.reagent.Out;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.reagent.OutManager;

@Service("outManager")
public class OutManagerImpl extends GenericManagerImpl<Out, Long> implements OutManager {
	
	private OutDao outDao;
	
    @Autowired
    public OutManagerImpl(OutDao outDao) {
        this.dao = outDao;
        this.outDao = outDao;
    }

	public void saveAll(List<Out> needSaveOut) {
		outDao.saveAll(needSaveOut);
	}
	
	public List<Out> getLastHMs(String ids, Date measuretime) {
		return outDao.getLastHMs(ids, measuretime);
	}

	public List<Out> getByLab(String lab) {
		return outDao.getByLab(lab);
	}

	public void updateTestnum(String lab, String testid, Long rgid, Date now) {
		outDao.updateTestnum(lab,testid,rgid,now);
	}

	public List<Out> getNeedCountList(String yesterday, String today) {
		return outDao.getNeedCountList(yesterday, today);
	}
}
