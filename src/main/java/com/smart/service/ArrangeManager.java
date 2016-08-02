package com.smart.service;

import java.util.List;

import com.smart.model.pb.Arrange;

public interface ArrangeManager extends GenericManager<Arrange, Long> {

	void saveAll(List<Arrange> list);

	Arrange getByUser(String name, String day);
	
	List<Arrange> getArrangerd(String names, String month, int state);
	
	List<Arrange> getMonthArrangeByName(String name, String month);

	List<Arrange> getPersonalArrange(String name, String month);

	List<Arrange> getSxjxArrangerd(String year);

	List<Arrange> getHistorySxjx(String names, String tomonth);

	void removeAll(String name, String date);
	
	List<String> getGXcount(String month);
	
	List<Arrange> getArrangeByType(String type, String month);
	
	List<Arrange> getPublish(String section,String month,int state);
	/*
	 * 根据科室、月份取排班记录
	 */
	List<Arrange> getBySectionMonth(String month, String section);
	
	/*
	 * 获取B超3个月内的排班记录
	 */
	List<Arrange> getMonthArrangeByshift(String shift, String month,String section);
	
	List<Arrange> getByDay(String day);
	
}
