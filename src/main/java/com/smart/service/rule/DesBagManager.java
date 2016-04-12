package com.smart.service.rule;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.model.rule.DesBag;
import com.smart.model.rule.Description;
import com.smart.service.GenericManager;

public interface DesBagManager extends GenericManager<DesBag, Long>{
	
	@Transactional
	List<DesBag> getByParentId(Long parentId);

	@Transactional
	List<DesBag> getBagByHospital(Long hospital);
	
	@Transactional
	List<DesBag> getBag(String name);

	@Transactional
	List<Description> getDescriptionByBag(String did);

}
