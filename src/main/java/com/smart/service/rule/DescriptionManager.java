package com.smart.service.rule;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.model.rule.Description;
import com.smart.service.GenericManager;

public interface DescriptionManager extends GenericManager<Description, Long>{
	
	List<Description> getDescription(int pageNum, String field, boolean isAsc);
	
	/**
	 *  返回规则数
	 * @return
	 */
	
	int getDescriptionsCount();
	
	/**
	 *  根据类别，搜索规则
	 * @param category  类别
	 * @return
	 */
	
	List<Description> getDescriptionsByBagID(Long bagId);
	
	/**
	 *  根据类别，分页搜索规则
	 * @param bagId 包ID
	 * @param pageNum
	 * @param field
	 * @param isAsc
	 * @return
	 */
	
	List<Description> getDescriptionsByBagID(Long bagId, int pageNum, String field, boolean isAsc);
	
	/**
	 *  该包下的规则数
	 * @param bagId
	 * @return
	 */
	
	int getDescriptionsCount(Long bagId);
	
	/**
	 *   根据规则的部分名称，模糊搜索匹配的规则
	 * @param ruleName
	 * @return
	 */
	
	List<Description> getDescriptionsByName(String ruleName);
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	
	List<Description> getDescriptionByType(int type);

	
	List<Description> getDescriptionList(String descriptionIds);
	
	
	List<Description> getDescriptionManual(String descriptionIds);

	
	List<Description> getDescriptionByTypes(String type);

}
