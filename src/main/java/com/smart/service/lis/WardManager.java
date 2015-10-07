package com.smart.service.lis;

import java.util.List;

import com.smart.model.lis.Ward;
import com.smart.service.GenericManager;

public interface WardManager extends GenericManager<Ward, String> {

	List<Ward> getByWard(String ward);

}
