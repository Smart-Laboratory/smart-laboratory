package com.smart.service.impl.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.rule.ResultDao;
import com.smart.model.rule.Result;
import com.smart.service.impl.GenericManagerImpl;
import com.smart.service.rule.ResultManager;

@Service("resultManager")
public class ResultManagerImpl extends GenericManagerImpl<Result, Long> implements ResultManager {

	private ResultDao resultDao;
	
	@Autowired
	public void setResultDao(ResultDao resultDao) {
		this.dao = resultDao;
		this.resultDao = resultDao;
	}

	public List<Result> getResults(String name) {
		return resultDao.getResultsByName(name);
	}

	public List<Result> getResults(int pageNum, String field, boolean isAsc) {
		return resultDao.getResults(pageNum, field, isAsc);
	}

	public List<Result> getResults(String category, int pageNum, String field, boolean isAsc) {
		return resultDao.getResultsByCategory(category, pageNum, field, isAsc);
	}

	public Result addResult(Result result) {
		return resultDao.save(result);
	}

	public Result updateResult(Result result) {
		return resultDao.save(result);
	}

	public int getResultsCount() {
		return resultDao.getResultsCount();
	}
}
