package com.smart.dao.rule;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.rule.DesBag;

public interface DesBagDao extends GenericDao<DesBag, Long> {
	
	@Transactional
	List<DesBag> getByParentId(Long parentId);

	@Transactional
	List<DesBag> getBagByHospital(Long hospital);
	
	@Transactional
	List<DesBag> getBag(String name);

//	@Transactional
//	List<Description> getDescriptionByBag(String did);

}
