package com.smart.dao.hibernate.rule;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.rule.BagDao;
import com.smart.model.rule.Bag;

@Repository("bagDao")
public class BagDaoHibernate extends GenericDaoHibernate<Bag, Long> implements BagDao {

    public BagDaoHibernate() {
        super(Bag.class);
    }

	@SuppressWarnings("unchecked")
	public List<Bag> getByParentId(Long parentId) {
		return getSession().createQuery("from Bag b where parent_id=" + parentId + " order by upper(b.id)").list();
	}

	@SuppressWarnings("unchecked")
	public List<Bag> getBagByHospital(String hospital) {
		return getSession().createQuery("from Bag b where hospital_id=" + hospital + " order by upper(b.id)").list();
	}

	@SuppressWarnings("unchecked")
	public List<Bag> getBag(String name) {
		return getSession().createCriteria(Bag.class).add(Restrictions.eq("name", name)).list();
	}
}
