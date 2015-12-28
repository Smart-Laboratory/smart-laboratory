package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Ylxh;

public interface YlxhDao extends GenericDao<Ylxh, Long> {

	@Transactional
	List<Ylxh> getYlxh();
	
	@Transactional
	List<Ylxh> getTest(String lab);

	//搜索检验套餐
	@Transactional
	List<Ylxh> getSearchData(String text);

	@Transactional
	String getRelativeTest(String ylxh);
}
