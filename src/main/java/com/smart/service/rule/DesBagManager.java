package com.smart.service.rule;

import java.util.List;

import com.smart.model.rule.DesBag;
import com.smart.model.rule.Description;
import com.smart.service.GenericManager;

public interface DesBagManager extends GenericManager<DesBag, Long>{
	
	List<DesBag> getByParentId(Long parentId);

	List<DesBag> getBagByHospital(Long hospital);
	
	List<DesBag> getBag(String name);

	List<Description> getDescriptionByBag(String did);

}
