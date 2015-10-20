package com.smart.service.impl.lis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.lis.BayesDao;
import com.smart.model.lis.Distribute;
import com.smart.service.lis.BayesService;

@Service("bayesService")
public class BayesManagerImpl implements BayesService {

	private BayesDao bayesDao;
	
	@Autowired
	public void setBayesDao(BayesDao bayesDao) {
		this.bayesDao = bayesDao;
	}

	public List<Distribute> getDistribute(String testId) {
		return bayesDao.getDistribute(testId);
	}

	public void update(List<Distribute> disList) {
		bayesDao.update(disList);
	}

}
