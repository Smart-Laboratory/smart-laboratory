package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Ylxh;

public interface YlxhDao extends GenericDao<Ylxh, Long> {

	List<Ylxh> getYlxh();
	
	List<Ylxh> getTest(String lab);

	//搜索检验套餐
	List<Ylxh> getSearchData(String text);

	String getRelativeTest(String ylxh);
	
	List<Ylxh> getLabofYlmcBylike(String lab ,String ylmc);
}
