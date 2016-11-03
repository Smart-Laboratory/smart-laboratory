package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Ylxh;

public interface YlxhDao extends GenericDao<Ylxh, Long> {

	List<Ylxh> getYlxh();
	
	List<Ylxh> getTest(String lab);

	String getRelativeTest(String ylxh);
	
	List<Ylxh> getLabofYlmcBylike(String lab ,String ylmc);

	int getSizeByLab(String lab, String sidx);

	List<Ylxh> getYlxhByLab(String query, String lab, int start, int end, String sidx, String sord);

	//搜索检验套餐
	List<Ylxh> searchData(String text, String lab);

    String getLatestYlxh();

	void saveAll(List<Ylxh> list);
}
