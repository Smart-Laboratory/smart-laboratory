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
		return getSession().createCriteria(Bag.class).add(Restrictions.eq("parentID", parentId)).list();
	}

	@SuppressWarnings("unchecked")
	public List<Bag> getBag() {
		return getSession().createQuery("from Bag b order by upper(b.id)").list();
	}

	@SuppressWarnings("unchecked")
	public List<Bag> getBag(String name) {
		return getSession().createCriteria(Bag.class).add(Restrictions.eq("name", name)).list();
	}
}
