package com.smart.dao.reagent;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.reagent.Out;

public interface OutDao extends GenericDao<Out, Long> {

	void saveAll(List<Out> needSaveOut);
	
	@Transactional
	List<Out> getLastHMs(String ids, Date measuretime);

	@Transactional
	List<Out> getByLab(String lab);

	void updateTestnum(String lab, String testid, Long rgid, Date now);

	List<Out> getNeedCountList(String yesterday, String today);
}
