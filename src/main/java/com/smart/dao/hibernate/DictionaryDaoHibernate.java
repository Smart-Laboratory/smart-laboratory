package com.smart.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.smart.model.Dictionary;
import com.smart.dao.DictionaryDao;

@Repository("dictionaryDao")
public class DictionaryDaoHibernate extends GenericDaoHibernate<Dictionary, Long>  implements DictionaryDao{
	
	public DictionaryDaoHibernate(){
		super(Dictionary.class);
	}

	public int getDictionaryCount(String query, String type) {
		String sql = "select count(1) cnt from lab_dictionary where 1=1 ";
		if(query != null && !query.equals(""))
			sql += " and (value  like '%" +query +"%' or sign like '%" +query +"%')";
		if(type != null && !type.equals(""))
			sql += " and type = " +type;
		Query q =  getSession().createSQLQuery(sql);
		return new Integer(q.uniqueResult() + "");
	}

	/**
	 * 获取科室分页列表
	 * @param query 查询值:编号或名称
	 * @param type
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Dictionary> getDictionaryList(String query, String type, int start, int end,String sidx,String sord)  {
		String sql = " from Dictionary s where 1=1 ";
		if(query != null && !query.equals(""))
			sql += " and s.value  like '%" +query +"%' or s.sign like '%" +query +"%')";
		if(type != null && !type.equals(""))
			sql += " and s.type = " +type;

		sidx = sidx.equals("")?"id":sidx;
		sql +=" order by  " +sidx + " " + sord;

		Query q =  getSession().createQuery(sql);

		if(end != 0){
			q.setFirstResult(start);
			q.setMaxResults(end);
		}
		return  q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Dictionary> getPatientInfo(String name) {
		String hql = "from Dictionary where type=2 and value like '%" + name + "%'";
		Query q = getSession().createQuery(hql);
		return q.list();
	}

	/**
	 * 样本类型信息 type =1
	 * @return
     */
	@SuppressWarnings("unchecked")
	public List<Dictionary> getSampleType() {
		return getSession().createQuery("from Dictionary where type=1").list();
	}

	/**
	 * 获取仪器类别信息 TYPE=3
	 * @return
     */
	@SuppressWarnings("unchecked")
	public List<Dictionary> getDeviceType(){
		return  getSession().createQuery("from Dictionary where type = 3").list();
	}

	/**
	 * 样本类型信息 type =1
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Dictionary> searchSampleType(String name) {
		return getSession().createQuery("from Dictionary where type=1 and value like '" + name + "%'").list();
	}
}
