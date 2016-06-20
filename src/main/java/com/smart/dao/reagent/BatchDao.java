package com.smart.dao.reagent;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.reagent.Batch;

public interface BatchDao extends GenericDao<Batch, Long> {

	void saveAll(List<Batch> needSaveBatch);

	@Transactional
	List<Batch> getByRgId(Long id);

	@Transactional
	List<Batch> getByRgIds(String rids);

}
