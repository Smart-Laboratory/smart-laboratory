package com.smart.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.model.pb.Arrange;

public interface ArrangeDao extends GenericDao<Arrange, Long> {

	void saveAll(List<Arrange> list);
	
	@Transactional
	Arrange getByUser(String name, String day);

	@Transactional
	List<Arrange> getArrangerd(String names, String month, int state);

	@Transactional
	List<Arrange> getPersonalArrange(String name, String day);
	
	@Transactional
	List<Arrange> getMonthArrangeByName(String name, String month);

	@Transactional
	List<Arrange> getSxjxArrangerd(String yearAndMonth);

	@Transactional
	List<Arrange> getHistorySxjx(String names, String tomonth);

	void removeAll(String name, String date);

	@Transactional
	List<String> getGXcount(String month);
	
	@Transactional
	List<Arrange> getArrangeByType(String type, String month);
	
	@Transactional
	List<Arrange> getPublish(String section,String month,int state);
	
	@Transactional
	List<Arrange> getBySectionMonth(String month, String section);
	
	@Transactional
	List<Arrange> getMonthArrangeByshift(String shift, String month,String section);
	
	@Transactional
	List<Arrange> getByDay(String day);
}
