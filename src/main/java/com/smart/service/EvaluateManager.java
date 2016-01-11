package com.smart.service;

import java.util.List;

import com.smart.model.user.Evaluate;

public interface EvaluateManager extends GenericManager<Evaluate, Long> {

	List<Evaluate> getByBA(String sampleno, String collector);
	
}
