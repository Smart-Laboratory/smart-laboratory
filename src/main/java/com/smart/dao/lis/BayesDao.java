package com.smart.dao.lis;

import java.util.List;

import com.smart.model.lis.Distribute;

public interface BayesDao {

	List<Distribute> getDistribute(String testId);
	
	void update(List<Distribute> disList);
}
