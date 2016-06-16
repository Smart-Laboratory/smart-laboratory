package com.smart.dao.rule;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.rule.DesBag;

public interface DesBagDao extends GenericDao<DesBag, Long> {
	List<DesBag> getByParentId(Long parentId);

	List<DesBag> getBagByHospital(Long hospital);
	
	List<DesBag> getBag(String name);

//	@Transactional
//	List<Description> getDescriptionByBag(String did);

}
