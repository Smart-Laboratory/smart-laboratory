package com.smart.service;

import java.util.List;

import com.smart.model.pb.WInfo;

public interface WInfoManager extends GenericManager<WInfo, Long> {

	List<WInfo> getBySection(String section, String type);

	WInfo getByName(String name);

	List<WInfo> getAll(String sidx, String sord);

	List<WInfo> getBySection(String section,String sidx, String sord);
	
	List<WInfo> getBySearch(String field, String string);

	List<WInfo> getBySection(String section);
	
	WInfo getByWorkId(String workid);
	
	List<WInfo> getByType(int type);
	
	List<String> getNameBySection(String section);
}
