package com.smart.dao.hibernate;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.smart.model.pb.Shift;
import com.smart.Constants;
import com.smart.dao.ShiftDao;

@Repository("shiftDao")
public class ShiftDaoHibernate extends GenericDaoHibernate<Shift, Long> implements ShiftDao {

	public ShiftDaoHibernate() {
		super(Shift.class);
	}

	@SuppressWarnings("unchecked")
	public List<String> getBySection(String section) {
		List<String> abs = getSession().createQuery("select ab from Shift where section='"+section+"'").list();
		return abs;
	}

	@SuppressWarnings("unchecked")
	public List<Shift> getShift(String section) {
		return getSession().createQuery("from Shift where section='"+section+"' or section='"+Constants.LaboratoryCode+"'").list();
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
		List<Shift> shifts = new LinkedList<Shift>();
		if(section==""+Constants.LaboratoryCode+"" || !section.contains("1300")){
			shifts = getSession().createQuery("from Shift where section='"+section+"' and showord>0 order by showord").list();
		}else{
			List<Shift> shifts1 = getSession().createQuery("from Shift where section='"+section+"' order by showord").list();
			List<Shift> shifts2 = getSession().createQuery("from Shift where section='"+Constants.LaboratoryCode+"' and showord = 0 order by showord").list();
			for(Shift s : shifts1){
				shifts.add(s);
			}
			for(Shift s : shifts2){
				shifts.add(s);
			}
		}
		return shifts;
	}
	@SuppressWarnings("unchecked")
	public List<Shift> getSx(){
		List<Shift> shifts = getSession().createQuery("from Shift where section = '"+Constants.LaboratoryCode+"' and order = '99' ").list();
		if(shifts == null || shifts.isEmpty())
			return null;
		return shifts;
	}
}
