package com.smart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.model.pb.Shift;
import com.smart.dao.ShiftDao;
import com.smart.service.ShiftManager;

@Service("shiftManager")
public class ShiftManagerImpl extends GenericManagerImpl<Shift, Long> implements ShiftManager {

	private ShiftDao shiftDao;

	@Autowired
	public void setShiftDao(ShiftDao shiftDao) {
		this.dao = shiftDao;
		this.shiftDao = shiftDao;
	}

	public List<String> getBySection(String section) {
		return shiftDao.getBySection(section);
	}

	public List<Shift> getShift(String section) {
		return shiftDao.getShift(section);
	}

	public List<Shift> getGrShift(String shift) {
		return shiftDao.getGrShift(shift);
	}

	public List<Shift> getAll(String sidx, String sord) {
		return shiftDao.getAll(sidx, sord);
	}

	public List<Shift> getShiftBySection(String section) {
		return shiftDao.getShiftBySection(section);
	}
	
	public List<Shift> getSx(){
		return shiftDao.getSx();
	}
}
