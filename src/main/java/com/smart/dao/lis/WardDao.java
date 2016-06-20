package com.smart.dao.lis;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Ward;

public interface WardDao extends GenericDao<Ward, String> {

	@Transactional
	List<Ward> getByWard(String ward);

}
