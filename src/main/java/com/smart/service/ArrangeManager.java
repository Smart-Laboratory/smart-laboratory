package com.smart.service;

import java.util.List;

import com.smart.model.pb.Arrange;

public interface ArrangeManager extends GenericManager<Arrange, Long> {

	void saveAll(List<Arrange> list);

	Arrange getByUser(String name, String day);
	
	List<Arrange> getArrangerd(String names, String month);
	
	List<Arrange> getMonthArrangeByName(String name, String month);

	List<Arrange> getPersonalArrange(String name, String month);

	List<Arrange> getSxjxArrangerd(String year);

	List<Arrange> getHistorySxjx(String names, String tomonth);

	void removeAll(String name, String date);
	
	List<String> getGXcount(String month);
	
	List<Arrange> getArrangeByType(String type, String month);
	
}
