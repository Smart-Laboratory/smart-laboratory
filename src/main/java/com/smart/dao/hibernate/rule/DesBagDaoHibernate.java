package com.smart.dao.hibernate.rule;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.rule.DesBagDao;
import com.smart.model.rule.DesBag;

@Repository("desBagDao")
public class DesBagDaoHibernate extends GenericDaoHibernate<DesBag, Long> implements DesBagDao{

	public DesBagDaoHibernate(){
		super(DesBag.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<DesBag> getByParentId(Long parentId) {
		return getSession().createQuery("from DesBag b where parent_id=" + parentId + " order by b.id").list();
	}

	@SuppressWarnings("unchecked")
	public List<DesBag> getBagByHospital(Long hospital) {
		return getSession().createQuery("from DesBag b where hospital_id=" + hospital + " order by upper(b.id)").list();
	}
	
	/*@SuppressWarnings("unchecked")
	public List<Description> getRuleByBag(String hospital) {
		List<DesBag> bags = getBagByHospital(Long.parseLong(hospital));
		List<Description> ruleList = new ArrayList<Description>();
		Set<Long> have = new HashSet<Long>();
		for(Bag b : bags) {
			for(Rule r : b.getRules()) {
				if(r.getType() != 1 && r.getType() != 2 && !have.contains(r.getId())) {
					r.getResults().size();
					r.getItems().size();
					ruleList.add(r);
					have.add(r.getId());
				}
			}
		}
		return ruleList;
	}*/

	@SuppressWarnings("unchecked")
	public List<DesBag> getBag(String name) {
		return getSession().createQuery("from DesBag where name like '"+name+"%'").list();
	}
}
