package com.smart.dao.hibernate.lis;

import java.util.List;

import org.aspectj.apache.bcel.classfile.Constant;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.smart.Constants;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.SOPIndexDao;
import com.smart.model.lis.SOPIndex;

@Repository("SOPIndexDao")
public class SOPIndexDaoHibernate extends GenericDaoHibernate<SOPIndex, Long> implements SOPIndexDao {

	public SOPIndexDaoHibernate() {
		super(SOPIndex.class);
	}

	@SuppressWarnings("unchecked")
	public List<SOPIndex> getByLab(String lab) {
		Query q = getSession().createQuery("from SOPIndex where lab=:lab or lab='"+Constants.LaboratoryCode+"' order by type asc").setString("lab", lab);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<SOPIndex> getByType(String lab, int type) {
		Query q = getSession().createQuery("from SOPIndex where (lab=:lab or lab='"+Constants.LaboratoryCode+"') and type=:type order by type asc");
		q.setString("lab", lab).setLong("type", type);
		return q.list();
	}
}
