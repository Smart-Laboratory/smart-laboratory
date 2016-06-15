package com.smart.dao.hibernate.rule;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.rule.IndexDao;
import com.smart.model.rule.Index;
import com.smart.Constants;

@Repository("indexDao")
public class IndexDaoHibernate extends GenericDaoHibernate<Index, Long> implements IndexDao {

	public IndexDaoHibernate() {
		super(Index.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<Index> getIndexs(int pageNum, String field, boolean isAsc) {
		
		//获取从pageSize * (pageNum - 1)开始的最多pageSize个指标
		String dir = "";
		if (isAsc) { dir = " asc"; } 
		else { dir = " desc"; }
		Query q = getSession().createQuery("from Index order by " + field + dir);
		q.setFirstResult(Constants.PAGE_SIZE * (pageNum - 1));
		q.setMaxResults(Constants.PAGE_SIZE);
		
		return q.list();
	}

	@Override
	public List<Index> getIndexs(String query, int start, int end, String sidx, String sord) {
		String sql = "select lab_index.* from lab_index  where 1=1";
		sql += query;

		sidx = sidx.equals("") ? "id" : sidx;
		sql +=" order by  " +sidx + " " + sord;
		SQLQuery q = getSession().createSQLQuery(sql);
		//System.out.println(sql);
		if(end !=0){
			q.setFirstResult(start);
			q.setMaxResults(end);
		}
		return q.addEntity(Index.class).list();
	}

	@Override
	public int getIndexsCount(String query, int start, int end, String sidx, String sord) {
		String sql = "select count(1) cnt from lab_index  where 1=1";
		if(query != null && !query.equals(""))
			sql += query;
		sidx = sidx.equals("") ? "id" : sidx;
		sql +=" order by  " +sidx + " " + sord;
		Query q =  getSession().createSQLQuery(sql);
		return new Integer(q.uniqueResult() + "");
	}

	// 待增加疾病种类和检验专业的搜索
	@SuppressWarnings("unchecked")
	public List<Index> getIndexsByCategory(String sample, int pageNum, String field, boolean isAsc) {

		// 根据包id，获取所属包下的规则
		String dir = "";
		if (isAsc) { dir = " asc"; } 
		else { dir = " desc"; }
		String sql = "from Index where sampleFrom='" + sample + "' order by " + field + dir;
		//System.out.println(sql);
		Query q = getSession().createQuery(sql);
		q.setFirstResult(Constants.PAGE_SIZE * (pageNum - 1));
		q.setMaxResults(Constants.PAGE_SIZE);
		
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Index> getIndexs(String indexName) {
		List<Index> indexs = getSession().createQuery("from Index where name like '" + indexName + "%'  or english like '"+indexName+"%' order by name,sampleFrom").list();
		return indexs;
	}
	
	public List getIndexsByIdandLab(String indexId ,String labDepartment){
		List<Index> indexs = getSession().createQuery("from Index where indexId = '" + indexId + "'  and labdepartment = '"+labDepartment+"'").list();
		return indexs;
	}

	public int getIndexsCount() {
		return ((Number)getSession().createQuery("select count(*) from Index").iterate().next()).intValue();
	}

	public int getIndexsCount(String sample) {
		return ((Number)getSession().createQuery("select count(*) from Index where sampleFrom='"+sample+"'").iterate().next()).intValue();
	}
	
	@SuppressWarnings("rawtypes")
	public Index getIndex(String indexId) {
		String sql = "from Index where indexId =:indexId";
		Query query = getSession().createQuery(sql);
		query.setString("indexId",indexId);
		List indexs = query.list();
        if (indexs == null || indexs.isEmpty()) {
            return null;
        } else {
            return (Index)indexs.get(0);
        }
	}

	@SuppressWarnings("unchecked")
	public List<Index> getIndexsByName(String name, int pageNum, String field, boolean isAsc) {
		
		String dir = "";
		if (isAsc) { dir = " asc"; } 
		else { dir = " desc"; }
		String sql = "from Index where name like '" + name + "%' order by " + field + dir;
		//System.out.println(sql);
		Query q = this.getSessionFactory().getCurrentSession().createQuery(sql);
		q.setFirstResult(Constants.PAGE_SIZE * (pageNum - 1));
		q.setMaxResults(Constants.PAGE_SIZE);
		
		return q.list();
	}

	@Override
	public List<Index> getIndexsByQuery(String q) {
		String sql = "select lab_index.* from lab_index  where 1=1";
		sql += q;
		List<Index> list = getSession().createSQLQuery(sql).addEntity(Index.class).list();
		return list;
	}

	public int getIndexsByNameCount(String name) {
		return ((Number)getSession().createQuery("select count(*) from Index where name like '"+name+"%'").iterate().next()).intValue();
	}
	
	

}
