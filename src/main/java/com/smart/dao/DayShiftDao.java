package com.smart.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.model.pb.DayShift;

public interface DayShiftDao extends GenericDao<DayShift, Long>  {

	@Transactional
	List<DayShift> getBySection(String section);

}
