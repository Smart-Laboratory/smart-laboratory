package com.smart.dao;

import java.util.List;

import com.smart.model.pb.SxArrange;

public interface SxArrangeDao extends GenericDao<SxArrange, Long> {

	void saveAll(List<SxArrange> list);
	
	List<SxArrange> getByMonth(String month);
}
