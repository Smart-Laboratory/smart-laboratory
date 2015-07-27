package com.smart.dao.hibernate.rule;

import java.util.List;

import org.hibernate.Query;
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
		List<Index> indexs = getSession().createQuery("from Index where name like '" + indexName + "%' order by name,sampleFrom").list();
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
		
		List indexs = getSession().createQuery("from Index where indexId=" + indexId).list();
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

	public int getIndexsByNameCount(String name) {
		return ((Number)getSession().createQuery("select count(*) from Index where name like '"+name+"%'").iterate().next()).intValue();
	}

}