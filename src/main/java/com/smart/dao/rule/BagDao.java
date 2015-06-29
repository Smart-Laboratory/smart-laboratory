package com.smart.dao.rule;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.rule.Bag;

public interface BagDao extends GenericDao<Bag, Long> {

	List<Bag> getByParentId(Long parentId);

	List<Bag> getBagByHospital(String hospital);
	
	List<Bag> getBag(String name);
	
}
