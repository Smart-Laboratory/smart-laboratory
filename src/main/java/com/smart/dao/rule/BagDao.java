package com.smart.dao.rule;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.rule.Bag;

public interface BagDao extends GenericDao<Bag, Long> {

	@Transactional
	List<Bag> getByParentId(Long parentId);

	@Transactional
	List<Bag> getBagByHospital(String hospital);
	
	@Transactional
	List<Bag> getBag(String name);
	
}
