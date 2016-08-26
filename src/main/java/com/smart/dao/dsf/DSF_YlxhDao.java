package com.smart.dao.dsf;

import com.smart.dao.GenericDao;
import com.smart.model.dsf.DSF_ylxh;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DSF_YlxhDao extends GenericDao<DSF_ylxh, Long> {

	@Transactional
	List<DSF_ylxh> getYlxh();
	
	@Transactional
	List<DSF_ylxh> getTest(String lab);

	//搜索检验套餐
	@Transactional
	List<DSF_ylxh> getSearchData(String text);

	@Transactional
	String getRelativeTest(String ylxh);
	
	@Transactional
	List<DSF_ylxh> getLabofYlmcBylike(String lab, String ylmc);

	@Transactional
	int getSizeByLab(String lab, String sidx);

	@Transactional
	List<DSF_ylxh> getYlxhByLab(String query, String lab, int start, int end, String sidx, String sord,String customerid);
}
