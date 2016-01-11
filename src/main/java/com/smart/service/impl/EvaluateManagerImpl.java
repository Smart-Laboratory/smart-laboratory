package com.smart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.model.user.Evaluate;
import com.smart.dao.EvaluateDao;
import com.smart.service.EvaluateManager;

@Service("evaluateManager")
public class EvaluateManagerImpl extends GenericManagerImpl<Evaluate, Long> implements EvaluateManager {

	private EvaluateDao evaluateDao;

	@Autowired
	public void setEvaluateDao(EvaluateDao evaluateDao) {
		this.dao = evaluateDao;
		this.evaluateDao = evaluateDao;
	}

	public List<Evaluate> getByBA(String sampleno, String collector) {
		return evaluateDao.getByBA(sampleno, collector);
	}

}
