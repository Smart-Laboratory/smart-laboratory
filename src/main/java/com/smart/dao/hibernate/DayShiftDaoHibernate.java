package com.smart.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.model.pb.DayShift;
import com.smart.dao.DayShiftDao;

@Repository("dayShiftDao")
public class DayShiftDaoHibernate extends GenericDaoHibernate<DayShift, Long> implements DayShiftDao {

	public DayShiftDaoHibernate() {
		super(DayShift.class);
	}

	@SuppressWarnings("unchecked")
	public List<DayShift> getBySection(String section) {
		return getSession().createQuery("from DayShift where section="+section).list();
	}

}
