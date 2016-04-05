package com.smart.dao.reagent;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.reagent.Batch;

public interface BatchDao extends GenericDao<Batch, Long> {

	void saveAll(List<Batch> needSaveBatch);

	List<Batch> getByRgId(Long id);

	List<Batch> getByRgIds(String rids);

}
