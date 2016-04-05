package com.smart.service.impl.reagent;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.reagent.BatchDao;
import com.smart.model.reagent.Batch;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.reagent.BatchManager;

@Service("batchManager")
public class BatchManagerImpl extends GenericManagerImpl<Batch, Long> implements BatchManager {
	
	private BatchDao batchDao;
	
    @Autowired
    public BatchManagerImpl(BatchDao batchDao) {
        this.dao = batchDao;
        this.batchDao = batchDao;
    }

	public void saveAll(List<Batch> needSaveBatch) {
		batchDao.saveAll(needSaveBatch);
	}

	public List<Batch> getByRgId(Long id) {
		return batchDao.getByRgId(id);
	}

	public List<Batch> getByRgIds(String rids) {
		return batchDao.getByRgIds(rids);
	}
}
