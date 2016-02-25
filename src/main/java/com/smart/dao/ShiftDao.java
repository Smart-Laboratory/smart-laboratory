package com.smart.dao;

import java.util.List;

import com.smart.model.pb.Shift;

public interface ShiftDao extends GenericDao<Shift, Long> {

	List<String> getBySection(String section);

	List<Shift> getShift(String section);

	List<Shift> getGrShift(String shift);

	List<Shift> getAll(String sidx, String sord);

	List<Shift> getShiftBySection(String section);

	List<Shift> getSx();
}
