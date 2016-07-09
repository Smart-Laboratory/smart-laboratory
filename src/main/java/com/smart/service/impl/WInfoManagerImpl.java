package com.smart.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.solr.common.params.CommonParams.EchoParamStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.model.pb.WInfo;
import com.smart.dao.WInfoDao;
import com.smart.service.WInfoManager;

@Service("wInfoManager")
public class WInfoManagerImpl extends GenericManagerImpl<WInfo, Long> implements WInfoManager {

	private WInfoDao wInfoDao;

	@Autowired
	public void setWInfoDao(WInfoDao wInfoDao) {
		this.dao = wInfoDao;
		this.wInfoDao = wInfoDao;
	}

	public List<WInfo> getBySection(String section, String type) {
		return wInfoDao.getBySection(section, type);
	}

	public WInfo getByName(String name) {
		return wInfoDao.getByName(name);
	}

	public List<WInfo> getAll(String sidx, String sord) {
		return wInfoDao.getAll(sidx, sord);
	}

	public List<WInfo> getBySection(String section,String sidx,String sord) {
		return wInfoDao.getBySection(section,sidx,sord);
	}
	
	public List<WInfo> getBySearch(String field, String string){
		return wInfoDao.getBySearch(field,string);
	}

	public List<WInfo> getBySection(String section){
		return wInfoDao.getBySection(section);
	}
	
	public WInfo getByWorkId(String workid){
		return wInfoDao.getByWorkId(workid);
	}
	
	public List<WInfo> getByType(int type){
		return wInfoDao.getByType(type);
	}
	
	public List<String> getNameBySection(String section){
		return wInfoDao.getNameBySection(section);
	}
}
