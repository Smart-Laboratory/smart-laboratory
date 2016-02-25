package com.smart.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.SxArrangeDao;
import com.smart.model.pb.SxArrange;
import com.smart.service.SxArrangeManager;

@Service("sxArrangeManager")
public class SxArrangeManageImpl extends GenericManagerImpl<SxArrange, Long> implements SxArrangeManager{

	private SxArrangeDao sxArrangeDao;
	
	@Autowired
	private void setSxArrangeDao(SxArrangeDao sxArrangeDao){
		this.dao = sxArrangeDao;
		this.sxArrangeDao = sxArrangeDao;
	}
	
	public void saveAll(List<SxArrange> list){
		sxArrangeDao.saveAll(list);
	}
	
	public List<SxArrange> getByMonth(String month){
		return sxArrangeDao.getByMonth(month);
	}
}
