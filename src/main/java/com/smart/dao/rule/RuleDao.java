package com.smart.dao.rule;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.rule.Index;
import com.smart.model.rule.Rule;


public interface RuleDao extends GenericDao<Rule, Long> {

	/**
	 *  搜索一页的规则
	 * @param pageNum 页号
	 * @param field 排序字段
	 * @param isAsc 排序方向
	 * @return
	 */
	@Transactional
	List<Rule> getRules(int pageNum, String field, boolean isAsc);
	
	/**
	 *  返回规则数
	 * @return
	 */
	@Transactional
	int getRulesCount();
	
	/**
	 *  根据类别，搜索规则
	 * @param category  类别
	 * @return
	 */
	@Transactional
	List<Rule> getRulesByBagID(Long bagId);
	
	/**
	 *  根据类别，分页搜索规则
	 * @param bagId 包ID
	 * @param pageNum
	 * @param field
	 * @param isAsc
	 * @return
	 */
	@Transactional
	List<Rule> getRulesByBagID(Long bagId, int pageNum, String field, boolean isAsc);
	
	/**
	 *  该包下的规则数
	 * @param bagId
	 * @return
	 */
	@Transactional
	int getRulesCount(Long bagId);
	
	/**
	 *   根据规则的部分名称，模糊搜索匹配的规则
	 * @param ruleName
	 * @return
	 */
	@Transactional
	List<Rule> getRulesByName(String ruleName);
	
	/**
	 * 
	 * @param type
	 * @return
	 */
	@Transactional
	List<Rule> getRuleByType(int type);

	@Transactional
	List<Rule> getRuleList(String ruleIds);
	
	@Transactional
	List<Rule> getRuleManual(String ruleIds);

	@Transactional
	List<Rule> getRuleByTypes(String type);
	
	@Transactional
	List<Rule> getDiffRule(int i, String mode);
	
	@Transactional
	Rule saveRule(Rule rule);

	@Transactional
	List<Rule> getRuleByIndexId(String indexId);

	@Transactional
	List<Index> getUsedIndex(Long id);
}
