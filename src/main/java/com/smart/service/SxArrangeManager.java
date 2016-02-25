package com.smart.service;

import java.util.List;

import com.smart.model.pb.SxArrange;

public interface SxArrangeManager extends GenericManager<SxArrange, Long> {

	void saveAll(List<SxArrange> list);
	
	List<SxArrange> getByMonth(String month);
}
