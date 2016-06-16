package com.smart.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.model.user.Evaluate;

public interface EvaluateDao extends GenericDao<Evaluate, Long> {

	@Transactional
	List<Evaluate> getByBA(String sampleno, String collector);
	
}
