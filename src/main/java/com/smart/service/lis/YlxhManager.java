package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.Ylxh;
import com.smart.service.GenericManager;

public interface YlxhManager extends GenericManager<Ylxh, Long> {

	List<Ylxh> getYlxh();
	
	List<Ylxh> getTest(String lab);
	
	List<Ylxh> getSearchData(String text);

	String getRelativeTest(String ylxh);
	
	List<Ylxh> getLabofYlmcBylike(String lab ,String ylmc);

	int getSizeByLab(String lab, String sidx);

	List<Ylxh> getYlxhByLab(String query, String lab, int start, int end, String sidx, String sord);

}
