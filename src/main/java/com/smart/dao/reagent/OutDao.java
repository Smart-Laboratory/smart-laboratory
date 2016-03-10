package com.smart.dao.reagent;

import java.util.Date;
import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.reagent.Out;

public interface OutDao extends GenericDao<Out, Long> {

	void saveAll(List<Out> needSaveOut);
	
	List<Out> getLastHMs(Long rgId, Date measuretime);

	List<Out> getByLab(String lab);
}
