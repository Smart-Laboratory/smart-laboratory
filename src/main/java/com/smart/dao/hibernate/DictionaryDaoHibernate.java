package com.smart.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.smart.model.Dictionary;
import com.smart.dao.DictionaryDao;

@Repository("dictionaryDao")
public class DictionaryDaoHibernate extends GenericDaoHibernate<Dictionary, Long>  implements DictionaryDao{
	
	public DictionaryDaoHibernate(){
		super(Dictionary.class);
	}

	@Override
	public List<Dictionary> getDictionaryList(Dictionary dictionary, int start, int end, String sidx, String sord) {
		Criteria criteria = getSession().createCriteria(Dictionary.class);
		//字典信息查询
		if( dictionary.getType() >0){
			criteria.add(Restrictions.eq("type",dictionary.getType()));
		}
		if(null!=dictionary.getValue() && !"".equals(dictionary.getValue()))   {
			criteria.add(Restrictions.ilike("value",dictionary.getValue()));
		}
		if(!"".equals(sord) && "desc".equals(sord))
			criteria.addOrder(Order.asc(sidx));
		else
			criteria.addOrder(Order.asc(sidx));

		int counts = criteria.list().size();
		criteria.setFirstResult(start);
		criteria.setMaxResults(end);

		List<Dictionary> list= criteria.list();
		return list;
	}


//	@SuppressWarnings("unchecked")
//	public List<Dictionary> getPatientInfo(String name) {
//		String hql = "from Dictionary where type=2 and value like '%" + name + "%'";
//		Query q = getSession().createQuery(hql);
//		return q.list();
//	}
//
//	@SuppressWarnings("unchecked")
//	public List<Dictionary> getSampleType() {
//		return getSession().createQuery("from Dictionary where type=1").list();
//	}

}
