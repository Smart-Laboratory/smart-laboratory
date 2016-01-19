package com.smart.service;

import java.util.List;

import com.smart.model.pb.DayShift;

public interface DayShiftManager extends GenericManager<DayShift, Long> {

	List<DayShift> getBySection(String lastLibrary);

}
