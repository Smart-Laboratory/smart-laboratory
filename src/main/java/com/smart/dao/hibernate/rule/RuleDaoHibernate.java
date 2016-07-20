package com.smart.dao.hibernate.rule;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.smart.Constants;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.rule.RuleDao;
import com.smart.model.rule.Index;
import com.smart.model.rule.Rule;

@Repository("ruleDao")
public class RuleDaoHibernate extends GenericDaoHibernate<Rule, Long> implements RuleDao {
	
	public RuleDaoHibernate() {
		super(Rule.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Rule> getRules(int pageNum, String field, boolean isAsc) {
		
		//获取从pageSize * (pageNum - 1)开始的最多pageSize个规则
		String dir = "";
		if (isAsc) { dir = " asc"; } 
		else { dir = " desc"; }
		Query q = getSession().createQuery("from Rule order by " + field + dir);
		q.setFirstResult(Constants.PAGE_SIZE * (pageNum - 1));
		q.setMaxResults(Constants.PAGE_SIZE);
		
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Rule> getRulesByBagID(Long bagId) {
		
		// 根据包id，获取所属包下的规则
		String sql = "select r from Rule r inner join r.bags b where b.id=:bagId";
		Query q = getSession().createQuery(sql).setLong("bagId", bagId);
		
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Rule> getRulesByBagID(Long bagId, int pageNum, String field, boolean isAsc) {

		// 根据包id，分页获取所属包下的规则
		String dir = "";
		if (isAsc) { dir = " asc"; } 
		else {dir = " desc";}
		String sql = "select r from Rule r inner join r.bags b where b.id=" + bagId +" order by r." + field + dir;
		Query q = getSession().createQuery(sql);
		q.setFirstResult(Constants.PAGE_SIZE * (pageNum - 1));
		q.setMaxResults(Constants.PAGE_SIZE);
		
		return q.list();
	}
	
	public int getRulesCount() {
		return ((Long)getSession().createQuery("select count(*) from Rule").iterate().next()).intValue();
	}

	public int getRulesCount(Long bagId) {
		// 根据包id，获取所属包下的规则
		String sql = "select Count(r) from Rule r inner join r.bags b where b.id=:bagId";
		Query q = getSession().createQuery(sql).setLong("bagId", bagId);
		return ((Long)q.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Rule> getRulesByName(String ruleName) {
		List<Rule> rules = getSession().createQuery("from Rule where name like '%" + ruleName + "%' order by name").list();
		return rules;
	}

	@SuppressWarnings("unchecked")
	public List<Rule> getRuleByType(int type) {	
		List<Rule> rules = getSession().createQuery("from Rule where type=" + type).list();
		for (Rule r : rules) {
			r.getResults().size();
			r.getItems().size();
		}
		return rules;
	}

	@SuppressWarnings("unchecked")
	public List<Rule> getRuleList(String ruleIds) {
		return getSession().createQuery("from Rule where id in (" + ruleIds +")").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Rule> getRuleManual(String ruleIds) {
		Query query = getSession().createQuery("from Rule where id in (" + ruleIds +")");
		List<Rule> rules = query.list();
		return rules;
	}

	@SuppressWarnings("unchecked")
	public List<Rule> getRuleByTypes(String type) {
		List<Rule> rules = getSession().createQuery("from Rule where type in (" + type + ")").list();
		for (Rule r : rules) {
			r.getResults().size();
			r.getItems().size();
		}
		return rules;
	}

	@SuppressWarnings("unchecked")
	public List<Index> getUsedIndex(Long id) {
		return getSession().createSQLQuery(
			"select b.* from lab_rule a, lab_index b, lab_item c, lab_rule_item d where a.id=" + id + " and a.id=d.rule_id and d.item_id=c.id and b.index_id=c.indexid"
		).addEntity("b", Index.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<Rule> getDiffRule(int type, String mode) {
		List<Rule> rules = getSession().createQuery("from Rule where type=" + type +" and hospitalmode in "+ mode).list();
		for (Rule r : rules) {
			r.getResults().size();
			r.getItems().size();
		}
		return rules;
	}
	
	public Rule saveRule(Rule rule){
		if (log.isDebugEnabled()) {
            log.debug("user's id: " + rule.getId());
        }
        Session s = getSession();
        s.saveOrUpdate(rule);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        s.flush();
        return rule;
		
	}

	@SuppressWarnings("unchecked")
	public List<Rule> getRuleByIndexId(String indexId) {
		Query query = getSession().createSQLQuery(
			"select a.* from lab_rule a, lab_index b, lab_item c, lab_rule_item d where b.index_id='" + indexId + "' and a.id=d.rule_id and d.item_id=c.id and b.index_id=c.indexid"
		);
		return query.list();
	}
}