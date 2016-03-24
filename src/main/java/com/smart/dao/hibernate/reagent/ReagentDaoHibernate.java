package com.smart.dao.hibernate.reagent;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.reagent.ReagentDao;
import com.smart.model.reagent.Reagent;

@Repository("reagentDao")
public class ReagentDaoHibernate extends GenericDaoHibernate<Reagent, Long> implements ReagentDao {

	public ReagentDaoHibernate() {
		super(Reagent.class);
	}

	@SuppressWarnings("unchecked")
	public List<Reagent> getReagents(String name, String lab) {
		return getSession().createQuery("from Reagent r where lab='" + lab + "' and (name like '" + name + "%' or pinyin like '" + name + "%') order by upper(r.id)").list();
	}

	public Reagent getByname(String name) {
		return (Reagent) getSession().createQuery("from Reagent r where name='" + name + "' order by upper(r.id)").list().get(0);
	}

	@SuppressWarnings("unchecked")
	public List<Reagent> getByIds(String ids) {
		return getSession().createQuery("from Reagent r where id in (" + ids + ")").list();
	}

	@SuppressWarnings("unchecked")
	public List<Reagent> getByLab(String lab) {
		return getSession().createQuery("from Reagent r where lab='" + lab + "'").list();
	}

	@SuppressWarnings("unchecked")
	public List<Reagent> getByTestId(String testid) {
		return  getSession().createQuery("from Reagent where testname like '%" + testid + "%'").list();
	}

}
