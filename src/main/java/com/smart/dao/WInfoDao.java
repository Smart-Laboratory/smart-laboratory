package com.smart.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.smart.model.pb.WInfo;

public interface WInfoDao extends GenericDao<WInfo, Long> {

	@Transactional
	List<WInfo> getBySection(String section, String type);

	@Transactional
	WInfo getByName(String name);

	@Transactional
	List<WInfo> getAll(String sidx, String sord);

	@Transactional
	List<WInfo> getBySection(String section, String sidx,String sord);
	
	@Transactional
	List<WInfo> getBySearch(String field, String string);
	
	@Transactional
	List<WInfo> getBySection(String section);
	
	@Transactional
	WInfo getByWorkId(String workid);
	
	@Transactional
	List<WInfo> getByType(int type);
	
	@Transactional
	List<String> getNameBySection(String section);
}

