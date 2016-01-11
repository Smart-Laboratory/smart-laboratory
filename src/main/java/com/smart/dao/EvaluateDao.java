package com.smart.dao;

import java.util.List;

import com.smart.model.user.Evaluate;

public interface EvaluateDao extends GenericDao<Evaluate, Long> {

	List<Evaluate> getByBA(String sampleno, String collector);
	
}
