package com.smart.dao.reagent;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.reagent.Out;

public interface OutDao extends GenericDao<Out, Long> {

	void saveAll(List<Out> needSaveOut);
	
	@Transactional
	List<Out> getLastHMs(String ids, String measuretime);

	@Transactional
	List<Out> getByLab(String lab);

	@Transactional
	void updateTestnum(String lab, String testid, Long rgid, Date now);
}
