package com.smart.dao.hibernate.rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.rule.BagDao;
import com.smart.model.rule.Bag;
import com.smart.model.rule.Rule;

@Repository("bagDao")
public class BagDaoHibernate extends GenericDaoHibernate<Bag, Long> implements BagDao {

    public BagDaoHibernate() {
        super(Bag.class);
    }

	@SuppressWarnings("unchecked")
	public List<Bag> getByParentId(Long parentId) {
		return getSession().createQuery("from Bag where parent_id=" + parentId + " order by upper(id)").list();
	}

	@SuppressWarnings("unchecked")
	public List<Bag> getBagByHospital(Long hospital) {
		return getSession().createQuery("from Bag b where hospital_id=" + hospital + " order by upper(b.id)").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Rule> getRuleByBag(String hospital) {
		List<Bag> bags = getSession().createQuery("from Bag where hospital_id=" + hospital + " order by upper(id)").list();
		List<Rule> ruleList = new ArrayList<Rule>();
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
	}

	@SuppressWarnings("unchecked")
	public List<Bag> getBag(String name) {
		return getSession().createQuery("from Bag where name='" + name + "' order by upper(id)").list();
	}
}
