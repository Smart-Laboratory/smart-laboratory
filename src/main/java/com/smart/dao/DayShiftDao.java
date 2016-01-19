package com.smart.dao;

import java.util.List;

import com.smart.model.pb.DayShift;

public interface DayShiftDao extends GenericDao<DayShift, Long>  {

	List<DayShift> getBySection(String section);

}
