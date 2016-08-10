package com.smart.dao.hibernate.rule;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.smart.Constants;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.rule.DescriptionDao;
import com.smart.model.rule.Description;
import com.smart.model.rule.DesBag;

@Repository("descriptionDao")
public class DescriptionDaoHibernate extends GenericDaoHibernate<Description, Long> implements DescriptionDao{

	public DescriptionDaoHibernate(){
		super(Description.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Description> getDescription(int pageNum, String field, boolean isAsc) {
		
		//获取从pageSize * (pageNum - 1)开始的最多pageSize个规则
		String dir = "";
		if (isAsc) { dir = " asc"; } 
		else { dir = " desc"; }
		Query q = getSession().createQuery("from Description order by " + field + dir);
		q.setFirstResult(Constants.PAGE_SIZE * (pageNum - 1));
		q.setMaxResults(Constants.PAGE_SIZE);
		
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Description> getDescriptionsByBagID(Long bagId) {
		
		// 根据包id，获取所属包下的规则
		String sql = "select r from Description r  where r.bagId=:bagId";
		Query q = getSession().createQuery(sql).setLong("bagId", bagId);
		
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Description> getDescriptionsByBagID(Long bagId, int pageNum, String field, boolean isAsc) {

		// 根据包id，分页获取所属包下的规则
		String dir = "";
		if (isAsc) { dir = " asc"; } 
		else {dir = " desc";}
		String sql = "select r from Description r ,DesBag b where b.id=" + bagId +" and r.bagId = b.id order by r." + field + dir;
		Query q = getSession().createQuery(sql);
		q.setFirstResult(Constants.PAGE_SIZE * (pageNum - 1));
		q.setMaxResults(Constants.PAGE_SIZE);
		
		return q.list();
	}
	
	public int getDescriptionsCount() {
		return ((Long)getSession().createQuery("select count(*) from Description").iterate().next()).intValue();
	}

	public int getDescriptionsCount(Long bagId) {
		// 根据包id，获取所属包下的规则
		String sql = "select Count(r) from Description r  where r.bagId=:bagId";
		Query q = getSession().createQuery(sql).setLong("bagId", bagId);
		return ((Long)q.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Description> getDescriptionsByName(String ruleName) {
		List<Description> rules = getSession().createQuery("from Description where name like '%" + ruleName + "%' order by name").list();
		return rules;
	}

	@SuppressWarnings("unchecked")
	public List<Description> getDescriptionByType(int type) {	
		List<Description> rules = getSession().createQuery("from Description where type=" + type).list();
		return rules;
	}

	@SuppressWarnings("unchecked")
	public List<Description> getDescriptionList(String ruleIds) {
		return getSession().createQuery("from Description where id in (" + ruleIds +")").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Description> getDescriptionManual(String ruleIds) {
		Query query = getSession().createQuery("from Description where id in (" + ruleIds +")");
		List<Description> rules = query.list();
		return rules;
	}

	@SuppressWarnings("unchecked")
	public List<Description> getDescriptionByTypes(String type) {
		List<Description> rules = getSession().createQuery("from Description where type in (" + type + ")").list();
		return rules;
	}


}
