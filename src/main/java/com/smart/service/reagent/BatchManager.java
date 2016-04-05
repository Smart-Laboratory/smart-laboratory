package com.smart.service.reagent;

import java.util.List;

import com.smart.model.reagent.Batch;
import com.smart.service.GenericManager;

public interface BatchManager extends GenericManager<Batch, Long> {

	void saveAll(List<Batch> needSaveBatch);

	List<Batch> getByRgId(Long id);

	List<Batch> getByRgIds(String rids);

}
