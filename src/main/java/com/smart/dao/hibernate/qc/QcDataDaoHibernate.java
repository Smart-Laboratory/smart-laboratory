package com.smart.dao.hibernate.qc;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.qc.QcDataDao;
import com.smart.model.qc.QcData;

import java.util.List;

@Repository("qcDataDao")
public class QcDataDaoHibernate extends GenericDaoHibernate<QcData, Long> implements QcDataDao {

	public QcDataDaoHibernate() {
		super(QcData.class);
	}

	public List<QcData> getByMonth(String lab, String deviceid, String qcbatch, String testid, String month){
		String hql = "from QcData where labDepart=:lab and deviceid=:deviceid and qcBatch=:qcbatch and testid=:testid and " +
				" to_char(measuretime,'yyyy-MM')=:month order by measuretime asc";
		Query q = getSession().createQuery(hql);
		q.setString("lab",lab);
		q.setString("deviceid",deviceid);
		q.setString("qcbatch",qcbatch);
		q.setString("testid",testid);
		q.setString("month",month);
		return q.list();
	}
}
