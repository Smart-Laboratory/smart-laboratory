package com.smart.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.model.pb.Shift;
import com.smart.dao.ShiftDao;

@Repository("shiftDao")
public class ShiftDaoHibernate extends GenericDaoHibernate<Shift, Long> implements ShiftDao {

	public ShiftDaoHibernate() {
		super(Shift.class);
	}

	@SuppressWarnings("unchecked")
	public List<String> getBySection(String section) {
		List abs = getSession().createQuery("select ab from Shift where section='"+section+"'").list();
		return abs;
	}

	@SuppressWarnings("unchecked")
	public List<Shift> getShift(String section) {
		return getSession().createQuery("from Shift where section='"+section+"' or section='1300000'").list();
	}

	@SuppressWarnings("unchecked")
	public List<Shift> getGrShift(String shift) {
		return getSession().createQuery("from Shift where ab in (" + shift + ")").list();
	}

	@SuppressWarnings("unchecked")
	public List<Shift> getAll(String sidx, String sord) {
		return getSession().createQuery("from Shift order by " + sidx + " " + sord).list();
	}

	@SuppressWarnings("unchecked")
	public List<Shift> getShiftBySection(String section) {
		return getSession().createQuery("from Shift where section='"+section+"'").list();
	}

}
