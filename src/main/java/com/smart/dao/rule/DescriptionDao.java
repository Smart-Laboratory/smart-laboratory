package com.smart.dao.rule;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.rule.Description;

public interface DescriptionDao extends GenericDao<Description, Long> {

	/**
	 *  搜索一页的规则
	 * @param pageNum 页号
	 * @param field 排序字段
	 * @param isAsc 排序方向
	 * @return
	 */
	@Transactional
	List<Description> getDescription(int pageNum, String field, boolean isAsc);
	
	/**
	 *  返回规则数
	 * @return
	 */
	@Transactional
	int getDescriptionsCount();
	
	/**
	 *  根据类别，搜索规则
	 * @param category  类别
	 * @return
	 */
	@Transactional
	List<Description> getDescriptionsByBagID(Long bagId);
	
	/**
	 *  根据类别，分页搜索规则
	 * @param bagId 包ID
	 * @param pageNum
	 * @param field
	 * @param isAsc
	 * @return
	 */
	@Transactional
	List<Description> getDescriptionsByBagID(Long bagId, int pageNum, String field, boolean isAsc);
	
	/**
	 *  该包下的规则数
	 * @param bagId
	 * @return
	 */
	@Transactional
	int getDescriptionsCount(Long bagId);
	
	/**
	 *   根据规则的部分名称，模糊搜索匹配的规则
	 * @param ruleName
	 * @return
	 */
	@Transactional
	List<Description> getDescriptionsByName(String ruleName);
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	@Transactional
	List<Description> getDescriptionByType(int type);

	@Transactional
	List<Description> getDescriptionList(String descriptionIds);
	
	@Transactional
	List<Description> getDescriptionManual(String descriptionIds);

	@Transactional
	List<Description> getDescriptionByTypes(String type);
	
}
