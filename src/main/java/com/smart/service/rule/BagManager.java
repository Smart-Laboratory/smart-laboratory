package com.smart.service.rule;

import java.util.List;

import com.smart.model.rule.Bag;
import com.smart.model.rule.Rule;
import com.smart.service.GenericManager;

public interface BagManager extends GenericManager<Bag, Long> {

	/**
	 *  获取所有的包
	 * @param hid 
	 * @return
	 */
	List<Bag> getBagByHospital(Long hid);
	
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

	/**
	 *  根据包获取所有规则
	 * @param hid	医院编号
	 * @return
	 */
	List<Rule> getRuleByBag(String hid);
}
