package com.smart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.model.pb.Arrange;
import com.smart.dao.ArrangeDao;
import com.smart.service.ArrangeManager;

@Service("arrangeManager")
public class ArrangeManagerImpl extends GenericManagerImpl<Arrange, Long> implements ArrangeManager {

	private ArrangeDao arrangeDao;

	@Autowired
	public void setArrangeDao(ArrangeDao arrangeDao) {
		this.dao = arrangeDao;
		this.arrangeDao = arrangeDao;
	}

	public void saveAll(List<Arrange> list) {
		arrangeDao.saveAll(list);
	}

	public Arrange getByUser(String name, String day){
		return arrangeDao.getByUser(name, day);
	}
	
	public List<Arrange> getArrangerd(String names, String month, int state) {
		return arrangeDao.getArrangerd(names, month, state);
	}

	public List<Arrange> getPersonalArrange(String name, String month) {
		return arrangeDao.getPersonalArrange(name, month);
	}

	public List<Arrange> getMonthArrangeByName(String name, String month){
		return arrangeDao.getMonthArrangeByName(name, month);
	}
	
	public List<Arrange> getSxjxArrangerd(String yearAndMonth) {
		return arrangeDao.getSxjxArrangerd(yearAndMonth);
	}

	public List<Arrange> getHistorySxjx(String names, String tomonth) {
		return arrangeDao.getHistorySxjx(names, tomonth);
	}

	public void removeAll(String name, String date) {
		arrangeDao.removeAll(name, date);
	}
	
	public List<String> getGXcount(String month){
		return arrangeDao.getGXcount(month);
	}
	
	public List<Arrange> getArrangeByType(String type, String month){
		return arrangeDao.getArrangeByType(type, month);
	}
	
	public List<Arrange> getPublish(String section,String month,int state){
		return arrangeDao.getPublish(section,month,state);
	}
	
	public List<Arrange> getBySectionMonth(String month, String section){
		return arrangeDao.getBySectionMonth(month, section);
	}
	
	public List<Arrange> getMonthArrangeByshift(String shift, String month,String section){
		return arrangeDao.getMonthArrangeByshift(shift, month,section);
	}
	
	public List<Arrange> getByDay(String day){
		return arrangeDao.getByDay(day);
	}
}
