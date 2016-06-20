package com.smart.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.model.pb.SxArrange;

public interface SxArrangeDao extends GenericDao<SxArrange, Long> {

	void saveAll(List<SxArrange> list);
	
	@Transactional
	List<SxArrange> getByMonth(String month);
	
	@Transactional
	List<SxArrange> getByName(String name);
	
	//获取一年内的排班
	@Transactional
	List<SxArrange> getByWeek(int year, int startWeek, int maxWeek);
}
