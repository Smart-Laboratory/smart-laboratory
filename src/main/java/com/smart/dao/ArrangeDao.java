package com.smart.dao;

import java.util.List;

import com.smart.model.pb.Arrange;

public interface ArrangeDao extends GenericDao<Arrange, Long> {

	void saveAll(List<Arrange> list);
	
	Arrange getByUser(String name, String day);

	List<Arrange> getArrangerd(String names, String month, int state);

	List<Arrange> getPersonalArrange(String name, String day);
	
	List<Arrange> getMonthArrangeByName(String name, String month);

	List<Arrange> getSxjxArrangerd(String yearAndMonth);

	List<Arrange> getHistorySxjx(String names, String tomonth);

	void removeAll(String name, String date);

	List<String> getGXcount(String month);
	
	List<Arrange> getArrangeByType(String type, String month);
	
	List<Arrange> getPublish(String section,String month,int state);
}