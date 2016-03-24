package com.smart.service;

import java.util.List;

import com.smart.model.pb.SxArrange;
import com.smart.model.pb.WInfo;

public interface SxArrangeManager extends GenericManager<SxArrange, Long> {

	void saveAll(List<SxArrange> list);
	
	List<SxArrange> getByMonth(String month);
	
	//获取当年的排班记录
	List<SxArrange> getByName(String name);
	
	List<SxArrange> getByWeek(int year, int startWeek, int maxWeek);
	
}
