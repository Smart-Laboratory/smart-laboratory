package com.smart.dao.rule;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.rule.Bag;
import com.smart.model.rule.Rule;

public interface BagDao extends GenericDao<Bag, Long> {

	List<Bag> getByParentId(Long parentId);

	List<Bag> getBagByHospital(Long hospital);
	
	List<Bag> getBag(String name);

	List<Rule> getRuleByBag(String hid);
	
}
