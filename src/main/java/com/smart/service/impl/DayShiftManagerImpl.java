package com.smart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.model.pb.DayShift;
import com.smart.dao.DayShiftDao;
import com.smart.service.DayShiftManager;

@Service("dayShiftManager")
public class DayShiftManagerImpl extends GenericManagerImpl<DayShift, Long> implements DayShiftManager {

	private DayShiftDao dayShiftDao;

	@Autowired
	public void setDayShiftDao(DayShiftDao dayShiftDao) {
		this.dao = dayShiftDao;
		this.dayShiftDao = dayShiftDao;
	}

	public List<DayShift> getBySection(String section) {
		return dayShiftDao.getBySection(section);
	}
}
