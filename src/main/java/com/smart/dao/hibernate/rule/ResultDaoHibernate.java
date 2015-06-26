package com.smart.dao.hibernate.rule;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.smart.Constants;
import com.smart.dao.hibernate.GenericDaoHibernate;
import com.smart.dao.rule.ResultDao;
import com.smart.model.rule.Result;

@Repository("resultDao")
public class ResultDaoHibernate extends GenericDaoHibernate<Result, Long> implements ResultDao {

	public ResultDaoHibernate() {
		super(Result.class);
	}

	@SuppressWarnings("unchecked")
	public List<Result> getResults(int pageNum, String field, boolean isAsc) {
		
		//获取从pageSize * (pageNum - 1)开始的最多pageSize个结果
		String dir = "";
		if (isAsc) { dir = " asc"; } 
		else { dir = " desc"; }
		Query q = getSession().createQuery("from Result order by " + field + dir);
		q.setFirstResult(Constants.PAGE_SIZE * (pageNum - 1));
		q.setMaxResults(Constants.PAGE_SIZE);
		
		return q.list();
	}
	
	// 获取结果的总数
	public int getResultsCount() {
		return ((Long)getSession().createQuery("select count(*) from Result").iterate().next()).intValue();
	}

	// 增加对结果按类别分类搜索
	@SuppressWarnings("unchecked")
	public List<Result> getResultsByCategory(String category, int pageNum, String field, boolean isAsc) {

		//获取从pageSize * (pageNum - 1)开始的最多pageSize个结果
		Query q = this.getSessionFactory().getCurrentSession().createQuery("from Result");
		q.setFirstResult(Constants.PAGE_SIZE * (pageNum - 1));
		q.setMaxResults(Constants.PAGE_SIZE);
		
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<Result> getResultsByName(String result) {

		List<Result> results = getSession().createQuery("from Result where content like '%" + result + "%'").list();
		return results;
	}

	public Result updateResult(Result result) {
		return this.save(result);
	}

	public Result addResult(Result result) {
		return this.save(result);
	}
}
