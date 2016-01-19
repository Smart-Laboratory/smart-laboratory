package com.smart.dao;

import java.util.List;

import com.smart.model.pb.Arrange;

public interface ArrangeDao extends GenericDao<Arrange, Long> {

	void saveAll(List<Arrange> list);

	List<Arrange> getArrangerd(String names, String month);

	List<Arrange> getPersonalArrange(String name, String month);

	List<Arrange> getSxjxArrangerd(String yearAndMonth);

	List<Arrange> getHistorySxjx(String names, String tomonth);

	void removeAll(String name, String date);

}
