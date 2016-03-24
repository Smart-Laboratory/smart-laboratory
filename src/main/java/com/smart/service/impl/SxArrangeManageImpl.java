package com.smart.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smart.dao.SxArrangeDao;
import com.smart.model.pb.SxArrange;
import com.smart.model.pb.WInfo;
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
	
	public List<SxArrange> getByName(String name){
		return sxArrangeDao.getByName(name);
	}
	
	public List<SxArrange> getByWeek(int year, int startWeek, int maxWeek){
		List<SxArrange> sxArranges = new ArrayList<SxArrange>();
		sxArranges = sxArrangeDao.getByWeek(year, startWeek, maxWeek);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		int yearMaxWeek = calendar.getActualMaximum(Calendar.WEEK_OF_YEAR);
		if(startWeek+maxWeek-1 >yearMaxWeek){
			List<SxArrange> arranges = sxArrangeDao.getByWeek(year+1, 1, startWeek+maxWeek-1-yearMaxWeek);
//			System.out.println(yearMaxWeek+arranges.size());
			sxArranges.addAll(arranges);
		}
//		System.out.println(startWeek+"-"+maxWeek+"---"+sxArranges.size());
		return sxArranges;
	}
	
}
