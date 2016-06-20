package com.smart.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.model.pb.Shift;

public interface ShiftDao extends GenericDao<Shift, Long> {

	@Transactional
	List<String> getBySection(String section);

	@Transactional
	List<Shift> getShift(String section);

	@Transactional
	List<Shift> getGrShift(String shift);

	@Transactional
	List<Shift> getAll(String sidx, String sord);

	@Transactional
	List<Shift> getShiftBySection(String section);

	@Transactional
	List<Shift> getSx();
}
