package com.smart.dao.lis;

import java.util.List;

import com.smart.dao.GenericDao;
import com.smart.model.lis.Ward;

public interface WardDao extends GenericDao<Ward, String> {

	List<Ward> getByWard(String ward);

}
