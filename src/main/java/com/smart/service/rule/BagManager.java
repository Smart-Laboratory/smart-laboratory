package com.smart.service.rule;

import java.util.List;

import com.smart.model.rule.Bag;
import com.smart.service.GenericManager;

public interface BagManager extends GenericManager<Bag, Long> {

	/**
	 *  获取所有的包
	 * @param hospital 
	 * @return
	 */
	List<Bag> getBagByHospital(String hospital);
	
	/**
	 *  获取所有子包
	 * @param parentId	父包Id
	 * @return
	 */
	List<Bag> getBag(Long parentId);
	
	/**
	 *  根据名称模糊搜索
	 * @param name	包名称
	 * @return
	 */
	List<Bag> getBagByName(String name);
}
