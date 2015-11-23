package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.Ylxh;
import com.smart.service.GenericManager;

public interface YlxhManager extends GenericManager<Ylxh, Long> {

	List<Ylxh> getYlxh();
	
	List<Ylxh> getTest(String lab);
	
	List<Ylxh> getSearchData(String text);

}
