package com.smart.dao;

import java.util.List;

import com.smart.model.pb.WInfo;

public interface WInfoDao extends GenericDao<WInfo, Long> {

	List<WInfo> getBySection(String section, String type);

	WInfo getByName(String name);

	List<WInfo> getAll(String sidx, String sord);

	List<WInfo> getBySection(String section);

}
