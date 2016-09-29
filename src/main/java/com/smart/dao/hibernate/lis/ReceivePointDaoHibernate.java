package com.smart.dao.hibernate.lis;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import com.ctc.wstx.util.StringUtil;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.ReceivePointDao;
import com.smart.model.lis.ReceivePoint;


@Repository("receivePointDao")
public class ReceivePointDaoHibernate extends GenericDaoHibernate<ReceivePoint, Long> implements ReceivePointDao {

	public ReceivePointDaoHibernate() {
		super(ReceivePoint.class);
	}

	@SuppressWarnings("unchecked")
	public List<ReceivePoint> getByType(int type) {
		return getSession().createQuery("from ReceivePoint where type=" + type).list();
	}

	@SuppressWarnings("unchecked")
	public List<ReceivePoint> getByName(String name){
		String hql= "from ReceivePoint where (name like '%"+name+"%' ";
		if(StringUtils.isNumeric(name))
			hql += "or id='"+name+"'";
		hql += ")";
		return getSession().createQuery(hql).list();
	}

	public int getPointCount(String query, String type) {
		String sql = "select count(1) cnt from l_receive_point where 1=1";
		if(type != null && !type.equals(""))
			sql += " and  \"TYPE\" = " +type;
		if(query != null && !query.equals(""))
			sql += " and (\"NAME\" like '%" +query +"%' or code like '%"+query+"%')";

		Query q =  getSession().createSQLQuery(sql);
		return new Integer(q.uniqueResult() + "");
	}

	public List<ReceivePoint> getList(String query, String type, int start, int end, String sidx, String sord) {
		String sql = " from ReceivePoint d where 1=1 ";
		if(type != null && !type.equals(""))
			sql += " and type = " +type;
		if(query != null && !query.equals(""))
			sql += " and (name like '%" +query +"%' or code like '%" + query + "%')";
		sidx = sidx.equals("") ? "id" : sidx;
		sql +=" order by  " +sidx + " " + sord;
		Query q = getSession().createQuery(sql);
		if(end !=0){
			q.setFirstResult(start);
			q.setMaxResults(end);
		}
		return q.list();
	}
}
