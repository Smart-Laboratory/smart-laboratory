package com.smart.service.impl.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.rule.RuleDao;
import com.smart.model.rule.Index;
import com.smart.model.rule.Rule;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.rule.RuleManager;

@Service("ruleManager")
public class RuleManagerImpl extends GenericManagerImpl<Rule, Long> implements RuleManager {

	private RuleDao ruleDao;
	
	@Autowired
	public void setRuleDao(RuleDao ruleDao) {
		this.dao = ruleDao;
		this.ruleDao = ruleDao;
	}

	public List<Rule> getRules(int pageNum, String field, boolean isAsc) {
		return ruleDao.getRules(pageNum, field, isAsc);
	}
	
	public int getRulesCount() {
		return ruleDao.getRulesCount();
	}

	public List<Rule> getRules(String bagId) {
		
		if (null == bagId || bagId.equals("")) {
			return null;
		} else {
			Long id = Long.parseLong(bagId);
			return ruleDao.getRulesByBagID(id);
		}
	}

	public List<Rule> getRules(String bagId, int pageNum, String field, boolean isAsc) {

		if (null == bagId || bagId.equals("")) {
			return null;
		} else {
			Long id = Long.parseLong(bagId);
			return ruleDao.getRulesByBagID(id, pageNum, field, isAsc);
		}
	}
	
	public int getRulesCount(String bagId) {
		return ruleDao.getRulesCount(Long.parseLong(bagId));
	}

	public List<Rule> searchRule(String ruleName) {

		if (null == ruleName || ruleName.equals("")) {
			return this.getAll();
		} else {
			return ruleDao.getRulesByName(ruleName);
		}
	}

	/**
	 *   添加规则，同时更新创建者，创建时间
	 */
	public Rule addRule(Rule rule) {
		return ruleDao.save(rule);
	}

	/**
	 *   更新规则，同时更新修改者、修改时间
	 */
	public Rule updateRule(Rule rule) {
		return ruleDao.save(rule);
	}
	
	public Rule saveRule(Rule rule){
		return ruleDao.saveRule(rule);
	}

	public List<Rule> getRuleByType(int type) {
		return ruleDao.getRuleByType(type);
	}

	/**
	 *   通过规则的id列表获取规则列表
	 */
	public List<Rule> getRuleList(String ruleIds) {
		return ruleDao.getRuleList(ruleIds);
	}
	
	public List<Rule> getRuleManual(String ruleIds) {
		return ruleDao.getRuleManual(ruleIds);
	}

	public List<Rule> getRuleByTypes(String type) {
		return ruleDao.getRuleByTypes(type);
	}

	public List<Rule> getDiffRule(int i, String mode) {
		return ruleDao.getDiffRule(i, mode);
	}

	public List<Rule> getRuleByIndexId(String indexId) {
		return ruleDao.getRuleByIndexId(indexId);
	}

	public List<Index> getUsedIndex(Long id) {
		return ruleDao.getUsedIndex(id);
	}
	
}
