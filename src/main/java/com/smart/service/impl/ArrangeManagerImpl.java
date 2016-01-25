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
	
	public List<Arrange> getArrangerd(String names, String month) {
		return arrangeDao.getArrangerd(names, month);
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
}
