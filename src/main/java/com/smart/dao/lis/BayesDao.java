package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.model.lis.Distribute;

public interface BayesDao {

	@Transactional
	List<Distribute> getDistribute(String testId);
	
	@Transactional
	void update(List<Distribute> disList);
}
