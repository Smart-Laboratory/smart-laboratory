package com.smart.dao.hibernate.lis;

import java.util.List;

import com.smart.dao.lis.ReasoningModifyDao;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.model.lis.ReasoningModify;

public class ReasoningModifyDaoHibernate extends GenericDaoHibernate<ReasoningModify, Long> implements ReasoningModifyDao {

	/**
     * Constructor to create a Generics-based version using ReasoningModify as the entity
     */
	public ReasoningModifyDaoHibernate() {
		super(ReasoningModify.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<ReasoningModify> getBySampleId(String sampleId) {
		
		return getSession().createQuery("from ReasoningModify r where r.docNo='"+sampleId+"' order by r.modifyTime").list();
	}

	public int getAddNumber() {
		return getSession().createQuery("from ReasoningModify r where r.type='ADD'").list().size();
	}

	public int getDragNumber() {
		return getSession().createQuery("from ReasoningModify r where r.type='DRAG'").list().size();
	}
}