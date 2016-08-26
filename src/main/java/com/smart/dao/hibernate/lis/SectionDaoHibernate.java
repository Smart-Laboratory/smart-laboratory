package com.smart.dao.hibernate.lis;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.smart.model.lis.Section;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.lis.SectionDao;

import java.util.*;

@Repository("sectionDao")
public class SectionDaoHibernate extends GenericDaoHibernate<Section, Long> implements SectionDao {
	
	public SectionDaoHibernate(){
		super(Section.class);
	}

	public Section getByCode(String sectionId) {
		return (Section) getSession().createQuery("from Section where code=" + sectionId).uniqueResult();
	}

	public int getSectionCount(String query, String hospitalId) {
		String sql = "select count(1) cnt from l_depart where 1=1";
		if(query != null && !query.equals(""))
			sql += " and code||name like '%" +query +"%'";
		if(hospitalId != null && hospitalId.equals(""))
			sql += " and hospital_id = " +hospitalId;
		Query q =  getSession().createSQLQuery(sql);
		return new Integer(q.uniqueResult() + "");
	}

	/**
	 * 获取科室分页列表
	 * @param query 查询值:编号或名称
	 * @param hospitalId
	 * @param start
	 * @param end
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<Section> getSectionList(String query, String hospitalId, int start, int end,String sidx,String sord)  {
		String sql = "from Section s where 1=1 ";
		if(query != null && !query.equals(""))
			sql += " and s.code||s.name  like '%" +query +"%'";
		if(hospitalId != null && hospitalId.equals(""))
			sql += " and s.hospital_id = " +hospitalId;

		sidx = sidx.equals("")?"code":sidx;
		sql +=" order by  " +sidx + " " + sord;

		Query q =  getSession().createQuery(sql);

		if(end != 0){
			q.setFirstResult(start);
			q.setMaxResults(end);
		}
		return  q.list();
	}

	/**
	 * 根据代码、名称查询部门列表
	 * @param name
	 * @return
     */
	@SuppressWarnings("unchecked")
	public List<Section> getSectionList(String name) {
		return getSession().createQuery("from Section as s where s.code like '" + name +"%' or s.name like '" +name +"%' order by s.name,s.code" ).list();
	}

	/**
	 * 批量删除科室
	 * @param ids 科室ID
     * @return
     */
	public boolean batchRemove(long[] ids) {
		StringBuilder  sql = new StringBuilder(" delete from Section p where p.id in (?");
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {

			for(int i=1;i <ids.length;i++){
				sql.append( ",?");
			}
			sql.append(")");
			Query q = session.createQuery(sql.toString());
			for(int i=0;i<ids.length;i++) {
				q.setLong(i, ids[i]);
			}
			q.executeUpdate();
			tx.commit();
		}catch (HibernateException e){
			tx.rollback();
			System.out.print(e.getMessage());
			log.error(e.getMessage());
		} finally {
			session.close();
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Section> getPbSection(String hospitalId){
		String hql="from Section where ispb=1 and hospitalId="+hospitalId;
		return getSession().createQuery(hql).list();
	}

}
