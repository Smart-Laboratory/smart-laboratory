package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.Ylxh;
import com.smart.service.GenericManager;
import org.springframework.transaction.annotation.Transactional;

public interface YlxhManager extends GenericManager<Ylxh, Long> {

	@Transactional
	List<Ylxh> getYlxh();

	@Transactional
	List<Ylxh> getTest(String lab);

	@Transactional
	String getRelativeTest(String ylxh);

	@Transactional
	List<Ylxh> getLabofYlmcBylike(String lab ,String ylmc);

	@Transactional
	int getSizeByLab(String lab, String sidx);

	@Transactional
	List<Ylxh> getYlxhByLab(String query, String lab, int start, int end, String sidx, String sord);

	@Transactional
	List<Ylxh> searchData(String query, String lab);
}
